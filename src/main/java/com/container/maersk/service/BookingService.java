package com.container.maersk.service;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.container.maersk.controller.MissingContainerDetailsException;
import com.container.maersk.controller.RequestTransformer;
import com.container.maersk.controller.RequestValidator;
import com.container.maersk.model.AvailableSpaces;
import com.container.maersk.model.BookingDetails;
import com.container.maersk.model.BookingResult;
import com.container.maersk.model.ContainerAvailability;
import com.container.maersk.repository.Booking;
import com.container.maersk.repository.BookingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Component
public class BookingService {

    final RequestTransformer requestTransformer;
    final RequestValidator requestValidator;
    private final WebClient webClient;
    private final BookingRepository bookingRepository;

    public BookingService(WebClient.Builder webClientBuilder, RequestTransformer requestTransformer, RequestValidator requestValidator, BookingRepository bookingRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/bookings").build();
        this.requestTransformer = requestTransformer;
        this.requestValidator = requestValidator;
        this.bookingRepository = bookingRepository;
    }

    public Flux<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public Mono<BookingResult> confirm(BookingDetails bookingDetails) {
        return requestValidator.validate(bookingDetails) ?
                webClient
                .post()
                .uri("/container/save")
                .bodyValue(requestTransformer.up(bookingDetails))
                .retrieve()
                .bodyToMono(BookingResult.class)
                : Mono.error(new MissingContainerDetailsException("Invalid Request details"));
    }

    public Mono<ContainerAvailability> checkAvailability() {
        return webClient
                .get()
                .uri("/container/checkAvailable")
                .retrieve()
                .bodyToMono(AvailableSpaces.class)
                .map(this::mapResponse);
    }

    public Mono<BookingResult> save(Booking booking) {
        return bookingRepository.save(booking).map(this::down);
    }


    /* The below method is mimicing the behaviour of external service
            "external service (that doesn’t really exist!) at endpoint: https://maersk.com/api/bookings/checkAvailable "
     */
    public Mono<AvailableSpaces> checkAvailableCapacity() {
        return Mono.just(
                new AvailableSpaces(ThreadLocalRandom.current().nextInt(0, 100 + 1))
        );
    }

    private BookingResult down(Booking confirmed) {
        return new BookingResult(confirmed.getBookingRef());
    }

    private ContainerAvailability mapResponse(AvailableSpaces spaces) {
        return spaces.getAvailableSpace() > 0 ? new ContainerAvailability(true)
                : new ContainerAvailability(false);
    }


}
