package com.rbm.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Rider extends User {
	
	@OneToMany(
			mappedBy = "rider",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			fetch = FetchType.LAZY
			)
	private List<Ride> rides;
	
	@ManyToMany(
			cascade = {CascadeType.PERSIST,CascadeType.MERGE},
			fetch = FetchType.LAZY
			)
	@JoinTable(
			name = "rider_coupon",
			joinColumns = @JoinColumn(name = "rider_id"),
			inverseJoinColumns = @JoinColumn(name = "coupon_id")
			)
	private List<Coupon> coupons;
	
	@OneToMany(
			mappedBy = "rider",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
			)
	private List<Rating> ratingsGiven;
	
	public List<Rating> getRatingsGiven() {
		return ratingsGiven;
	}
	public void setRatingsGiven(List<Rating> ratingsGiven) {
		this.ratingsGiven = ratingsGiven;
	}
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
