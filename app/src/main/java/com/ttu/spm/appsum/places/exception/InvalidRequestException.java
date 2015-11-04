package com.ttu.spm.appsum.places.exception;

import com.ttu.spm.appsum.places.Statuses;

public class InvalidRequestException extends GooglePlacesException {
    public InvalidRequestException(String errorMessage) {
        super(Statuses.STATUS_INVALID_REQUEST, errorMessage);
    }

    public InvalidRequestException() {
        this(null);
    }
}
