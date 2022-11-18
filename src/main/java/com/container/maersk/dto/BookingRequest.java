package com.container.maersk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    private String containerType;
    private Integer containerSize;
    private String origin;
    private String destination;
    private Integer quantity;
}
