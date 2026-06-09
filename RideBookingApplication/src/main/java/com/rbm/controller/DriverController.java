package com.rbm.controller;

import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.dao.implementation.DriverDaoImple;
import com.rbm.util.AppUtil;



public class DriverController {
	DriverDao dao = new DriverDaoImple();
	Scanner sc = AppUtil.getScanner();
	public void driverRegistration() {
		dao.registerDriver();
	}
	
	
	
}
