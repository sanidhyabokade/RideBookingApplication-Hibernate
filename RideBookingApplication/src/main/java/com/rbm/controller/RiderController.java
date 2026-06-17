package com.rbm.controller;

import com.rbm.dao.RiderDao;
import com.rbm.dao.implementation.RiderDaoImple;

public class RiderController {
	RiderDao dao = new RiderDaoImple();
	
	public void riderLogin(String email, String password) {
		dao.loginAsRider(email, password);
	}
	
	public void registerRider() {
		dao.registerRider();
	}
}
