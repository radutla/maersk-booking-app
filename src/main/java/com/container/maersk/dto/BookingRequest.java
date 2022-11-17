package com.container.maersk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookingRequest {
    private String containerType;
    private Integer containerSize;
    private String origin;
    private String destination;
    private Integer quantity;
}
