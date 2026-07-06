package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Admin;
import com.rbm.entity.Driver;
import com.rbm.entity.Payment;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;

public interface AdminDao {
	
	List<Admin> loginAsAdmin(String email, String password);
	
	List<Rider> getAllRiders();
	
	List<Driver> getAllDrivers();
	
	List<Ride> getAllRides();
	
	List<Ride> getPendingRides();
	
	List<Ride> getAcceptedRides();
	
	List<Ride> getCompletedRides();
	
	long getTotalDrivers();
	
	long getTotalRiders();
	
	long getCompletedRideCount();
	
	long getCancelledRideCount();
	
	double getRevenue();
	
	Driver getTopRatedDriver();
	
	List<Ride> getCancelledRides();
	
	List<Payment> getAllPayments();
	
	boolean deleteRider(int riderId);

    boolean deleteDriver(int driverId);
	
	Admin viewAdminProfile();
	
	boolean updateAdmin(Admin admin);
	
	Double getTotalRevenue();
	
	Admin getAdminById(int adminId);
}
