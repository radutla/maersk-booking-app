package com.container.maersk.service;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.container.maersk.controller.MissingContainerDetailsException;
import com.container.maersk.controller.RequestTransformer;
import com.container.maersk.controller.RequestValidator;
import com.container.maersk.dto.AvailableSpaces;
import com.container.maersk.dto.BookingDetails;
import com.container.maersk.dto.BookingRequest;
import com.container.maersk.dto.BookingResponse;
import com.container.maersk.dto.ContainerAvailability;
import com.container.maersk.model.Booking;
import com.container.maersk.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class BookingService {

    private final RequestTransformer requestTransformer;
    private final RequestValidator requestValidator;
    private final WebClient webClient;
    private final BookingRepository bookingRepository;

    public BookingService(WebClient.Builder webClientBuilder, RequestTransformer requestTransformer,
                          RequestValidator requestValidator, BookingRepository bookingRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/bookings").build();
        this.requestTransformer = requestTransformer;
        this.requestValidator = requestValidator;
        this.bookingRepository = bookingRepository;
    }

    public Flux<BookingDetails> getAll() {
        return bookingRepository.findAll()
                .map(requestTransformer::mapResponse);
    }

    public Mono<BookingResponse> confirm(BookingRequest bookingRequest) {
        return requestValidator.validate(bookingRequest) ?
                webClient.post()
                        .uri("/container/save")
                        .bodyValue(requestTransformer.up(bookingRequest))
                        .retrieve()
                        .bodyToMono(BookingResponse.class)
                : Mono.error(new MissingContainerDetailsException("Invalid Request details"));
    }

    public Mono<ContainerAvailability> checkAvailability() {
        return webClient.get()
                .uri("/container/checkAvailable")
                .retrieve()
                .bodyToMono(AvailableSpaces.class)
                .map(requestTransformer::map);
    }

    public Mono<BookingResponse> save(Booking booking) {
        log.info("booking with ref is being saved {}", booking.getBookingRef());
        return bookingRepository.save(booking)
                .map(requestTransformer::map);
    }

    /**
     * The below method is mimicing the behaviour of external service
     * "external service (that doesn’t really exist!) at endpoint: https://maersk.com/api/bookings/checkAvailable "
     */
    public Mono<AvailableSpaces> checkAvailableCapacity() {
        return Mono.just(
                AvailableSpaces.builder()
                        .availableSpace(ThreadLocalRandom.current().nextInt(0, 100 + 1))
                        .build()
        );
    }
}
