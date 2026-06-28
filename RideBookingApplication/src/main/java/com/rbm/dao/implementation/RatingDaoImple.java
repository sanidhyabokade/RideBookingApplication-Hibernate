package com.rbm.dao.implementation;

import java.util.List;

import com.rbm.dao.RatingDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Rating;
import com.rbm.entity.Ride;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class RatingDaoImple implements RatingDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

	@Override
	public boolean giveRating(Rating rating, Ride ride, Driver driver) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		
		try {

			et.begin();

			em.persist(rating);
			em.merge(ride);
			em.merge(driver);

			et.commit();

			return true;

		} catch(Exception e) {

			if(et.isActive()) {
				et.rollback();
			}
			return false;
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
