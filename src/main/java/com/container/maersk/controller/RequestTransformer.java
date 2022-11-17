package com.container.maersk.controller;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.container.maersk.dto.AvailableSpaces;
import com.container.maersk.dto.BookingDetails;
import com.container.maersk.dto.BookingRequest;
import com.container.maersk.dto.BookingResponse;
import com.container.maersk.dto.ContainerAvailability;
import com.container.maersk.dto.ContainerType;
import com.container.maersk.model.Booking;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RequestTransformer {

    private final RequestValidator validator;

    public Mono<Booking> toDb(BookingRequest bookingRequest) {
        return Mono.create(sink ->
        {
            try {
                if (Boolean.TRUE.equals(validator.validate(bookingRequest))) {
                    sink.success(up(bookingRequest));
                }
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }

    public BookingResponse map(Booking booking) {
        return BookingResponse.builder()
                .bookingRef(booking.getBookingRef())
                .build();
    }

    public BookingDetails mapResponse(Booking booking) {
        return BookingDetails.builder()
                .bookingRef(booking.getBookingRef())
                .containerSize(booking.getContainerSize())
                .containerType(ContainerType.valueOf(booking.getContainerType()))
                .origin(booking.getOrigin())
                .destination(booking.getDestination())
                .quantity(booking.getQuantity())
                .build();
    }

    public Booking up(BookingRequest bookingRequest) {
        return Booking.builder()
                .bookingRef(UUID.randomUUID())
                .containerType(bookingRequest.getContainerType())
                .containerSize(bookingRequest.getContainerSize())
                .origin(bookingRequest.getOrigin())
                .destination(bookingRequest.getDestination())
                .quantity(bookingRequest.getQuantity())
                .creationTime(Instant.now())
                .build();
    }

    public ContainerAvailability map(AvailableSpaces spaces) {
        return spaces.getAvailableSpace() > 0 ?
                ContainerAvailability.builder().available(true).build()
                : ContainerAvailability.builder().available(false).build();
    }
}
