package com.rbm.service;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.dao.RatingDao;
import com.rbm.dao.RideDao;
import com.rbm.dao.implementation.DriverDaoImple;
import com.rbm.dao.implementation.RatingDaoImple;
import com.rbm.dao.implementation.RideDaoImple;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.enums.DriverAvailablity;
import com.rbm.enums.RideStatus;
import com.rbm.util.AppUtil;
import com.rbm.util.UiUtil;

public class DriverServices {
	Scanner sc = AppUtil.getScanner();
	DriverDao dd = new DriverDaoImple();
	UiUtil ui = new UiUtil();
	RatingDao rad = new RatingDaoImple(); 
	RideDao rd = new RideDaoImple();
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
			System.out.println("|     12. Logout                 |");
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
				int rideId2 = currentRide2.getRideId();
				rd.completeRide(rideId2);
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
				dd.updateDriver(driverId);
				break;
			case 10:
				Double driverRating = rad.getAverageDriverRating(driverId);
				System.out.println("Your current rating is: "+driverRating.toString());
				break;
			case 11:
				dd.getTotalRevenue(driverId);
				break;
			case 12:
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
