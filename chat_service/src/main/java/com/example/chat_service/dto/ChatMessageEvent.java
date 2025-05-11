package com.example.chat_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEvent {
    private Long chatId;
    private Long senderId;
    private String content;
    private LocalDateTime timestamp;
}
