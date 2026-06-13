package com.rbm.dao.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.rbm.dao.RideDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.RideStatus;
import com.rbm.enums.VehicleType;
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
	public void bookRide(int riderId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {

			Rider rider = em.find(Rider.class, riderId);

			if(rider == null) {

				System.err.println("Rider Not Found!");
				return;
			}

			Ride ride = new Ride();

			System.out.println("===============================");
			System.out.println("==                           ==");
			System.out.println("==         BOOK RIDE         ==");
			System.out.println("==                           ==");
			System.out.println("===============================");
			System.out.println();

			System.out.print("Enter Pickup Location: ");
			String pickUpLoc = sc.next();

			System.out.print("Enter Destination: ");
			String destination = sc.next();

			System.out.print("Enter Distance(km): ");
			double distance = sc.nextDouble();

			VehicleType vehicleType = chooseVehicleType();

			double fare = AppUtil.calculateFare(distance, vehicleType);

			ride.setPickUpLoc(pickUpLoc);
			ride.setDestination(destination);
			ride.setDistance(distance);
			ride.setFare(fare);
			ride.setBookingTime(LocalDateTime.now());
			ride.setRideStatus(RideStatus.REQUESTED);
			ride.setRider(rider);
			ride.setVehicleType(vehicleType);

			et.begin();

			em.persist(ride);

			et.commit();

			System.out.println("Ride Booked Successfully");

			System.out.println("Ride Id : "+ ride.getRideId());

			System.out.println("Estimated Fare : ₹"+ fare);

		} catch (Exception e) {
			if(et.isActive()) {
	            et.rollback();
	        }

	        e.printStackTrace();
		}
		finally {
			em.close();
		}



	}

	@Override
	public Ride getRideById(int rideId) {
		EntityManager em = emf.createEntityManager();
		Ride ride = em.find(Ride.class, rideId);
		return ride;
	}

	@Override
	public List<Ride> getAllRides() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Ride> typedQuery = em.createQuery("SELECT r ride FROM ride r",Ride.class);
		List<Ride> list = typedQuery.getResultList();
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

	private VehicleType chooseVehicleType() {

		while(true) {

			System.out.println("1.BIKE");
			System.out.println("2.AUTO");
			System.out.println("3.MINI");
			System.out.println("4.SEDAN");
			System.out.println("5.SUV");

			int choice = sc.nextInt();

			switch(choice) {

			case 1:
				return VehicleType.BIKE;

			case 2:
				return VehicleType.AUTO;

			case 3:
				return VehicleType.MINI;

			case 4:
				return VehicleType.SEDAN;

			case 5:
				return VehicleType.SUV;

			default:
				System.out.println("Invalid Choice!");
			}
		}
	}
}
