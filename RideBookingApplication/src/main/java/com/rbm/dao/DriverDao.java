package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;

public interface DriverDao {
	
	boolean registerDriver(Driver driver);
	
	void loginAsDriver(String email, String password);
	
	Driver getDriverById(int driverId);
	
	List<Ride> getDriverRideHistory(int driverId);
	
	boolean updateDriver(Driver driver);
	
	boolean changeAvailability(int driverId, DriverAvailablity availablity);
	
	boolean deleteDriver(int driverId);
	
	int getTotalRides(int driverId);
	
	double getTotalRevenue(int driverId);
}
