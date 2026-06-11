package com.rbm.dao.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.entity.Vehicle;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.RideStatus;
import com.rbm.enums.VehicleType;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;


public class DriverDaoImple implements DriverDao{

	Scanner sc = AppUtil.getScanner();

	@Override
	public void registerDriver() {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction et = em.getTransaction();

		System.out.println("===============================");
		System.out.println("==                           ==");
		System.out.println("==    DRIVER REGISTRATION    ==");
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

		System.out.print("Enter Your License Number: ");
		String licenseNum = sc.next();

		System.out.println();
		System.out.println("-------- VEHICLE DETAILS --------");
		System.out.println();

		System.out.print("Enter Your Vehicle Number(MH12PQ5869): ");
		String vehicleNum = sc.next();

		System.out.print("Enter Vehicle Model: ");
		String model = sc.next();

		System.out.print("Enter Your Vehicle Color: ");
		String color = sc.next();

		VehicleType type = null;

		while(type == null) {

			System.out.print("Enter Your Vehicle Type(BIKE, AUTO, MINI, SEDAN, SUV): ");
			String choice = sc.next();

			if(choice.equalsIgnoreCase("BIKE")) {
				type = VehicleType.BIKE;
			}else if(choice.equalsIgnoreCase("AUTO")) {
				type = VehicleType.AUTO;
			}else if(choice.equalsIgnoreCase("MINI")) {
				type = VehicleType.MINI;
			}else if(choice.equalsIgnoreCase("SEDAN")) {
				type = VehicleType.SEDAN;
			}else if(choice.equalsIgnoreCase("SUV")) {
				type = VehicleType.SUV;
			}else {
				System.err.println("Enter A Valid Type...!");
			}
		}

		System.out.print("Enter Seating Capacity Of Your Vehicle: ");
		int capacity = sc.nextInt();

		Vehicle v = new Vehicle();
		v.setVehicleNumber(vehicleNum);
		v.setModel(model);
		v.setColor(color);
		v.setSeatingCapacity(capacity);
		v.setVehicleType(type);

		Driver d = new Driver();
		d.setName(name);
		d.setEmail(email);
		d.setPassword(password);
		d.setPhoneNUmber(contactNumber);
		d.setLicenseNumber(licenseNum);
		d.setDriverAvailability(DriverAvailablity.ONLINE);
		d.setCreatedAt(LocalDateTime.now());
		d.setVehicle(v);

		try {
			et.begin();
			em.persist(d);
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
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		em.close();
		return driver;
	}

	@Override
	public List<Ride> getDriverRideHistory(int driverId) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		Driver driver = em.find(Driver.class, driverId);
		List<Ride> rides = driver.getRides();
		em.close();
		return rides;
	}

	@Override
	public void updateDriver(int driverId) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
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
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
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
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
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

}
