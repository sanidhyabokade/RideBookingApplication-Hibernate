package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Ride;

public interface RideDao {

	void bookRide(Ride ride);
	
	Ride getRideById(int rideId);
	
	List<Ride> getAllRides();
	
	List<Ride> getPendingRides();
	
	List<Ride> getAcceptedRides();
	
	List<Ride> getCompletedRides();
	
	List<Ride> getCancelledRides();
	
	void assignDriver(int rideId, int driverId);
	
	void cancelRide(int rideId);
	
	void completeRide(int rideId);
}
