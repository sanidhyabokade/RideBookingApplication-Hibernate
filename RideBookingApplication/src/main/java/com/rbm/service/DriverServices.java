package com.rbm.service;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.DriverDao;
import com.rbm.dao.implementation.DriverDaoImple;
import com.rbm.entity.Ride;
import com.rbm.util.AppUtil;

public class DriverServices {
	Scanner sc = AppUtil.getScanner();
	DriverDao dd = new DriverDaoImple();
	public void driverDashBoard(int driverId) {
		while(true) {
			System.out.println("******************************");
			System.out.println("**                          **");
			System.out.println("** Ride Booking Application **");
			System.out.println("**                          **");
			System.out.println("******************************");
			System.out.println("==============================");
			System.out.println("==                          ==");
			System.out.println("==     DRIVER DASHBOARD     ==");
			System.out.println("==                          ==");
			System.out.println("==============================");
			System.out.println("|                            |");
			System.out.println("|   1. View Available Ride   |");
			System.out.println("|   2. Accept Ride           |");
			System.out.println("|   3. View Current Ride     |");
			System.out.println("|   4. Start Ride            |");
			System.out.println("|   5. Complete Ride         |");
			System.out.println("|   6. View Ride History     |");
			System.out.println("|   7. Change Availability   |");
			System.out.println("|   8. View Profile          |");
			System.out.println("|   9. Update Profile        |");
			System.out.println("|   10. View Ratings         |");
			System.out.println("|   11. View Total Earnings  |");
			System.out.println("|   12. Logout               |");
			System.out.println("|                            |");
			System.out.println("==============================");
			System.out.print("Enter Your Choice(1, 2, 3, ..., 12): ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				List<Ride> rides = dd.viewAvailableRides();
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
				dd.acceptRide(driverId, rideId);
				break;
			case 3:
				Ride ride = dd.viewCurrentRide(driverId);
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
				
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 7:
				
				break;
			case 8:
				
				break;
			case 9:
				
				break;
			case 10:
				
				break;
			case 11:
				
				break;
			case 12:
				System.out.println("Exiting...!");
				return;

			default:
				System.err.println("Invlid Input...!");
				break;
			}
		}
	}
}
