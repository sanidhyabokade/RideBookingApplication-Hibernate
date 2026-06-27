package com.rbm.dao.implementation;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;
import com.rbm.service.DriverServices;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;



public class DriverDaoImple implements DriverDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	Scanner sc = AppUtil.getScanner();

	@Override
	public void registerDriver(Driver driver) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.persist(driver);
			et.commit();
			System.out.println("Driver Registered Successfully!");
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			System.err.println("Registration Failed...!");
		}
		finally {
			em.close();
		}
	}

	@Override
	public Driver getDriverById(int driverId) {
		EntityManager em = emf.createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		em.close();
		return driver;
	}

	@Override
	public List<Ride> getDriverRideHistory(int driverId) {
		EntityManager em = emf.createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		List<Ride> rides = driver.getRides();
		em.close();
		return rides;
	}

	@Override
	public void updateDriver(int driverId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Driver driver = em.find(Driver.class, driverId);
		
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
				driver.setEmail(email);
				try {
					et.begin();
					em.merge(driver);
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
				driver.setPassword(password);
				try {
					et.begin();
					em.merge(driver);
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
				driver.setPhoneNUmber(phoneNum);
				try {
					et.begin();
					em.merge(driver);
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
	public void changeAvailability(int driverId, DriverAvailablity availablity) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Driver driver = em.find(Driver.class, driverId);
		driver.setDriverAvailability(availablity);
		try {
			et.begin();
			em.merge(driver);
			et.commit();
			System.out.println("Driver Availability Changed Successfully!");
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			System.err.println("Changing Driver Availability Failed...!");
		}
		finally {
			em.close();
		}
	}

	@Override
	public void deleteDriver(int driverId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Driver driver = em.find(Driver.class, driverId);
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
	public void loginAsDriver(String email, String password) {
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Driver> query = builder.createQuery(Driver.class);
		Root<Driver> root = query.from(Driver.class);
		query.select(root).where(
				builder.and(
						builder.equal(root.get("email"), email),
						builder.equal(root.get("password"), password)
						)
				);
		List<Driver> list = em.createQuery(query).getResultList();
		
		if(list.isEmpty()) {
			System.err.println("Invalid Credentials...!");
		}else {
			System.out.println();
			System.out.println("Login Successfull...!");
			System.out.println();
			Driver driver = list.get(0);
			String greetings = "Hello "+driver.getName()+" 👋";
			DriverServices ds = new DriverServices();
			ds.driverDashBoard(driver.getUserId(), greetings);
		}
		
	}

	@Override
	public int getTotalRides(int driverId) {
		EntityManager em = emf.createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		int size = driver.getRides().size();
		return size;
	}

	@Override
	public double getTotalRevenue(int driverId) {
		EntityManager em = emf.createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		double earnings = driver.getTotalEarnings();
		return earnings;
	}
	
	
}
