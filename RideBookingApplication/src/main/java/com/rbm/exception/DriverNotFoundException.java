package com.rbm.exception;

public class DriverNotFoundException extends RuntimeException{

	public DriverNotFoundException() {
        super("Driver not found.");
    }

    public DriverNotFoundException(String message) {
        super(message);
    }

	

}
