package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // ✅ Import this
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String topic = "email-topic";
    private final ObjectMapper objectMapper;

    public KafkaProducer() {
        // ✅ Properly configure ObjectMapper to handle LocalDateTime
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 👈 Register JavaTime support
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 👈 Format as ISO string
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("📤 Kafka message sent: " + message);
    }

    public void sendJobToKafka(EmailJob job) {
        try {
            String json = objectMapper.writeValueAsString(job);
            kafkaTemplate.send(topic, json);
            System.out.println("📨 Kafka JSON sent: " + json);
        } catch (Exception e) {
            System.err.println("❌ Error sending Kafka job: " + e.getMessage());
        }
    }
}