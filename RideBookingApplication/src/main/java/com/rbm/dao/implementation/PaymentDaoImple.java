package com.rbm.dao.implementation;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.PaymentDao;
import com.rbm.entity.Payment;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PaymentDaoImple implements PaymentDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	Scanner sc = AppUtil.getScanner();

	@Override
	public void makePayment(Payment payment) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {

			et.begin();

			em.merge(payment);

			et.commit();

			System.out.println("Payment Successful!");

		} catch (Exception e) {

			if(et.isActive()) {
				et.rollback();
			}

			System.err.println("Payment Failed!");

		} finally {
			em.close();
		}

	}

	@Override
	public Payment getPaymentById(int paymentId) {
		EntityManager em = emf.createEntityManager();

		Payment payment = em.find(Payment.class, paymentId);

		em.close();

		return payment;
	}

	@Override
	public List<Payment> getAllPayments() {
		EntityManager em = emf.createEntityManager();

		TypedQuery<Payment> query =
				em.createQuery("SELECT p FROM Payment p", Payment.class);

		List<Payment> payments = query.getResultList();

		em.close();

		return payments;
	}


	@Override
	public Double getTotalRevenue() {
		EntityManager em = emf.createEntityManager();

		Double revenue =
				em.createQuery("SELECT COALESCE(SUM(p.amount),0) FROM Payment p", Double.class)
				.getSingleResult();

		em.close();

		return revenue;
	}

}
