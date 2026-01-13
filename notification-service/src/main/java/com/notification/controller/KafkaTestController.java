package com.notification.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/kafka-test")
public class KafkaTestController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaTestController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/producer")
    public String testProducer() {
        String testMessage = "Test message at " + LocalDateTime.now();
        kafkaTemplate.send("test-topic", "test-key", testMessage);
        return "Message sent: " + testMessage;
    }
}