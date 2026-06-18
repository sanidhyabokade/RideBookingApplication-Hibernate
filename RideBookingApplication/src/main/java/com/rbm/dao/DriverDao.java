package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;

public interface DriverDao {
	
	void registerDriver();
	
	void loginAsDriver(String email, String password);
	
	Driver getDriverById(int driverId);
	
	List<Ride> getDriverRideHistory(int driverId);
	
	void updateDriver(int driverId);
	
	void changeAvailability(int driverId, DriverAvailablity availablity);
	
	void deleteDriver(int driverId);
	
	int getTotalRides(int driverId);
	
	double getTotalRevenue(int driverId);
}
