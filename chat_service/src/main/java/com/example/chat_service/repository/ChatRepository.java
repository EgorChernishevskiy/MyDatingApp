package com.example.chat_service.repository;

import com.example.chat_service.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    boolean existsByUserId1AndUserId2(Long userId1, Long userId2);
    List<Chat> findAllByUserId1OrUserId2(Long userId1, Long userId2);
    Optional<Chat> findByUserId1AndUserId2(Long userId1, Long userId2);
    Optional<Chat> findByUserId2AndUserId1(Long userId2, Long userId1);
}
