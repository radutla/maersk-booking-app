package com.container.maersk.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class BookingDetails {
    private UUID bookingRef;
    private ContainerType containerType;
    private Integer containerSize;
    private String origin;
    private String destination;
    private Integer quantity;
}
