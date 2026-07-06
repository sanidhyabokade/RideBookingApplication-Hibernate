package com.rbm.dao.implementation;

import java.util.List;

import com.rbm.dao.AdminDao;
import com.rbm.entity.Admin;
import com.rbm.entity.Driver;
import com.rbm.entity.Payment;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.enums.PaymentStatus;
import com.rbm.enums.RideStatus;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AdminDaoImple implements AdminDao {

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
	public List<Payment> getAllPayments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRider(int riderId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Rider rider = em.find(Rider.class, riderId);
		try {
			et.begin();
			em.remove(rider);
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
		Admin driver = em.find(Admin.class, driverId);
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
	public Admin viewAdminProfile() {
		EntityManager em = emf.createEntityManager();
		Admin admin = em.find(Admin.class, 2);
		return admin;

	}

	@Override
	public boolean updateAdmin(Admin admin) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.merge(admin);
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
	public Double getTotalRevenue() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Admin> loginAsAdmin(String email, String password) {
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Admin> query = builder.createQuery(Admin.class);
		Root<Admin> root = query.from(Admin.class);
		query.select(root).where(
				builder.and(
						builder.equal(root.get("email"), email),
						builder.equal(root.get("password"), password)
						)
				);
		List<Admin> list = em.createQuery(query).getResultList();
		return list;

	}

	@Override
	public Admin getAdminById(int adminId) {
		EntityManager em = emf.createEntityManager();
		Admin admin = em.find(Admin.class, adminId);
		return admin;
	}

	@Override
	public long getTotalDrivers() {

		EntityManager em = emf.createEntityManager();

		try {

			return em.createQuery(
					"SELECT COUNT(d) FROM Driver d",
					Long.class)
					.getSingleResult();

		} finally {
			em.close();
		}
	}

	@Override
	public long getTotalRiders() {
		EntityManager em = emf.createEntityManager();

		try {

			return em.createQuery(
					"SELECT COUNT(r) FROM Rider r",
					Long.class)
					.getSingleResult();

		} finally {
			em.close();
		}
	}

	@Override
	public long getCompletedRideCount() {
		EntityManager em = emf.createEntityManager();

		try {

			return em.createQuery(
					"SELECT COUNT(r) FROM Ride r WHERE r.rideStatus = :status",
					Long.class)
					.setParameter("status", RideStatus.COMPLETED)
					.getSingleResult();

		} finally {
			em.close();
		}
	}

	@Override
	public long getCancelledRideCount() {
		EntityManager em = emf.createEntityManager();

		try {

			return em.createQuery(
					"SELECT COUNT(r) FROM Ride r WHERE r.rideStatus = :status",
					Long.class)
					.setParameter("status", RideStatus.CANCELLED)
					.getSingleResult();

		} finally {
			em.close();
		}
	}

	@Override
	public double getRevenue() {
		EntityManager em = emf.createEntityManager();

		try {

			Double revenue = em.createQuery(
					"SELECT SUM(p.amount) FROM Payment p WHERE p.paymentStatus = :status",
					Double.class)
					.setParameter("status", PaymentStatus.SUCCESS)
					.getSingleResult();

			return revenue == null ? 0.0 : revenue;

		} finally {
			em.close();
		}
	}

	@Override
	public Driver getTopRatedDriver() {
		EntityManager em = emf.createEntityManager();

		try {

			return em.createQuery(
					"SELECT d FROM Driver d ORDER BY d.rating DESC",
					Driver.class)
					.setMaxResults(1)
					.getSingleResult();

		} catch (NoResultException e) {

			return null;

		} finally {

			em.close();

		}
	}

}
