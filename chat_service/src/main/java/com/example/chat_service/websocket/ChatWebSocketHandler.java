package com.example.chat_service.websocket;

import com.example.chat_service.dto.SendMessageRequest;
import com.example.chat_service.entity.Message;
import com.example.chat_service.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Map<Long, Map<Long, WebSocketSession>> chatSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long chatId = extractChatId(session);
        Long userId = extractUserId(session);

        if (chatId != null && userId != null) {
            chatSessions.computeIfAbsent(chatId, k -> new ConcurrentHashMap<>())
                    .put(userId, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long chatId = extractChatId(session);
        Long userId = extractUserId(session);

        if (chatId != null && userId != null) {
            Map<Long, WebSocketSession> users = chatSessions.get(chatId);
            if (users != null) {
                users.remove(userId);
                if (users.isEmpty()) {
                    chatSessions.remove(chatId);
                }
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        SendMessageRequest request = objectMapper.readValue(textMessage.getPayload(), SendMessageRequest.class);

        // Сохраняем сообщение
        Message message = Message.builder()
                .chatId(request.getChatId())
                .senderId(request.getSenderId())
                .content(request.getContent())
                .build();
        message = messageRepository.save(message);

        String response = objectMapper.writeValueAsString(message);

        // Шлем сообщение всем участникам чата
        Map<Long, WebSocketSession> sessions = chatSessions.get(request.getChatId());
        if (sessions != null) {
            for (WebSocketSession s : sessions.values()) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(response));
                }
            }
        }
    }

    private Long extractUserId(WebSocketSession session) {
        String query = Objects.requireNonNull(session.getUri()).getQuery();
        return extractParam(query, "userId");
    }

    private Long extractChatId(WebSocketSession session) {
        String query = Objects.requireNonNull(session.getUri()).getQuery();
        return extractParam(query, "chatId");
    }

    private Long extractParam(String query, String key) {
        try {
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith(key + "=")) {
                    return Long.parseLong(param.split("=")[1]);
                }
            }
        } catch (Exception ignored) {}
        return null;
    }
}
