package com.rbm.entity;

import java.util.List;

import com.rbm.enums.DriverAvailablity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class Driver extends User{

	
	private String licenseNumber;

	private double averageRating;
	private int totalRatings;
	private int totalRidesCompleted;
	private double totalEarnings;
	
	@Enumerated(EnumType.STRING)
	private DriverAvailablity driverAvailability;

	@OneToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
			)
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	@OneToMany(
			mappedBy = "driver",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			fetch = FetchType.LAZY
			)
	private List<Ride> rides;
	
	@OneToMany(
			mappedBy = "driver",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			fetch = FetchType.LAZY
			)
	private List<Rating> ratingReceived;

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public double getAverageRating() {
		return averageRating;
	}


	public int getTotalRatings() {
		return totalRatings;
	}

	public int getTotalRidesCompleted() {
		return totalRidesCompleted;
	}


	public List<Rating> getRatingReceived() {
		return ratingReceived;
	}

	public void setRatingReceived(List<Rating> ratingReceived) {
		this.ratingReceived = ratingReceived;
	}

	public double getTotalEarnings() {
		return totalEarnings;
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
	
	public void completeRide(double fare) {
		totalRidesCompleted++;
		totalEarnings += fare;
	}
	
	public void addRating(int stars) {
		if(stars < 1 || stars > 5) {
			throw new IllegalArgumentException("Rating must be between 1 and 5");
		}
		
		averageRating = ((averageRating * totalRatings) + stars) / (totalRatings + 1);
		
		totalRatings++;
	}
	
	public Driver() {
		this.averageRating = 0.0;
		this.totalRatings = 0;
		this.totalRidesCompleted = 0;
		this.totalEarnings = 0.0;
	}
	
}
