package com.example;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Test
    public void testSendJobToKafka() {
        EmailJob job = new EmailJob();
        job.setId(1L);
        job.setBody("Test Kafka Message");

        kafkaProducer.sendJobToKafka(job);

        // Capture arguments passed to KafkaTemplate
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), valueCaptor.capture());

        assertTrue(topicCaptor.getValue().equals("email-topic"));
        assertTrue(valueCaptor.getValue().contains("\"body\":\"Test Kafka Message\""));
    }
}