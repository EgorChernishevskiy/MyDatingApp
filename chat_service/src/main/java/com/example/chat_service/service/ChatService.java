package com.example.chat_service.service;

import com.example.chat_service.dto.ChatMessageEvent;
import com.example.chat_service.dto.SendMessageRequest;
import com.example.chat_service.entity.Chat;
import com.example.chat_service.entity.Message;
import com.example.chat_service.repository.ChatRepository;
import com.example.chat_service.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final KafkaTemplate<String, ChatMessageEvent> kafkaTemplate;

    public List<Chat> getAllChatsForUser(Long userId) {
        return chatRepository.findAllByUser(userId);
    }

    public Chat createChatIfNotExists(Long userId1, Long userId2) {
        Long low = Math.min(userId1, userId2);
        Long high = Math.max(userId1, userId2);

        return chatRepository.findByUserId1AndUserId2(low, high)
                .orElseGet(() -> chatRepository.save(Chat.builder()
                        .userId1(low)
                        .userId2(high)
                        .build()));
    }

    public void handleNewMessage(SendMessageRequest request, MessageRepository messageRepository) {
        Message saved = messageRepository.save(Message.builder()
                .chatId(request.getChatId())
                .senderId(request.getSenderId())
                .content(request.getContent())
                .build());

        kafkaTemplate.send("chat-messages", new ChatMessageEvent(
                saved.getChatId(),
                saved.getSenderId(),
                saved.getContent(),
                saved.getTimestamp()
        ));
    }
}
