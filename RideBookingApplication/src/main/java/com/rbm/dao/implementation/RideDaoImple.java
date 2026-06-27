package com.rbm.dao.implementation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.rbm.dao.RatingDao;
import com.rbm.dao.RideDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Payment;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.PaymentMethod;
import com.rbm.enums.PaymentStatus;
import com.rbm.enums.RideStatus;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RideDaoImple implements RideDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	Scanner sc = AppUtil.getScanner();

	@Override
	public void bookRide(Ride ride) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

			try {
				et.begin();
				em.persist(ride);
				et.commit();
				System.out.println("Ride Booked Successfully");
				System.out.println("Ride Id : "+ ride.getRideId());
			} catch (Exception e) {
				if(et.isActive()) {
					et.rollback();
				}
				System.err.println("Booking Ride Failed...!");
			}
			finally {
				em.close();
			}
	}

	@Override
	public Ride getRideById(int rideId) {
		EntityManager em = emf.createEntityManager();
		Ride ride = em.find(Ride.class, rideId);
		em.close();
		return ride;
	}

	@Override
	public List<Ride> getAllRides() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Ride> typedQuery = em.createQuery("SELECT r FROM Ride r",Ride.class);
		List<Ride> list = typedQuery.getResultList();
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
		EntityManager em = emf.createEntityManager();
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
		EntityManager em = emf.createEntityManager();
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
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Ride> query = builder.createQuery(Ride.class);
		Root<Ride> root = query.from(Ride.class);
		query.select(root).where(builder.equal(root.get("rideStatus"), RideStatus.CANCELLED));

		List<Ride> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	@Override
	public void cancelRide(int rideId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Ride ride = em.find(Ride.class, rideId);
		ride.setRideStatus(RideStatus.CANCELLED);
		try {
			et.begin();
			em.merge(ride);
			et.commit();
			System.out.println("Ride Cancelled Successfully!");
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			System.err.println("Cancelling Ride Failed...!");
		}
		finally {
			em.close();
		}

	}

	@Override
	public void completeRide(int rideId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {

			Ride ride = em.find(Ride.class, rideId);

			if(ride == null) {
				System.err.println("Ride Not Found!");
				return;
			}

			if(ride.getRideStatus() != RideStatus.STARTED) {
				System.err.println("Ride must be IN_PROGRESS!");
				return;
			}

			Driver driver = ride.getDriver();

			et.begin();

			ride.setRideStatus(RideStatus.COMPLETED);

			LocalDateTime endTime = LocalDateTime.now();

			ride.setEndTime(endTime);

			int duration = (int) Duration.between(
					ride.getStartTime(),
					endTime
					).toMinutes();

			ride.setDuration(duration);

			driver.setTotalEarnings(
					driver.getTotalEarnings()
					+ ride.getFare());

			driver.setTotalRidesCompleted(
					driver.getTotalRidesCompleted()
					+ 1);

			driver.setDriverAvailability(
					DriverAvailablity.ONLINE);

			Payment payment = new Payment();

			payment.setRide(ride);
			payment.setAmount(ride.getFare());

			PaymentMethod method = null;
			System.out.println("*CHOOSE PAYMENT MODE*");


			System.out.println("1.UPI");
			System.out.println("2.CARD");
			System.out.println("3.CASH");

			int choice = sc.nextInt();

			switch(choice) {

			case 1:
				method = PaymentMethod.UPI;
				break;

			case 2:
				method = PaymentMethod.CARD;
				break;

			case 3:
				method = PaymentMethod.CASH;
				break;

			default:
				System.out.println("Invalid Choice!");
				return;
			}


			payment.setPaymentMethod(method);
			payment.setPaymentStatus(PaymentStatus.SUCCESS);

			payment.setPaymentTime(LocalDateTime.now());

			payment.setRide(ride);
			ride.setPayment(payment);

			em.merge(driver);
			em.merge(ride);

			et.commit();

			System.out.println("Ride Completed Successfully!");
			System.out.println("Ride Duration : "
					+ duration + " Minutes");

			System.out.println();
			System.out.println("Would you like to rate your driver?");
			System.out.println("1. Yes");
			System.out.println("2. Skip");
			int choice1 = sc.nextInt();
			switch (choice1) {
			case 1:
				RatingDao rd = new RatingDaoImple();
				rd.giveRating(rideId);
				break;
			case 2:

				return;

			default:
				break;
			}

		} catch (Exception e) {

			if(et.isActive()) {
				et.rollback();
			}

			System.err.println("Completing Ride Failed!");
			e.printStackTrace();

		} finally {
			em.close();
		}
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
	public void startRide(int rideId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {

			Ride ride = em.find(Ride.class, rideId);

			if(ride == null) {
				System.err.println("Ride Not Found!");
				return;
			}

			if(ride.getRideStatus() != RideStatus.ACCEPTED) {
				System.err.println("Only ACCEPTED rides can be started!");
				return;
			}

			et.begin();

			ride.setRideStatus(RideStatus.STARTED);
			ride.setStartTime(LocalDateTime.now());

			em.merge(ride);

			et.commit();

			System.out.println("Ride Started Successfully!");

		} catch (Exception e) {

			if(et.isActive()) {
				et.rollback();
			}

			System.err.println("Starting Ride Failed!");

		} finally {
			em.close();
		}
	}
}
