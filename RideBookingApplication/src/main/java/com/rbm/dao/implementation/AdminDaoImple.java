package com.rbm.dao.implementation;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.AdminDao;
import com.rbm.entity.Admin;
import com.rbm.entity.Driver;
import com.rbm.entity.Payment;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.enums.RideStatus;
import com.rbm.service.AdminServices;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AdminDaoImple implements AdminDao {

	Scanner sc = AppUtil.getScanner();
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
	public void deleteRider(int riderId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Rider rider = em.find(Rider.class, riderId);
		try {
			et.begin();
			em.remove(rider);
			et.commit();
			System.out.println("Rider Removed Successfully!");
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			System.err.println("Removing Rider Failed...!");
		}
		finally {
			em.close();
		}

	}

	@Override
	public void deleteDriver(int driverId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Admin driver = em.find(Admin.class, driverId);
		try {
			et.begin();
			em.remove(driver);
			et.commit();
			System.out.println("Driver Removed Successfully!");
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			System.err.println("Removing Driver Failed...!");
		}
		finally {
			em.close();
		}

	}

	@Override
	public void viewAdminProfile() {
		EntityManager em = emf.createEntityManager();
		Admin admin = em.find(Admin.class, 2);
		System.out.println("======== ADMIN PROFILE ========");
		System.out.println("ID      : " + admin.getUserId());
		System.out.println("Name    : " + admin.getName());
		System.out.println("Email   : " + admin.getEmail());
		System.out.println("Phone   : " + admin.getPhoneNUmber());
		System.out.println();

	}

	@Override
	public void updateAdmin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Admin admin = em.find(Admin.class, 2);

		while(true) {
			System.out.println("Press 1 to Update Email");
			System.out.println("Press 2 to Update Password");
			System.out.println("Press 3 to Update Contact Number");
			System.out.println("Press 4 to go back to previous menu");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.print("Enter New Email: ");
				String email = sc.next();
				admin.setEmail(email);
				try {
					et.begin();
					em.merge(admin);
					et.commit();
					System.out.println("Email Updated Successfully!");
				} catch (Exception e) {
					if(et.isActive()) {
						et.rollback();
					}
					System.err.println("Updating Email Failed...!");
				}
				finally {
					em.close();
				}
				break;
			case 2:
				System.out.print("Enter New Password: ");
				String password = sc.next();
				admin.setPassword(password);
				try {
					et.begin();
					em.merge(admin);
					et.commit();
					System.out.println("Password Updated Successfully!");
				} catch (Exception e) {
					if(et.isActive()) {
						et.rollback();
					}
					System.err.println("Updating Password Failed...!");
				}
				finally {
					em.close();
				}
				break;
			case 3:
				System.out.print("Enter New Contact Number: ");
				long phoneNum = sc.nextLong();
				admin.setPhoneNUmber(phoneNum);
				try {
					et.begin();
					em.merge(admin);
					et.commit();
					System.out.println("Contact Number Updated Successfully!");
				} catch (Exception e) {
					if(et.isActive()) {
						et.rollback();
					}
					System.err.println("Updating Contact Number Failed...!");
				}
				finally {
					em.close();
				}
				break;
			case 4:
				return;

			default:
				System.err.println("Invaid Choice...!");
				break;
			}
		}

	}

	@Override
	public Double getTotalRevenue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void loginAsAdmin(String email, String password) {
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
		
		if(list.isEmpty()) {
			System.err.println("Invalid Credentials...!");
		}else {
			System.out.println();
			System.out.println("Login Successfull...!");
			System.out.println();
			Admin admin = list.get(0);
			String greetings = "Hello "+admin.getName()+" 👋";
			AdminServices as = new AdminServices();
			as.adminDashboard(admin.getUserId(), greetings);
		}
		
	}

}
