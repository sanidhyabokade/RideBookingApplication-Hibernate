package com.rbm.dao.implementation;

import java.time.LocalDateTime;
import java.util.List;

import com.rbm.dao.RideDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.RideStatus;
import com.rbm.exception.RideNotFoundException;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RideDaoImple implements RideDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

	private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
	
	@Override
	public boolean bookRide(Ride ride) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();

			try {
				et.begin();
				em.persist(ride);
				et.commit();
				return true;
			} catch (PersistenceException e) {
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
	public Ride getRideById(int rideId) {
		EntityManager em = getEntityManager();
		Ride ride = em.find(Ride.class, rideId);
		em.close();
		return ride;
	}

	@Override
	public List<Ride> getAllRides() {
		EntityManager em = getEntityManager();
		TypedQuery<Ride> typedQuery = em.createQuery("SELECT r FROM Ride r",Ride.class);
		List<Ride> list = typedQuery.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Ride> getPendingRides() {
		EntityManager em = getEntityManager();
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
		EntityManager em = getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Ride> query = builder.createQuery(Ride.class);
		Root<Ride> root = query.from(Ride.class);
		query.select(root).where(builder.equal(root.get("rideStatus"), RideStatus.ACCEPTED));

		List<Ride> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Ride> getCompletedRides() {
		EntityManager em = getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Ride> query = builder.createQuery(Ride.class);
		Root<Ride> root = query.from(Ride.class);
		query.select(root).where(builder.equal(root.get("rideStatus"), RideStatus.COMPLETED));

		List<Ride> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Ride> getCancelledRides() {
		EntityManager em = getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Ride> query = builder.createQuery(Ride.class);
		Root<Ride> root = query.from(Ride.class);
		query.select(root).where(builder.equal(root.get("rideStatus"), RideStatus.CANCELLED));

		List<Ride> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	@Override
	public boolean cancelRide(int rideId) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Ride ride = em.find(Ride.class, rideId);
		if (ride == null) {
		    throw new RideNotFoundException(
		        "Ride not found with ID: " + rideId
		    );
		}
		ride.setRideStatus(RideStatus.CANCELLED);
		try {
			et.begin();
			em.merge(ride);
			et.commit();
			return true;
		} catch (PersistenceException e) {
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
	public boolean completeRide(Ride ride, Driver driver) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.merge(driver);
			em.merge(ride);
			et.commit();
			return true;
		} catch (PersistenceException e) {

			if(et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
			return false;

		} finally {
			em.close();
		}
	}

	@Override
	public List<Ride> viewAvailableRides() {
		EntityManager em = getEntityManager();
		TypedQuery<Ride> query = em.createQuery("SELECT r FROM Ride r WHERE r.ridestatus = :status",Ride.class);
		query.setParameter("status", RideStatus.REQUESTED);
		List<Ride> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public boolean acceptRide(Driver driver, Ride ride) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();

		

		ride.setDriver(driver);
		ride.setRideStatus(RideStatus.ACCEPTED);
		driver.setDriverAvailability(DriverAvailablity.ON_RIDE);

		try {
			et.begin();
			em.merge(ride);
			em.merge(driver);
			et.commit();
			return true;
		} catch (PersistenceException e) {
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
	public Ride viewCurrentRide(int driverId) {
		EntityManager em = getEntityManager();
		try {

			TypedQuery<Ride> query = em.createQuery("SELECT r FROM Ride r WHERE r.driver.userId = :driverId AND (r.rideStatus = :accepted OR r.rideStatus = :started)", Ride.class);

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

	

	@Override
	public boolean startRide(int rideId) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			Ride ride = em.find(Ride.class, rideId);
			et.begin();
			ride.setRideStatus(RideStatus.STARTED);
			ride.setStartTime(LocalDateTime.now());
			em.merge(ride);
			et.commit();
			return true;

		} catch (PersistenceException e) {

			if(et.isActive()) {
				et.rollback();
			}
			return false;

		} finally {
			em.close();
		}
	}
}
