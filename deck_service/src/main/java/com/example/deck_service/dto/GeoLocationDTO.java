package com.example.deck_service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class GeoLocationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private double latitude;
    private double longitude;
}