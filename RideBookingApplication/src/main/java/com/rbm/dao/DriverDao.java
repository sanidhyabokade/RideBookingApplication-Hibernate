package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;

public interface DriverDao {
	
	void registerDriver();
	
	Driver getDriverById(int driverId);
	
	List<Ride> getDriverRideHistory(int driverId);
	
	List<Ride> viewAvailableRides();
	
	void updateDriver(int driverId);
	
	void changeAvailability(int driverId, DriverAvailablity availablity);
	
	void deleteDriver(int driverId);
}
