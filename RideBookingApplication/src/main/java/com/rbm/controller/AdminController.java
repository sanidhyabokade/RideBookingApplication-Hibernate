package com.rbm.controller;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.AdminDao;
import com.rbm.dao.implementation.AdminDaoImple;
import com.rbm.entity.Admin;
import com.rbm.service.AdminServices;
import com.rbm.util.AppUtil;

public class AdminController {
	Scanner sc = AppUtil.getScanner();
	AdminDao dao = new AdminDaoImple();
	
	public void adminLogin(String email, String password) {
		List<Admin> admin = dao.loginAsAdmin(email, password);
		if(admin.isEmpty()) {
			System.err.println("Invalid Credentials...!");
		}else {
			System.out.println();
			System.out.println("Login Successfull...!");
			System.out.println();
			Admin admin1 = admin.get(0);
			String greetings = "Hello "+admin1.getName()+" 👋";
			AdminServices as = new AdminServices();
			as.adminDashboard(admin1.getUserId(), greetings);
		}
	}
}
