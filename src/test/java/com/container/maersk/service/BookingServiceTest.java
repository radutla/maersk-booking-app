package com.container.maersk.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.web.reactive.function.client.WebClient;
import com.container.maersk.controller.RequestTransformer;
import com.container.maersk.controller.RequestValidator;
import com.container.maersk.model.BookingResult;
import com.container.maersk.repository.Booking;
import com.container.maersk.repository.BookingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(JUnit4.class)
public class BookingServiceTest {

    private static BookingRepository bookingRepository;
    private static BookingService bookingService;
    private static final WebClient.Builder builder = WebClient.builder();

    private final Booking booking = new Booking(UUID.randomUUID(), "DRY", 1, "SINGAPORE", "LONDON", 5, Instant.now());

    @BeforeClass
    public static void setup() {
        bookingRepository = mock(BookingRepository.class);
        RequestValidator requestValidator = mock(RequestValidator.class);
        RequestTransformer requestTransformer = new RequestTransformer(requestValidator);
        bookingService = new BookingService(builder, requestTransformer, requestValidator, bookingRepository);
    }

    @Test
    public void testGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(Flux.just(booking));
        Flux<Booking> response = bookingService.getAll();

        StepVerifier.create(response)
                .assertNext(
                        actualResult -> {
                            Assert.assertNotNull(actualResult);
                            Assert.assertEquals(actualResult.getContainerType(), booking.getContainerType());
                        }
                )
                .expectComplete()
                .verify();
    }

    @Test
    public void testBookingConfirmSuccess() {
        when(bookingRepository.save(any(Booking.class))).
                thenReturn(Mono.just(booking));
        Mono<BookingResult> response = bookingService.save(booking);
        StepVerifier.create(response)
                .assertNext(
                        Assert::assertNotNull
                )
                .expectComplete()
                .verify();
    }
}