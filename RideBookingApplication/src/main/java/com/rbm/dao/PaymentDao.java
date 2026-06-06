package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Payment;
import com.rbm.enums.PaymentStatus;

public interface PaymentDao {
	
	void makePayment(Payment payment);
	
	Payment getPaymentById(int paymentId);
	
	List<Payment> getAllPayments();
	
	void updatePaymentStatus(int paymentId, PaymentStatus status);
	
}
