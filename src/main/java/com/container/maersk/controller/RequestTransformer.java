package com.container.maersk.controller;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.container.maersk.model.AvailableSpaces;
import com.container.maersk.model.BookingDetails;
import com.container.maersk.model.BookingResult;
import com.container.maersk.model.ContainerAvailability;
import com.container.maersk.model.Require;
import com.container.maersk.repository.Booking;
import reactor.core.publisher.Mono;

@Component
public class RequestTransformer {

    private final RequestValidator validator;

    public RequestTransformer(RequestValidator validator) {
        this.validator = Require.requireNonNull(validator, "validator");
    }

    public Mono<Booking> toDb(BookingDetails bookingDetails) {
        return Mono.create(sink ->
        {
            try {
                if (Boolean.TRUE.equals(validator.validate(bookingDetails))) {
                    sink.success(up(bookingDetails));
                }
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }

    public BookingResult down(Booking booking) {
        return new BookingResult(booking.getBookingRef());
    }

    public Booking up(BookingDetails bookingDetails) {
        return new Booking(UUID.randomUUID(), bookingDetails.getContainerType(), bookingDetails.getContainerSize(),
                bookingDetails.getOrigin(), bookingDetails.getDestination(), bookingDetails.getQuantity(), Instant.now());
    }

    private ContainerAvailability map(AvailableSpaces spaces) {
        return spaces.getAvailableSpace() > 0 ? new ContainerAvailability(true)
                : new ContainerAvailability(false);
    }
}
