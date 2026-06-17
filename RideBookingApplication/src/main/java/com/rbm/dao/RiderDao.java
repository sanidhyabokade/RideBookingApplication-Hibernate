package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Ride;
import com.rbm.entity.Rider;

public interface RiderDao {
	
	void registerRider();
	
	Rider getRiderById(int riderId);
	
	List<Ride> getRideHistory(int riderId);
	
	void loginAsRider(String email, String password);
	
	void updateRider(int riderId);
	
	void deleteRider(int riderId);
	
	void viewProfile(int riderId);
}
