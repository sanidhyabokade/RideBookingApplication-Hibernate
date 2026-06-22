package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Vehicle;

public interface VehicleDao {

	void addVehicle(Vehicle vehicle);
	
	Vehicle getVehicleById(int vehicleId);
		
	void updateVehicle(int vehicleId);
	
	void deleteVehicle(int vehicleId);
	
	List<Vehicle> getAllVehicles();
}
