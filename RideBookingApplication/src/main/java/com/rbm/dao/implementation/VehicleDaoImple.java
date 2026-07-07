package com.rbm.dao.implementation;

import java.util.List;

import com.rbm.dao.VehicleDao;
import com.rbm.entity.Vehicle;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class VehicleDaoImple implements VehicleDao{
	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	
	private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
	
	@Override
	public boolean addVehicle(Vehicle vehicle) {
		EntityManager em = getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();
	        em.persist(vehicle);
	        et.commit();
	        return true;
	    } catch (Exception e) {

	        if(et.isActive()) {
	            et.rollback();
	        }
	        return false;
	    } finally {
	        em.close();
	    }
		
	}

	@Override
	public Vehicle getVehicleById(int vehicleId) {

		EntityManager em = getEntityManager();

	    Vehicle vehicle = em.find(Vehicle.class, vehicleId);

	    em.close();

	    return vehicle;
	}


	@Override
	public boolean updateVehicle(Vehicle vehicle) {
		EntityManager em = getEntityManager();
	    EntityTransaction et = em.getTransaction();
	    
	    try {

	        et.begin();

	        em.merge(vehicle);

	        et.commit();

	        return true;

	    } catch (Exception e) {

	        if(et.isActive()) {
	            et.rollback();
	        }

	        return false;

	    } finally {
	        em.close();
	    }
		
	}

	@Override
	public boolean deleteVehicle(int vehicleId) {
		EntityManager em = getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    Vehicle vehicle = em.find(Vehicle.class, vehicleId);

	    if(vehicle == null) {
	        em.close();
	        return false;
	    }

	    try {
	        et.begin();
	        em.remove(vehicle);
	        et.commit();
	        return true;
	    } catch (Exception e) {

	        if(et.isActive()) {
	            et.rollback();
	        }
	        return false;
	    } finally {
	        em.close();
	    }
		
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		EntityManager em = getEntityManager();

		    String jpql = "SELECT v FROM Vehicle v";

		    TypedQuery<Vehicle> query = em.createQuery(jpql, Vehicle.class);

		    List<Vehicle> vehicles = query.getResultList();

		    em.close();

		    return vehicles;
	}

}
