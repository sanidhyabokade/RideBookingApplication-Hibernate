package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Driver;
import com.rbm.entity.Payment;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;

public interface AdminDao {
	
	List<Rider> getAllRiders();
	
	List<Driver> getAllDrivers();
	
	List<Ride> getAllRides();
	
	List<Payment> getAllPayments();
	
	void blockDriver(int driverId);
	
	void unblockDriver(int driverId);
	
	void blockRider(int riderId);
	
	Double getTotalRevenue();
}
