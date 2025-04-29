package com.example.deck_service.entity.swipe;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "swipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userIdFrom;

    private Long userIdTo;

    private Boolean decisionFrom;

    private Boolean decisionTo;
}
