package com.rbm.dao.implementation;

import java.util.List;

import com.rbm.dao.DriverDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;



public class DriverDaoImple implements DriverDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	

	@Override
	public boolean registerDriver(Driver driver) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.persist(driver);
			et.commit();
			return true;
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			return false;
		}
		finally {
			em.close();
		}
	}

	@Override
	public Driver getDriverById(int driverId) {
		EntityManager em = emf.createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		em.close();
		return driver;
	}

	@Override
	public List<Ride> getDriverRideHistory(int driverId) {
		EntityManager em = emf.createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		List<Ride> rides = driver.getRides();
		em.close();
		return rides;
	}

	@Override
	public boolean updateDriver(Driver driver) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		try {
			et.begin();
			em.merge(driver);
			et.commit();
			return true;
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			return false;
		}
		finally {
			em.close();
		}
		
	}

	@Override
	public boolean changeAvailability(int driverId, DriverAvailablity availablity) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Driver driver = em.find(Driver.class, driverId);
		driver.setDriverAvailability(availablity);
		try {
			et.begin();
			em.merge(driver);
			et.commit();
			return true;
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			return false;
		}
		finally {
			em.close();
		}
	}

	@Override
	public boolean deleteDriver(int driverId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Driver driver = em.find(Driver.class, driverId);
		try {
			et.begin();
			em.remove(driver);
			et.commit();
			return true;
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			return false;
		}
		finally {
			em.close();
		}

	}

	@Override
	public List<Driver> loginAsDriver(String email, String password) {
		EntityManager em = emf.createEntityManager();
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
		return list;
		
	}

	@Override
	public int getTotalRides(int driverId) {
		EntityManager em = emf.createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		int size = driver.getRides().size();
		return size;
	}

	@Override
	public double getTotalRevenue(int driverId) {
		EntityManager em = emf.createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		double earnings = driver.getTotalEarnings();
		return earnings;
	}
	
	
}
