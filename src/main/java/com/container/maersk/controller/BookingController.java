package com.container.maersk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.container.maersk.model.AvailableSpaces;
import com.container.maersk.model.BookingDetails;
import com.container.maersk.model.BookingResult;
import com.container.maersk.model.ContainerAvailability;
import com.container.maersk.repository.Booking;
import com.container.maersk.service.BookingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RequestMapping("/api/bookings")
@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    /**
     * Externally called APIs
     */

    @GetMapping("/all")
    public Flux<Booking> getBookings() {
        return bookingService.getAll();
    }

    @GetMapping("/container/availability")
    public Mono<ContainerAvailability> checkIfAvailable() {
        return bookingService.checkAvailability();
    }

    @PostMapping("/container/book")
    public Mono<ResponseEntity<BookingResult>> create(@RequestBody BookingDetails bookingDetails) {
        return bookingService.confirm(bookingDetails)
                .map(confirmed -> ResponseEntity.ok().body(confirmed))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    /**
     * Internally called APIs
     */

    @GetMapping("/container/checkAvailable")
    public Mono<AvailableSpaces> findAvailableSpaces() {
        return bookingService.checkAvailableCapacity();
    }

    @PostMapping("/container/save")
    public Mono<BookingResult> saveBooking(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }
}


