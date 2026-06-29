package com.rbm.controller;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.RiderDao;
import com.rbm.dao.implementation.RiderDaoImple;
import com.rbm.entity.Rider;
import com.rbm.service.RiderServices;
import com.rbm.util.AppUtil;

public class RiderController {
	Scanner sc = AppUtil.getScanner();
	RiderDao dao = new RiderDaoImple();
	
	public void riderLogin(String email, String password) {
		List<Rider> loginAsRider = dao.loginAsRider(email, password);
		if(loginAsRider.isEmpty()) {
			System.err.println("Invalid Credentials...!");
		}else {
			System.out.println();
			System.out.println("Login Successfull...!");
			System.out.println();
			Rider rider = loginAsRider.get(0);
			String greetings = "Hello "+rider.getName()+" 👋";
			RiderServices rs = new RiderServices();
			rs.riderDashBoard(rider.getUserId(),greetings);
		}
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
		
		boolean registerRider = dao.registerRider(rider);
		if(registerRider) {
			System.out.println("Rider Registered Successfully!");
		}else {
			System.err.println("Registration Failed...!");
		}
	}
}
