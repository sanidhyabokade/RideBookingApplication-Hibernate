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
	
	List<Ride> getPendingRides();
	
	List<Ride> getAcceptedRides();
	
	List<Ride> getCompletedRides();
	
	List<Ride> getCancelledRides();
	
	List<Payment> getAllPayments();
	
	void deleteRider(int riderId);

    void deleteDriver(int driverId);
	
	void viewAdminProfile();
	
	void updateAdmin();
	
	Double getTotalRevenue();
}
