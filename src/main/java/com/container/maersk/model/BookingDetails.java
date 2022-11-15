package com.container.maersk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookingDetails {
    private String containerType;
    private Integer containerSize;
    private String origin;
    private String destination;
    private Integer quantity;
}
