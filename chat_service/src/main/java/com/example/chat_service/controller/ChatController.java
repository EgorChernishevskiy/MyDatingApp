package com.example.chat_service.controller;

import com.example.chat_service.dto.SendMessageRequest;
import com.example.chat_service.entity.Chat;
import com.example.chat_service.entity.Message;
import com.example.chat_service.repository.MessageRepository;
import com.example.chat_service.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
class ChatController {

    private final MessageRepository messageRepository;
    private final ChatService chatService;

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Chat>> getChats(@PathVariable Long userId) {
        List<Chat> chats = chatService.getAllChatsForUser(userId);
        return ResponseEntity.ok(chats);
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody SendMessageRequest request) {
        chatService.handleNewMessage(request, messageRepository);
    }

    @GetMapping("/messages/{chatId}")
    public List<Message> getMessages(@PathVariable Long chatId) {
        return messageRepository.findByChatId(chatId);
    }
}
