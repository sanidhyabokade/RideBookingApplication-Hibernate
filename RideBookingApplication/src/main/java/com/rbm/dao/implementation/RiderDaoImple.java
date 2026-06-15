package com.rbm.dao.implementation;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.RiderDao;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class RiderDaoImple implements RiderDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	Scanner sc = AppUtil.getScanner();
	
	@Override
	public void registerRider() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Rider r = new Rider();

		System.out.println("===============================");
		System.out.println("==                           ==");
		System.out.println("==    Rider REGISTRATION     ==");
		System.out.println("==                           ==");
		System.out.println("===============================");
		System.out.println();

		System.out.print("Enter Your Name: ");
		String name = sc.next();

		System.out.print("Enter Your Email: ");
		String email = sc.next();

		System.out.print("Enter Your Password: ");
		String password = sc.next();

		System.out.print("Enter Your Contact Number: ");
		long contactNumber = sc.nextLong();
		
		r.setName(name);
		r.setEmail(email);
		r.setPassword(password);
		r.setPhoneNUmber(contactNumber);
		
		try {
			et.begin();
			em.persist(r);
			et.commit();
			System.out.println("Rider Registered Successfully!");
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			System.err.println("Registration Failed...!");
		}
		finally {
			em.close();
		}
		
	}

	@Override
	public Rider getRiderById(int riderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ride> getRideHistory(int riderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRider(Rider rider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRider(int riderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewProfile(int riderId) {
		// TODO Auto-generated method stub
		
	}

}
