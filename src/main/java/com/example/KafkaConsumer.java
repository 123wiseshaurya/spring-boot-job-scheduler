package com.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "email-topic", groupId = "job-scheduler-group")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("📥 Kafka Consumer received a message:");
        System.out.println("🔑 Key: " + record.key());
        System.out.println("📦 Value: " + record.value());
        System.out.println("📅 Topic: " + record.topic());
    }
}