package com.rbm.dao.implementation;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.RiderDao;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.service.RiderServices;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RiderDaoImple implements RiderDao{

	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	Scanner sc = AppUtil.getScanner();
	
	@Override
	public void registerRider() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Rider r = new Rider();

		System.out.println("===============================");
		System.out.println("==                           ==");
		System.out.println("==    Rider REGISTRATION     ==");
		System.out.println("==                           ==");
		System.out.println("===============================");
		System.out.println();

		System.out.print("Enter Your Name: ");
		String name = sc.next();

		System.out.print("Enter Your Email: ");
		String email = sc.next();

		System.out.print("Enter Your Password: ");
		String password = sc.next();

		System.out.print("Enter Your Contact Number: ");
		long contactNumber = sc.nextLong();
		
		r.setName(name);
		r.setEmail(email);
		r.setPassword(password);
		r.setPhoneNUmber(contactNumber);
		
		try {
			et.begin();
			em.persist(r);
			et.commit();
			System.out.println("Rider Registered Successfully!");
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
	public void updateRider(int riderId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Rider rider = em.find(Rider.class, riderId);
		
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
				rider.setEmail(email);
				try {
					et.begin();
					em.merge(rider);
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
				rider.setPassword(password);
				try {
					et.begin();
					em.merge(rider);
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
				rider.setPhoneNUmber(phoneNum);
				try {
					et.begin();
					em.merge(rider);
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
	public void viewProfile(int riderId) {
		Rider rider = getRiderById(riderId);
		System.out.println("======== RIDER PROFILE ========");
	    System.out.println("ID      : " + rider.getUserId());
	    System.out.println("Name    : " + rider.getName());
	    System.out.println("Email   : " + rider.getEmail());
	    System.out.println("Phone   : " + rider.getPhoneNUmber());
	    System.out.println();
		
	}

	@Override
	public void loginAsRider(String email, String password) {
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
		
		if(list.isEmpty()) {
			System.err.println("Invalid Credentials...!");
		}else {
			System.out.println();
			System.out.println("Login Successfull...!");
			System.out.println();
			Rider rider = list.get(0);
			String greetings = "Hello "+rider.getName()+" 👋";
			RiderServices rs = new RiderServices();
			rs.riderDashBoard(rider.getUserId(),greetings);
		}
		
	}

}
