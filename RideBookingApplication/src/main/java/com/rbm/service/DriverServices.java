package com.rbm.service;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.dao.RatingDao;
import com.rbm.dao.RideDao;
import com.rbm.dao.VehicleDao;
import com.rbm.dao.implementation.DriverDaoImple;
import com.rbm.dao.implementation.RatingDaoImple;
import com.rbm.dao.implementation.RideDaoImple;
import com.rbm.dao.implementation.VehicleDaoImple;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.PaymentStatus;
import com.rbm.enums.RideStatus;
import com.rbm.util.AppUtil;
import com.rbm.util.JpaUtil;
import com.rbm.util.UiUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DriverServices {
	Scanner sc = AppUtil.getScanner();
	EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	DriverDao dd = new DriverDaoImple();
	UiUtil ui = new UiUtil();
	RatingDao rad = new RatingDaoImple(); 
	RideDao rd = new RideDaoImple();
	VehicleDao vd = new VehicleDaoImple();
	public void driverDashBoard(int driverId, String greetings) {
		while(true) {
			System.out.println("**********************************");
			System.out.println("**                              **");
			System.out.println("**   Ride Booking Application   **");
			System.out.println("**                              **");
			System.out.println("**********************************");
			System.out.println("==================================");
			System.out.println("==                              ==");
			ui.printCentered("DRIVER DASHBOARD");
			System.out.println("==                              ==");
			ui.printCentered(greetings);
			System.out.println("==                              ==");
			System.out.println("==================================");
			System.out.println("|                                |");
			System.out.println("|     1. View Available Ride     |");
			System.out.println("|     2. Accept Ride             |");
			System.out.println("|     3. View Current Ride       |");
			System.out.println("|     4. Start Ride              |");
			System.out.println("|     5. Complete Ride           |");
			System.out.println("|     6. View Ride History       |");
			System.out.println("|     7. Change Availability     |");
			System.out.println("|     8. View Profile            |");
			System.out.println("|     9. Update Profile          |");
			System.out.println("|     10. View Ratings           |");
			System.out.println("|     11. View Total Earnings    |");
			System.out.println("|     12. View Vehicle           |");
			System.out.println("|     13. Update Vehicle         |");
			System.out.println("|     14. Logout                 |");
			System.out.println("|                                |");
			System.out.println("==================================");
			System.out.print("Enter Your Choice(1, 2, 3, ..., 12): ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				List<Ride> rides = rd.viewAvailableRides();
				System.out.println("------------ Available Rides  -------------");
				for(Ride r: rides) {
					System.out.println("Ride ID: "+r.getRideId());
					System.out.println("Pickup Location: "+r.getPickUpLoc());
					System.out.println("Drop Location: "+r.getDestination());
					System.out.println("Fare: "+r.getFare());
					System.out.println();
					System.out.println("----------------------------------------");
				}
				break;
			case 2:
				System.out.print("ENTER RIDE ID OF THE RIDE THAT YOU WANT TO ACCEPT: ");
				int rideId = sc.nextInt();
				rd.acceptRide(driverId, rideId);
				break;
			case 3:
				Ride ride = rd.viewCurrentRide(driverId);
				if(ride == null) {
			        System.out.println("No Current Ride Found!");
			    } else {
					System.out.println("------------ Current Ride  -------------");
			    	System.out.println("Ride ID: "+ride.getRideId());
					System.out.println("Pickup Location: "+ride.getPickUpLoc());
					System.out.println("Drop Location: "+ride.getDestination());
					System.out.println("Fare: "+ride.getFare());
					System.out.println();
					System.out.println("----------------------------------------");
			    }
				break;
			case 4:
				Ride currentRide1 = rd.viewCurrentRide(driverId);
				int rideId1 = currentRide1.getRideId();
				rd.startRide(rideId1);
				break;
			case 5:
				Ride currentRide2 = rd.viewCurrentRide(driverId);
				if(currentRide2.getPayment().getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
					currentRide2.setRideStatus(RideStatus.COMPLETED);
				}else {
					currentRide2.getPayment().setPaymentStatus(PaymentStatus.PENDING);
				}
				Driver driverById = dd.getDriverById(driverId);
				if(currentRide2.getRideStatus().equals(RideStatus.COMPLETED)) {
					driverById.setDriverAvailability(DriverAvailablity.ONLINE);
				}
				rd.completeRide(currentRide2, driverById);
				break;
			case 6:
				List<Ride> completedRides = rd.getCompletedRides();
				System.out.println("---------------- Rides Completed -------------------");
				for(Ride r:completedRides) {
					System.out.println("Ride ID: "+r.getRideId());
					System.out.println("Pickup Location: "+r.getPickUpLoc());
					System.out.println("Drop Location: "+r.getDestination());
					System.out.println("Fare: "+r.getFare());
					System.out.println();
					System.out.println("-------------------------------------------------");
				}
				break;
			case 7:
				System.out.println("==================================");
				System.out.println("|                                |");
				System.out.println("|          1. Online             |");
				System.out.println("|          2. Offline            |");
				System.out.println("|                                |");
				System.out.println("==================================");
				int choice1 = sc.nextInt();

				DriverAvailablity availability = null;

				switch(choice1) {
				    case 1:
				        availability = DriverAvailablity.ONLINE;
				        break;

				    case 2:
				        availability = DriverAvailablity.OFFLINE;
				        break;
				        
				    default:
				        System.err.println("Invalid Choice!");
				        return;
				}

				dd.changeAvailability(driverId, availability);
				break;
			case 8:
				Driver driver = dd.getDriverById(driverId);
				System.out.println("----------------------------------------------------------------");
				System.out.println("Driver ID                : "+driver.getUserId());
				System.out.println("Name                     : "+driver.getName());
				System.out.println("Phone Number             : "+driver.getPhoneNUmber());
				System.out.println("Rating                   : "+driver.getAverageRating());
				System.out.println("Driver Availability      : "+driver.getDriverAvailability());
				System.out.println("Vehicle Type             : "+driver.getVehicle().getVehicleType());
				System.out.println("Total Earnings           : "+driver.getTotalEarnings());
				System.out.println("Total Rides              : "+dd.getTotalRides(driver.getUserId()));
				System.out.println("----------------------------------------------------------------");
				break;
			case 9:
				EntityManager em = emf.createEntityManager();
				Driver driver1 = em.find(Driver.class, driverId);
				
				while(true) {
					System.out.println("Press 1 to Update Email");
					System.out.println("Press 2 to Update Password");
					System.out.println("Press 3 to Update Contact Number");
					System.out.println("Press 4 to go back to previous menu");
					int choice2 = sc.nextInt();
					switch (choice2) {
					case 1:
						System.out.print("Enter New Email: ");
						String email = sc.next();
						driver1.setEmail(email);
						dd.updateDriver(driver1);
						em.close();
						break;
					case 2:
						System.out.print("Enter New Password: ");
						String password = sc.next();
						driver1.setPassword(password);
						em.close();
						break;
					case 3:
						System.out.print("Enter New Contact Number: ");
						long phoneNum = sc.nextLong();
						driver1.setPhoneNUmber(phoneNum);
						em.close();
						break;
					case 4:
						return;

					default:
						System.err.println("Invaid Choice...!");
						break;
					}
				}
			case 10:
				Double driverRating = rad.getAverageDriverRating(driverId);
				System.out.println("Your current rating is: "+driverRating.toString());
				break;
			case 11:
				double totalRevenue = dd.getTotalRevenue(driverId);
				System.out.println("Your Total Earnings are: "+totalRevenue);
				break;
			case 12:
				Driver driver2 = dd.getDriverById(driverId);
				int vehicleId = driver2.getVehicle().getVehicleId();
				vd.getVehicleById(vehicleId);
				return;
			case 13:
				vd.updateVehicle(dd.getDriverById(driverId).getVehicle().getVehicleId());
				return;
			case 14:
				System.out.println();
				System.out.println("Exiting...!");
				return;

			default:
				System.err.println("Invlid Input...!");
				break;
			}
		}
	}
}
