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
	@Override
	public void addVehicle(Vehicle vehicle) {
		EntityManager em = emf.createEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();

	        em.persist(vehicle);

	        et.commit();

	        System.out.println("Vehicle Added Successfully!");

	    } catch (Exception e) {

	        if(et.isActive()) {
	            et.rollback();
	        }

	        System.err.println("Adding Vehicle Failed...!");

	    } finally {
	        em.close();
	    }
		
	}

	@Override
	public Vehicle getVehicleById(int vehicleId) {

	    EntityManager em = emf.createEntityManager();

	    Vehicle vehicle = em.find(Vehicle.class, vehicleId);

	    em.close();

	    return vehicle;
	}


	@Override
	public boolean updateVehicle(Vehicle vehicle) {
		EntityManager em = emf.createEntityManager();
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
	public void deleteVehicle(int vehicleId) {
		EntityManager em = emf.createEntityManager();
	    EntityTransaction et = em.getTransaction();

	    Vehicle vehicle = em.find(Vehicle.class, vehicleId);

	    if(vehicle == null) {
	        System.err.println("Vehicle Not Found!");
	        em.close();
	        return;
	    }

	    try {

	        et.begin();

	        em.remove(vehicle);

	        et.commit();

	        System.out.println("Vehicle Deleted Successfully!");

	    } catch (Exception e) {

	        if(et.isActive()) {
	            et.rollback();
	        }

	        System.err.println("Deleting Vehicle Failed...!");

	    } finally {
	        em.close();
	    }
		
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		 EntityManager em = emf.createEntityManager();

		    String jpql = "SELECT v FROM Vehicle v";

		    TypedQuery<Vehicle> query = em.createQuery(jpql, Vehicle.class);

		    List<Vehicle> vehicles = query.getResultList();

		    em.close();

		    return vehicles;
	}

}
