package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Ride;
import com.rbm.entity.Rider;

public interface RiderDao {
	
	void registerRider(Rider rider);
	
	Rider getRiderById(int riderId);
	
	List<Ride> getRideHistory(int riderId);
	
	void updateRider(Rider rider);
	
	void deleteRider(int riderId);
}
