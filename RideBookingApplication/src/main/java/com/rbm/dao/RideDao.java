package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Ride;

public interface RideDao {

	void bookRide(int riderId);
	
	Ride getRideById(int rideId);
	
	List<Ride> getAllRides();
	
	List<Ride> getPendingRides();
	
	List<Ride> getAcceptedRides();
	
	List<Ride> getCompletedRides();
	
	List<Ride> getCancelledRides();
	
	void assignDriver(int rideId, int driverId);
	
	void cancelRide(int rideId);
	
	void completeRide(int rideId);
	
	List<Ride> viewAvailableRides();
	
	void acceptRide(int driverId, int rideId);
	
	Ride viewCurrentRide(int driverId);
}
