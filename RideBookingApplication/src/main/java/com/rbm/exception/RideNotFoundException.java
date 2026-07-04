package com.rbm.exception;

public class RideNotFoundException extends RuntimeException{
	public RideNotFoundException() {
        super("Ride not found.");
    }

    public RideNotFoundException(String message) {
        super(message);
    }
}
