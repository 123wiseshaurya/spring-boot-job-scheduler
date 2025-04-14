package com.example;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EmailJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 📧 Email/Kafka-specific fields
    @Column(nullable = true)
    private String toEmail;

    @Column(nullable = true)
    private String subject;

    @Column(nullable = true, length = 2000)
    private String body;

    // ⏰ Scheduling metadata
    @Column(nullable = false)
    private LocalDateTime scheduledTime;

    @Column(nullable = false)
    private boolean sent = false;

    @Column(nullable = false)
    private String repeat = "ONCE"; // ONCE, DAILY, WEEKLY, MONTHLY

    @Column(nullable = false)
    private String jobType = "EMAIL"; // EMAIL, KAFKA, BINARY

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, SUCCESS, FAILURE

    // 🔁 Recurrence support
    @Column(nullable = true)
    private String daysOfWeek; // e.g., "MONDAY,FRIDAY"

    @Column(nullable = true)
    private String daysOfMonth; // e.g., "1,15,30"

    // 🧠 Binary command or MinIO presigned URL
    @Column(length = 1000)
    private String commandToRun;

    // 🌍 Time zone for scheduling (e.g., "Asia/Kolkata", "UTC")
    @Column(nullable = false)
    private String timeZone = "UTC";

    // ✅ Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getDaysOfMonth() {
        return daysOfMonth;
    }

    public void setDaysOfMonth(String daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public String getCommandToRun() {
        return commandToRun;
    }

    public void setCommandToRun(String commandToRun) {
        this.commandToRun = commandToRun;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}