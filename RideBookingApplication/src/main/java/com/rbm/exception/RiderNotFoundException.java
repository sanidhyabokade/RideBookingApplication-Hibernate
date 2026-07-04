package com.rbm.exception;

public class RiderNotFoundException extends RuntimeException {
	public RiderNotFoundException() {
        super("Rider not found.");
    }

    public RiderNotFoundException(String message) {
        super(message);
    }
}
