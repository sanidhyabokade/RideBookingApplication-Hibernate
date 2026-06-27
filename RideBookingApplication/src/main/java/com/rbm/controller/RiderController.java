package com.rbm.controller;

import java.util.Scanner;

import com.rbm.dao.RiderDao;
import com.rbm.dao.implementation.RiderDaoImple;
import com.rbm.entity.Rider;
import com.rbm.util.AppUtil;

public class RiderController {
	Scanner sc = AppUtil.getScanner();
	RiderDao dao = new RiderDaoImple();
	
	public void riderLogin(String email, String password) {
		dao.loginAsRider(email, password);
	}
	
	public void registerRider() {
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
		
		Rider rider = new Rider();
		rider.setName(name);
		rider.setEmail(email);
		rider.setPassword(password);
		rider.setPhoneNUmber(contactNumber);
		
		dao.registerRider(rider);
	}
}
