package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Repository to access scheduled jobs (EmailJob) from the database.
 * Includes method to find pending jobs before the current time.
 */
public interface EmailJobRepository extends JpaRepository<EmailJob, Long> {

    /**
     * Finds all jobs that haven't been sent yet and are scheduled before the current time.
     *
     * @param now Current time
     * @return List of jobs that are due for execution
     */
    List<EmailJob> findBySentFalseAndScheduledTimeBefore(LocalDateTime now);

    // OPTIONAL: Filter by job type (useful if you ever want separate handling)
    List<EmailJob> findByJobTypeAndSentFalseAndScheduledTimeBefore(String jobType, LocalDateTime now);
}