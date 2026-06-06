package com.rbm.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Rider extends User {
	
	@OneToMany(
			mappedBy = "rider",
			fetch = FetchType.LAZY
			)
	private List<Ride> rides;
	
	@ManyToMany(
			fetch = FetchType.LAZY
			)
	private List<Coupon> coupons;
	
	
	public List<Ride> getRides() {
		return rides;
	}
	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	public List<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
}
