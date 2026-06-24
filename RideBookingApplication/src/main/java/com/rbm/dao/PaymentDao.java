package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Payment;

public interface PaymentDao {
	
	void makePayment(Payment payment);
	
	Payment getPaymentById(int paymentId);
	
	List<Payment> getAllPayments();
		
	Double getTotalRevenue();
	
}
