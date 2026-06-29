package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Driver;
import com.rbm.entity.Ride;

public interface RideDao {

	boolean bookRide(Ride ride);

	Ride getRideById(int rideId);

	List<Ride> getAllRides();

	List<Ride> getPendingRides();

	List<Ride> getAcceptedRides();

	List<Ride> getCompletedRides();

	List<Ride> getCancelledRides();

	boolean cancelRide(int rideId);

	boolean completeRide(Ride ride, Driver driver);

	List<Ride> viewAvailableRides();

	boolean acceptRide(Driver driver, Ride ride);

	Ride viewCurrentRide(int driverId);

	boolean startRide(int rideId);
}
