package com.rbm.entity;

import java.time.LocalDateTime;

import com.rbm.enums.RideStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;

@Entity
public class Ride {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rideId;
	private String pickUpLoc;
	private String destination;
	private double distance;
	private int duration;
	private double fare;
	private LocalDateTime bookingTime;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private RideStatus rideStatus;
	
	@Version
	private int version;
	
	@ManyToOne
	@JoinColumn(name="rider_id")
	private Rider rider;
	
	@ManyToOne
	@JoinColumn(name="driver_id")
	private Driver driver;
	
	@OneToOne(
			cascade = CascadeType.ALL,
		    fetch = FetchType.EAGER
		    )
	@JoinColumn(name="payment_id")
	private Payment payment;
	
	@OneToOne(
			cascade = CascadeType.ALL,
		    fetch = FetchType.EAGER
			)
	@JoinColumn(name = "rating_id")
	private Rating rating;
	
	@ManyToOne(
			fetch = FetchType.EAGER
			)
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public String getPickUpLoc() {
		return pickUpLoc;
	}

	public void setPickUpLoc(String pickUpLoc) {
		this.pickUpLoc = pickUpLoc;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public RideStatus getRideStatus() {
		return rideStatus;
	}

	public void setRideStatus(RideStatus rideStatus) {
		this.rideStatus = rideStatus;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Rider getRider() {
		return rider;
	}

	public void setRider(Rider rider) {
		this.rider = rider;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
	
}
