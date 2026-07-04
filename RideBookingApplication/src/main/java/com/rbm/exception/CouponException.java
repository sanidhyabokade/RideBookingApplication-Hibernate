package com.rbm.exception;

public class CouponException extends RuntimeException{
	
	public CouponException() {
        super("Coupon operation failed.");
    }

    public CouponException(String message) {
        super(message);
    }
	
}
