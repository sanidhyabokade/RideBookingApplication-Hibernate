package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Vehicle;

public interface VehicleDao {

	boolean addVehicle(Vehicle vehicle);
	
	Vehicle getVehicleById(int vehicleId);
		
	boolean updateVehicle(Vehicle vehicle);
	
	boolean deleteVehicle(int vehicleId);
	
	List<Vehicle> getAllVehicles();
}
