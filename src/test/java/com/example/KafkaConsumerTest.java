package com.example;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

@SpringBootTest
@DirtiesContext // Ensures a fresh Spring context
@EmbeddedKafka(partitions = 1, topics = { "email-topic" }, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092", "port=9092"
})
public class KafkaConsumerTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Test
    public void testKafkaConsumerReceivesMessage() throws InterruptedException {
        // Create Kafka producer with string serializers
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafka);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        ProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<>(producerProps);
        KafkaTemplate<String, String> template = new KafkaTemplate<>(pf);

        String message = "Test message for KafkaConsumer";

        // Send message
        template.send("email-topic", message);
        System.out.println("✅ Test message sent to topic");

        // Give the consumer time to process
        Thread.sleep(2000);
    }
}