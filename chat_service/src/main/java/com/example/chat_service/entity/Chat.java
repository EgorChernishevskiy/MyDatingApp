package com.example.chat_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats", uniqueConstraints = @UniqueConstraint(columnNames = {"userId1", "userId2"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId1;
    private Long userId2;
    private LocalDateTime createdAt = LocalDateTime.now();
}
