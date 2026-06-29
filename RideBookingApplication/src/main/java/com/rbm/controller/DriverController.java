package com.rbm.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.dao.implementation.DriverDaoImple;
import com.rbm.entity.Driver;
import com.rbm.entity.Vehicle;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.VehicleType;
import com.rbm.service.DriverServices;
import com.rbm.util.AppUtil;



public class DriverController {
	DriverDao dao = new DriverDaoImple();
	Scanner sc = AppUtil.getScanner();
	
	
	public void driverRegistration() {
		System.out.println("===============================");
		System.out.println("==                           ==");
		System.out.println("==    DRIVER REGISTRATION    ==");
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

		System.out.print("Enter Your License Number: ");
		String licenseNum = sc.next();

		System.out.println();
		System.out.println("-------- VEHICLE DETAILS --------");
		System.out.println();

		System.out.print("Enter Your Vehicle Number(MH12PQ5869): ");
		String vehicleNum = sc.next();

		System.out.print("Enter Vehicle Model: ");
		String model = sc.next();

		System.out.print("Enter Your Vehicle Color: ");
		String color = sc.next();

		VehicleType type = null;

		while(type == null) {

			System.out.print("Enter Your Vehicle Type(BIKE, AUTO, MINI, SEDAN, SUV): ");
			String choice = sc.next();

			if(choice.equalsIgnoreCase("BIKE")) {
				type = VehicleType.BIKE;
			}else if(choice.equalsIgnoreCase("AUTO")) {
				type = VehicleType.AUTO;
			}else if(choice.equalsIgnoreCase("MINI")) {
				type = VehicleType.MINI;
			}else if(choice.equalsIgnoreCase("SEDAN")) {
				type = VehicleType.SEDAN;
			}else if(choice.equalsIgnoreCase("SUV")) {
				type = VehicleType.SUV;
			}else {
				System.err.println("Enter A Valid Type...!");
			}
		}

		System.out.print("Enter Seating Capacity Of Your Vehicle: ");
		int capacity = sc.nextInt();
		
		Vehicle v = new Vehicle();
		v.setVehicleNumber(vehicleNum);
		v.setModel(model);
		v.setColor(color);
		v.setSeatingCapacity(capacity);
		v.setVehicleType(type);

		Driver driver = new Driver();
		driver.setName(name);
		driver.setEmail(email);
		driver.setPassword(password);
		driver.setPhoneNUmber(contactNumber);
		driver.setLicenseNumber(licenseNum);
		driver.setDriverAvailability(DriverAvailablity.ONLINE);
		driver.setCreatedAt(LocalDateTime.now());
		driver.setVehicle(v);

		boolean registerDriver = dao.registerDriver(driver);
		if(registerDriver) {
			System.out.println("Driver Registered Successfully!");
		}else {
			System.err.println("Registration Failed...!");
		}
	}
	
	public void driverLogin(String email, String password) {
		List<Driver> loginAsDriver = dao.loginAsDriver(email, password);
		if(loginAsDriver.isEmpty()) {
			System.err.println("Invalid Credentials...!");
		}else {
			System.out.println();
			System.out.println("Login Successfull...!");
			System.out.println();
			Driver driver = loginAsDriver.get(0);
			String greetings = "Hello "+driver.getName()+" 👋";
			DriverServices ds = new DriverServices();
			ds.driverDashBoard(driver.getUserId(), greetings);
		}
	}
}
