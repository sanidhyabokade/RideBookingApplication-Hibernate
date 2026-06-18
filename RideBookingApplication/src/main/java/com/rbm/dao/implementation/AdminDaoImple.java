package com.rbm.dao.implementation;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.AdminDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Payment;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.enums.RideStatus;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AdminDaoImple implements AdminDao {

	Scanner sc = AppUtil.getScanner();
	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	
	@Override
	public List<Rider> getAllRiders() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Rider> query = em.createQuery("SELECT r FROM Rider r", Rider.class);
		List<Rider> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Driver> getAllDrivers() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d", Driver.class);
		List<Driver> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Ride> getAllRides() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Ride> query = em.createQuery("SELECT r FROM Ride r", Ride.class);
		List<Ride> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Ride> getPendingRides() {
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Ride> query = builder.createQuery(Ride.class);
		Root<Ride> root = query.from(Ride.class);
		query.select(root).where(builder.equal(root.get("rideStatus"), RideStatus.REQUESTED));

		List<Ride> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Ride> getAcceptedRides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ride> getCompletedRides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ride> getCancelledRides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllPayments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRider(int riderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDriver(int driverId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewAdminProfile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAdmin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Double getTotalRevenue() {
		// TODO Auto-generated method stub
		return null;
	}

}
