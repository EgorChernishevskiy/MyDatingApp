package com.example.chat_service.kafka;

import com.example.chat_service.dto.MatchEvent;
import com.example.chat_service.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchEventListener {

    private final ChatService chatService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "match-events", groupId = "chat-service")
    public void consume(String message) {
        try {
            System.out.println("Received Kafka message: " + message);
            MatchEvent event = objectMapper.readValue(message, MatchEvent.class);
            chatService.createChatIfNotExists(event.getUserIdFrom(), event.getUserIdTo());
        } catch (Exception e) {
            System.err.println("Error processing Kafka message: " + message);
            e.printStackTrace();
        }
    }
}