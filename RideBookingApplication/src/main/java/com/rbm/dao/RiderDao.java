package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Ride;
import com.rbm.entity.Rider;

public interface RiderDao {
	
	boolean registerRider(Rider rider);
	
	Rider getRiderById(int riderId);
	
	List<Ride> getRideHistory(int riderId);
	
	List<Rider> loginAsRider(String email, String password);
	
	boolean updateRider(Rider rider);
	
	boolean deleteRider(int riderId);
	
	Rider viewProfile(int riderId);
	
	int getTotalRides(int riderId);
}
