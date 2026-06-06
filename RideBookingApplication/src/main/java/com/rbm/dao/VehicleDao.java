package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Vehicle;

public interface VehicleDao {

	void addVehicle(Vehicle vehicle);
	
	Vehicle getVehicleById(int vehicleId);
	
	List<Vehicle> getVehiclesByDriver(int driverId);
	
	void updateVehicle(Vehicle vehicle);
	
	void deleteVehicle(int vehicleId);
}
