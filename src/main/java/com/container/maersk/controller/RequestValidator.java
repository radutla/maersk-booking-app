package com.container.maersk.controller;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;
import com.container.maersk.dto.BookingRequest;
import com.container.maersk.dto.ContainerType;

@Component
public class RequestValidator {

    public boolean validate(BookingRequest bookingRequest) {
        return EnumUtils.isValidEnum(ContainerType.class, bookingRequest.getContainerType())
                && bookingRequest.getContainerSize() > 0
                && bookingRequest.getQuantity() > 0
                && isNotNullNotEmpty(bookingRequest.getOrigin())
                && isNotNullNotEmpty(bookingRequest.getDestination());
    }

    private boolean isNotNullNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
