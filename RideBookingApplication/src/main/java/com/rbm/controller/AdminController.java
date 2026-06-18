package com.rbm.controller;

import java.util.Scanner;

import com.rbm.dao.AdminDao;
import com.rbm.dao.implementation.AdminDaoImple;
import com.rbm.util.AppUtil;

public class AdminController {
	Scanner sc = AppUtil.getScanner();
	AdminDao dao = new AdminDaoImple();
	
	public void adminLogin(String email, String password) {
		dao.loginAsAdmin(email, password);
	}
	
	
}
