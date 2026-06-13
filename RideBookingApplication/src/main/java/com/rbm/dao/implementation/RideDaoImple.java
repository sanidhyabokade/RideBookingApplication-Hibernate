package com.rbm.dao.implementation;

import java.util.List;

import com.rbm.dao.RideDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.RideStatus;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class RideDaoImple implements RideDao{

	@Override
	public void bookRide() {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Ride ride = new Ride();
		
		System.out.println("===============================");
		System.out.println("==                           ==");
		System.out.println("==         BOOK RIDE         ==");
		System.out.println("==                           ==");
		System.out.println("===============================");
		System.out.println();
		
		
		
		
		

	}

	@Override
	public Ride getRideById(int rideId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ride> getAllRides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ride> getPendingRides() {
		// TODO Auto-generated method stub
		return null;
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
	public void assignDriver(int rideId, int driverId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelRide(int rideId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void completeRide(int rideId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Ride> viewAvailableRides() {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		TypedQuery<Ride> query = em.createQuery("SELECT r FROM Ride r WHERE r.ridestatus = :status",Ride.class);
		query.setParameter("status", RideStatus.REQUESTED);
		List<Ride> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public void acceptRide(int driverId, int rideId) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Driver driver = em.find(Driver.class, driverId);
		Ride ride = em.find(Ride.class, rideId);
		
		if(driver == null) {
		    System.err.println("Driver Not Found!");
		    return;
		}

		if(ride == null) {
		    System.err.println("Ride Not Found!");
		    return;
		}
		
		if(ride.getRideStatus() != RideStatus.REQUESTED) {
		    System.err.println("Ride Already Accepted/Completed!");
		    return;
		}
		
		if(ride.getDriver() != null) {
		    System.err.println("Ride Already Assigned!");
		    return;
		}
		
		ride.setDriver(driver);
		ride.setRideStatus(RideStatus.ACCEPTED);
		driver.setDriverAvailability(DriverAvailablity.ON_RIDE);
		
		try {
			et.begin();
			em.merge(ride);
			em.merge(driver);
			et.commit();
			System.out.println("Ride Accepted Successfully!");
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			System.err.println("Accepting Ride Failed...!");
		}
		finally {
			em.close();
		}

	}

	@Override
	public Ride viewCurrentRide(int driverId) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {

			TypedQuery<Ride> query = em.createQuery(
					"SELECT r FROM Ride r " +
							"WHERE r.driver.userId = :driverId " +
							"AND (r.rideStatus = :accepted OR r.rideStatus = :started)",
							Ride.class);

			query.setParameter("driverId", driverId);
			query.setParameter("accepted", RideStatus.ACCEPTED);
			query.setParameter("started", RideStatus.STARTED);

			List<Ride> rides = query.getResultList();

			if(rides.isEmpty()) {
				return null;
			}

			return rides.get(0);

		} finally {
			em.close();
		}
	}
}
