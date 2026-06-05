package com.rbm.Entity;

import java.util.List;

import com.rbm.Enums.DriverAvailablity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class Driver extends Users{

	
	private String licenseNumber;

	private double rating;
	private double totalEarnings;
	private DriverAvailablity driverAvailability;

	@OneToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
			)
	private Vehicle vehicle;

	@OneToMany(mappedBy = "driver")
	private List<Ride> rides;

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public DriverAvailablity getDriverAvailability() {
		return driverAvailability;
	}

	public void setDriverAvailability(DriverAvailablity driverAvailability) {
		this.driverAvailability = driverAvailability;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	
	
}
