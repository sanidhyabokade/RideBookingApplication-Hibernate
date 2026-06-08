package com.rbm.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int couponId;
	private String couponCode;
	@Min(0)
	@Max(100)
	private double discountPercentage;
	private LocalDate expiryDate;
	private boolean active;
	
	@OneToMany(mappedBy = "coupon")
	private List<Ride> rides;
	
	@ManyToMany(
			mappedBy = "coupons",
			fetch = FetchType.LAZY
			)
	private List<Rider> riders;

	public int getCouponId() {
		return couponId;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}


	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public List<Rider> getRiders() {
		return riders;
	}

	public void setRiders(List<Rider> riders) {
		this.riders = riders;
	}
	
	
}
