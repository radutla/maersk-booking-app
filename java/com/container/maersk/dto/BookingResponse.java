package com.container.maersk.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public class BookingResponse {
    private UUID bookingRef;
}
