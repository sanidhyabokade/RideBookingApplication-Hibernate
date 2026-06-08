package com.rbm.dao.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.entity.Vehicle;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.VehicleType;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DriverDaoImple implements DriverDao{
	
	Scanner sc = AppUtil.getScanner();

	@Override
	public void registerDriver() {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		System.out.println("======== DRIVER REGISTRATION ========");
		
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
		
		System.out.println("-------- VEHICLE DETAILS --------");
		
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
		
		Driver d = new Driver();
		d.setName(name);
		d.setEmail(email);
		d.setPassword(password);
		d.setPhoneNUmber(contactNumber);
		d.setLicenseNumber(licenseNum);
		d.setDriverAvailability(DriverAvailablity.ONLINE);
		d.setCreatedAt(LocalDateTime.now());
		d.setVehicle(v);
		
		try {
			et.begin();
			em.persist(d);
			et.commit();
			System.out.println("Driver Registered Successfully!");
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
	public Driver getDriverById(int driverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ride> getDriverRideHistory(int driverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDriver(Driver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeAvailability(int driverId, DriverAvailablity availablity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDriver(int driverId) {
		// TODO Auto-generated method stub
		
	}

}
