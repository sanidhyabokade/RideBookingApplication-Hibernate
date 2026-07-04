package com.rbm.exception;

public class PaymentException extends RuntimeException{
	
	public PaymentException() {
        super("Payment could not be processed.");
    }

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
