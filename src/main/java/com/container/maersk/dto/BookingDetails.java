package com.container.maersk.dto;

import java.util.UUID;
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
public class BookingDetails {
    private UUID bookingRef;
    private ContainerType containerType;
    private Integer containerSize;
    private String origin;
    private String destination;
    private Integer quantity;
}
