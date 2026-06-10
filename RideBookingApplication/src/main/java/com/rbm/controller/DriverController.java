package com.rbm.controller;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.dao.implementation.DriverDaoImple;
import com.rbm.entity.Driver;
import com.rbm.service.DriverServices;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;



public class DriverController {
	DriverDao dao = new DriverDaoImple();
	Scanner sc = AppUtil.getScanner();
	
	
	public void driverRegistration() {
		dao.registerDriver();
	}
	
	public void loginAsDriver(String email, String password) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Driver> query = builder.createQuery(Driver.class);
		Root<Driver> root = query.from(Driver.class);
		query.select(root).where(
				builder.and(
						builder.equal(root.get("email"), email),
						builder.equal(root.get("password"), password)
						)
				);
		List<Driver> list = em.createQuery(query).getResultList();
		
		if(list.isEmpty()) {
			System.err.println("Invalid Credentials...!");
		}else {
			System.out.println();
			System.out.println("Login Successfull...!");
			System.out.println();
			Driver driver = list.get(0);
			System.out.println("Hello "+driver.getName()+" 👋");
			DriverServices ds = new DriverServices();
			ds.driverDashBoard(driver.getUserId());
		}
	}
	
	
}
