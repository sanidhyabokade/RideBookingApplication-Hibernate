package com.rbm.dao.implementation;

import java.util.List;

import com.rbm.dao.RiderDao;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.service.RiderServices;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RiderDaoImple implements RiderDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	
	@Override
	public boolean registerRider(Rider rider) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.persist(rider);
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
	public Rider getRiderById(int riderId) {
		EntityManager em = emf.createEntityManager();
		Rider rider = em.find(Rider.class, riderId);
		em.close();
		return rider;
	}

	@Override
	public List<Ride> getRideHistory(int riderId) {
		EntityManager em = emf.createEntityManager();
		Rider rider = em.find(Rider.class, riderId);
		List<Ride> rides = rider.getRides();
		em.close();
		return rides;
	}

	@Override
	public boolean updateRider(Rider rider) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.merge(rider);
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
	public Rider viewProfile(int riderId) {
		Rider rider = getRiderById(riderId);
	    return rider;
		
	}

	@Override
	public List<Rider> loginAsRider(String email, String password) {
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Rider> query = builder.createQuery(Rider.class);
		Root<Rider> root = query.from(Rider.class);
		query.select(root).where(
				builder.and(
						builder.equal(root.get("email"), email),
						builder.equal(root.get("password"), password)
						)
				);
		List<Rider> list = em.createQuery(query).getResultList();
		return list;
		
	}

	@Override
	public int getTotalRides(int riderId) {
		EntityManager em = emf.createEntityManager();
		Rider rider = em.find(Rider.class, riderId);
		int size = rider.getRides().size();
		return size;
	}

}
