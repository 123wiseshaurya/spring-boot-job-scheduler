package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka; // ✅ Import Kafka
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableKafka // ✅ Add this to enable Kafka listener support
public class EmaildemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmaildemoApplication.class, args);
        System.out.println("🚀 App is running — Scheduler & Kafka Consumer are active!");
    }
}