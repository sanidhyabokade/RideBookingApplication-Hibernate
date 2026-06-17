package com.rbm.service;

import java.util.Scanner;

import com.rbm.dao.RideDao;
import com.rbm.dao.RiderDao;
import com.rbm.dao.implementation.RideDaoImple;
import com.rbm.dao.implementation.RiderDaoImple;
import com.rbm.entity.Ride;
import com.rbm.util.AppUtil;
import com.rbm.util.UiUtil;

public class RiderServices {
	Scanner sc = AppUtil.getScanner();
	UiUtil ui = new UiUtil();
	RiderDao rid = new RiderDaoImple();
	RideDao rd = new RideDaoImple();
	public void riderDashBoard(int riderId, String greetings) {
		while(true) {
			System.out.println("**********************************");
			System.out.println("**                              **");
			System.out.println("**   Ride Booking Application   **");
			System.out.println("**                              **");
			System.out.println("**********************************");
			System.out.println("==================================");
			System.out.println("==                              ==");
			ui.printCentered("RIDER DASHBOARD");
			System.out.println("==                              ==");
			ui.printCentered(greetings);
			System.out.println("==                              ==");
			System.out.println("==================================");
			System.out.println("|                                |");
			System.out.println("|     1. Book a Ride             |");
			System.out.println("|     2. View Current Ride       |");
			System.out.println("|     3. Cancel Ride             |");
			System.out.println("|     4. View Ride History       |");
			System.out.println("|     5. View Profile            |");
			System.out.println("|     6. Update Profile          |");
			System.out.println("|     7. Logout                  |");
			System.out.println("|                                |");
			System.out.println("==================================");
			System.out.print("Enter Your Choice(1, 2, 3, ..., 8): ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				rd.bookRide(riderId);
				break;
			case 2:
				Ride ride = rd.viewCurrentRide(riderId);
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
			case 3:
				System.out.println();
				System.out.print("Enter your ride ID: ");
				int rideId = sc.nextInt();
				rd.cancelRide(rideId);
				break;
			case 4:
				rid.getRideHistory(riderId);
				break;
			case 5:
				rid.viewProfile(riderId);
				break;
			case 6:
				rid.updateRider(riderId);
				break;
			case 7:
				System.out.println("Exiting...!");
				return;
				
			default:
				System.err.println("Invlid Input...!");
				break;
			}
		}
	}
}
