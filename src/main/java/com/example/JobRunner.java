package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class JobRunner {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailJobRepository jobRepository;

    @Scheduled(fixedRate = 30000)
    public void runScheduledJobs() {
        System.out.println("⏰ Checking jobs at " + LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now().minusSeconds(1);
        List<EmailJob> jobs = jobRepository.findBySentFalseAndScheduledTimeBefore(now);

        for (EmailJob job : jobs) {
            boolean shouldRun = true;

            if ("WEEKLY".equalsIgnoreCase(job.getRepeat()) && job.getDaysOfWeek() != null) {
                DayOfWeek today = LocalDate.now().getDayOfWeek();
                shouldRun = Arrays.asList(job.getDaysOfWeek().split(",")).contains(today.name());
            }

            if ("MONTHLY".equalsIgnoreCase(job.getRepeat()) && job.getDaysOfMonth() != null) {
                int today = LocalDate.now().getDayOfMonth();
                shouldRun = Arrays.asList(job.getDaysOfMonth().split(",")).contains(String.valueOf(today));
            }

            if (!shouldRun) {
                System.out.println("⏩ Skipping job ID: " + job.getId());
                continue;
            }

            try {
                System.out.println("🔍 Executing jobType = " + job.getJobType() + " for Job ID: " + job.getId());

                switch (job.getJobType().toUpperCase()) {
                    case "EMAIL" -> {
                        emailService.sendEmail(job.getToEmail(), job.getSubject(), job.getBody());
                        handleRecurrence(job);
                        job.setStatus("SUCCESS");
                    }

                    case "KAFKA" -> {
                        kafkaProducer.sendJobToKafka(job);
                        job.setStatus("SUCCESS");
                        job.setSent(true);
                    }

                    case "BINARY" -> {
                        String cmd = job.getCommandToRun();
                        if (cmd == null || cmd.trim().isEmpty()) {
                            System.out.println("⚠️ No command provided for binary execution.");
                            job.setStatus("FAILURE");
                            job.setSent(true);
                            break;
                        }

                        System.out.println("📦 Command to execute: " + cmd);

                        if (cmd.startsWith("http://") || cmd.startsWith("https://")) {
                            String filename = cmd.substring(cmd.lastIndexOf('/') + 1).split("\\?")[0];
                            File downloaded = new File("/tmp/" + filename);

                            try (InputStream in = new URL(cmd).openStream()) {
                                Files.copy(in, downloaded.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("📥 Downloaded file to: " + downloaded.getAbsolutePath());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                job.setStatus("FAILURE");
                                job.setSent(true);
                                break;
                            }

                            if (filename.endsWith(".jar")) {
                                cmd = "java -jar " + downloaded.getAbsolutePath();
                            } else if (filename.endsWith(".zip")) {
                                String unzipPath = "/tmp/" + filename.replace(".zip", "");
                                unzip(downloaded, unzipPath);

                                File pkgJson = new File(unzipPath + "/package.json");
                                if (!pkgJson.exists()) {
                                    System.out.println("❌ No package.json found. Cannot run npm script.");
                                    job.setStatus("FAILURE");
                                    job.setSent(true);
                                    break;
                                }

                                cmd = "npm run sayhello --prefix " + unzipPath;
                            } else {
                                System.out.println("⚠️ Unsupported file type.");
                                job.setStatus("FAILURE");
                                job.setSent(true);
                                break;
                            }
                        }

                        ProcessBuilder builder = cmd.contains("|") || cmd.contains(">") || cmd.contains("<")
                                ? (System.getProperty("os.name").toLowerCase().contains("win")
                                ? new ProcessBuilder("cmd.exe", "/c", cmd)
                                : new ProcessBuilder("sh", "-c", cmd))
                                : new ProcessBuilder(cmd.split(" "));

                        builder.redirectErrorStream(true);
                        Process process = builder.start();

                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println("⚙️ " + line);
                            }
                        }

                        boolean finished = process.waitFor(60, TimeUnit.SECONDS);
                        job.setStatus(finished && process.exitValue() == 0 ? "SUCCESS" : "FAILURE");
                        job.setSent(true);

                        System.out.println(finished ? "✅ Binary executed successfully." : "❌ Binary failed.");
                    }
                }
            } catch (Exception e) {
                job.setStatus("FAILURE");
                job.setSent(true);
                e.printStackTrace();
            }

            jobRepository.save(job);
            System.out.println("✅ Job finished: ID " + job.getId() + " | Status: " + job.getStatus());
        }
    }

    private void handleRecurrence(EmailJob job) {
        switch (job.getRepeat().toUpperCase()) {
            case "ONCE" -> job.setSent(true);
            case "HOURLY" -> job.setScheduledTime(job.getScheduledTime().plusHours(1));
            case "DAILY" -> job.setScheduledTime(job.getScheduledTime().plusDays(1));
            case "WEEKLY" -> job.setScheduledTime(job.getScheduledTime().plusWeeks(1));
            case "MONTHLY" -> job.setScheduledTime(job.getScheduledTime().plusMonths(1));
        }
    }

    private void unzip(File zipFile, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(dir, entry.getName());
                String canonicalPath = newFile.getCanonicalPath();
                if (!canonicalPath.startsWith(dir.getCanonicalPath() + File.separator)) {
                    throw new IOException("❌ Entry is outside of target dir: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }
}