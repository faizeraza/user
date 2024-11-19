package com.microservices.user.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        super("No Record Fornd On Server!!");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
