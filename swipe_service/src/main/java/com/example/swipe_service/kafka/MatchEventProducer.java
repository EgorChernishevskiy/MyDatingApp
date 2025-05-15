package com.example.swipe_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchEventProducer {

    private final KafkaTemplate<String, MatchEvent> kafkaTemplate;
    private static final String TOPIC = "match-events";

    public void sendMatchEvent(MatchEvent event) {
        System.out.println("Sending match event to Kafka: " + event);
        kafkaTemplate.send(TOPIC, event);
    }
}

