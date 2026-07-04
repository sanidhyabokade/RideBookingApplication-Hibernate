package com.rbm.exception;

public class RatingException extends RuntimeException{
	
	
	public RatingException() {
        super("Rating operation failed.");
    }

    public RatingException(String message) {
        super(message);
    }
	
}
