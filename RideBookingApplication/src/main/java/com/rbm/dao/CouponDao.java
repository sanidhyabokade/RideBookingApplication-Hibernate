package com.rbm.dao;

import java.util.List;

import com.rbm.entity.Coupon;

public interface CouponDao {
	
	void addCoupon(Coupon coupon);
	
	Coupon getCouponByCode(String code);
	
	List<Coupon> getAllCoupons();
	
	void updateCoupon(Coupon coupon);
	
	void deleteCoupon(int couponId);
}
