package com.container.maersk.controller;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;
import com.container.maersk.model.BookingDetails;
import com.container.maersk.model.ContainerType;

@Component
public class RequestValidator {

    public boolean validate(BookingDetails bookingDetails) {
        return EnumUtils.isValidEnum(ContainerType.class, bookingDetails.getContainerType())
                && bookingDetails.getContainerSize() > 0
                && bookingDetails.getQuantity() > 0
                && isNotNullNotEmpty(bookingDetails.getOrigin())
                && isNotNullNotEmpty(bookingDetails.getDestination());
    }

    private boolean isNotNullNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
