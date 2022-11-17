package com.container.maersk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.container.maersk.dto.AvailableSpaces;
import com.container.maersk.dto.BookingDetails;
import com.container.maersk.dto.BookingRequest;
import com.container.maersk.dto.BookingResponse;
import com.container.maersk.dto.ContainerAvailability;
import com.container.maersk.model.Booking;
import com.container.maersk.service.BookingService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/api/bookings")
@RestController
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    /**
     * Externally called APIs
     */

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookingDetails> getBookings() {
        return bookingService.getAll();
    }

    @GetMapping("/container/availability")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ContainerAvailability> checkIfAvailable() {
        return bookingService.checkAvailability();
    }

    @PostMapping("/container/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<BookingResponse>> create(@RequestBody BookingRequest bookingRequest) {
        return bookingService.confirm(bookingRequest)
                .map(confirmed -> ResponseEntity.ok().body(confirmed))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    /**
     * Internally called APIs
     */

    @GetMapping("/container/checkAvailable")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AvailableSpaces> findAvailableSpaces() {
        return bookingService.checkAvailableCapacity();
    }

    @PostMapping("/container/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookingResponse> saveBooking(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }
}


