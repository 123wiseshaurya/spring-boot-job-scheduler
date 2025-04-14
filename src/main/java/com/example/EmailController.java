package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*") // ✅ Allow frontend to make requests
public class EmailController {

    @Autowired
    private EmailJobRepository jobRepository;

    @PostMapping("/schedule")
    public String scheduleJob(
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String body,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time,
            @RequestParam(required = false) Integer delayMinutes,
            @RequestParam(defaultValue = "ONCE") String repeat,
            @RequestParam(defaultValue = "EMAIL") String jobType,
            @RequestParam(required = false) String daysOfWeek,
            @RequestParam(required = false) String daysOfMonth,
            @RequestParam(required = false) String commandToRun,
            @RequestParam(required = false) String binaryName,
            @RequestParam(required = false) String zoneId
    ) {
        // 🌍 Time zone setup
        ZoneId userZone = (zoneId != null) ? ZoneId.of(zoneId) : ZoneId.systemDefault();
        LocalDateTime scheduledTime;

        if (delayMinutes != null) {
            scheduledTime = LocalDateTime.now(userZone).plusMinutes(delayMinutes);
        } else if (time != null) {
            scheduledTime = time;
        } else {
            return "❌ Provide either 'time' or 'delayMinutes'";
        }

        // Convert user time to server time
        ZonedDateTime userZonedTime = ZonedDateTime.of(scheduledTime, userZone);
        ZonedDateTime serverZonedTime = userZonedTime.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime serverTime = serverZonedTime.toLocalDateTime();

        // 🎯 Create job entity
        EmailJob job = new EmailJob();
        job.setRepeat(repeat.toUpperCase());
        job.setJobType(jobType.toUpperCase());
        job.setScheduledTime(serverTime);
        job.setStatus("PENDING");
        job.setSent(false);
        job.setTimeZone(userZone.toString());

        // ✅ Set email fields for EMAIL & KAFKA jobs
        if ("EMAIL".equalsIgnoreCase(jobType) || "KAFKA".equalsIgnoreCase(jobType)) {
            job.setToEmail(to);
            job.setSubject(subject);
            job.setBody(body);
        }

        // ⚙️ Set binary job command
        if ("BINARY".equalsIgnoreCase(jobType)) {
            if (commandToRun != null) {
                job.setCommandToRun(commandToRun);
            } else if (binaryName != null) {
                job.setCommandToRun("http://localhost:9000/binary-files/" + binaryName);
            }
        }

        // 🔁 Recurrence setup
        if (daysOfWeek != null) job.setDaysOfWeek(daysOfWeek.toUpperCase());
        if (daysOfMonth != null) job.setDaysOfMonth(daysOfMonth);

        jobRepository.save(job);

        // 📋 Log job creation
        System.out.println("📌 Job created: ID=" + job.getId() + " | Type=" + job.getJobType());
        System.out.println("🕒 Client time: " + scheduledTime + " | Zone: " + userZone);
        System.out.println("🕒 Server time: " + serverTime + " | Zone: " + ZoneId.systemDefault());

        return String.format(
                "✅ Job scheduled!\n🕒 Your Time: %s (%s)\n🖥️ Server Time: %s (%s)",
                scheduledTime, userZone,
                serverTime, ZoneId.systemDefault()
        );
    }

    @GetMapping("/jobs")
    public List<EmailJob> getAllJobs() {
        return jobRepository.findAll();
    }
}