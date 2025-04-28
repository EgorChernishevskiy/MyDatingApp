package com.example.swipe_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SwipeRequestDTO {
    private Long userIdFrom;
    private Long userIdTo;
    private Boolean decision;
}
