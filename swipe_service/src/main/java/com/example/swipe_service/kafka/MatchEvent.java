package com.example.swipe_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchEvent {
    private Long userIdFrom;
    private Long userIdTo;
}
