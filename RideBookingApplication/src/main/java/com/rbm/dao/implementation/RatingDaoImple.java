package com.rbm.dao.implementation;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.RatingDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Rating;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.enums.RideStatus;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class RatingDaoImple implements RatingDao{

	Scanner sc = AppUtil.getScanner();
	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

	@Override
	public void giveRating(int rideId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Ride ride = em.find(Ride.class, rideId);

		if(ride == null) {
			System.err.println("Ride Not Found!");
			return;
		}
		if (ride.getRating() != null) {
			System.out.println("This ride has already been rated.");
			return;
		}

		if(ride.getRideStatus() != RideStatus.COMPLETED) {
			System.err.println("Ride Must Be Completed Before Rating!");
			return;
		}

		Rating rating = new Rating();

		System.out.print("Enter Rating (1-5): ");
		int stars = sc.nextInt();

		sc.nextLine();

		System.out.print("Enter Review : ");
		String review = sc.nextLine();

		rating.setStars(stars);
		rating.setReview(review);

		rating.setRide(ride);
		rating.setDriver(ride.getDriver());
		rating.setRider(ride.getRider());

		Driver driver = ride.getDriver();
		Rider rider = ride.getRider();

		driver.setTotalRatings(driver.getTotalRatings() + 1);
		driver.getRatingReceived().add(rating);
		rider.getRatingsGiven().add(rating);
		ride.setRating(rating);

		double newAverage = (driver.getAverageRating() * (driver.getTotalRatings() - 1) + stars) / driver.getTotalRatings();

		driver.setAverageRating(newAverage);

		try {

			et.begin();

			em.persist(rating);

			ride.setRating(rating);
			em.merge(ride);
			em.merge(driver);

			et.commit();

			System.out.println("Rating Added Successfully!");

		} catch(Exception e) {

			if(et.isActive()) {
				et.rollback();
			}

			e.printStackTrace();

		} finally {
			em.close();
		}

	}

	@Override
	public Rating getRatingById(int ratingId) {
		EntityManager em = emf.createEntityManager();

		Rating rating = em.find(Rating.class, ratingId);

		em.close();

		return rating;
	}

	@Override
	public List<Rating> getDriverRatings(int driverId) {
		EntityManager em = emf.createEntityManager();

		TypedQuery<Rating> query = em.createQuery("SELECT r FROM Rating r WHERE r.driver.userId = :driverId",Rating.class);

		query.setParameter("driverId", driverId);

		List<Rating> ratings = query.getResultList();

		em.close();

		return ratings;
	}

	@Override
	public Double getAverageDriverRating(int driverId) {
		EntityManager em = emf.createEntityManager();

		TypedQuery<Double> query = em.createQuery("SELECT AVG(r.stars) FROM Rating r WHERE r.driver.userId = :driverId", Double.class);

		query.setParameter("driverId", driverId);

		Double average = query.getSingleResult();

		em.close();

		return average == null ? 0.0 : average;
	}

}
