package com.rbm.service;

import java.util.List;
import java.util.Scanner;

import com.rbm.dao.AdminDao;
import com.rbm.dao.DriverDao;
import com.rbm.dao.RideDao;
import com.rbm.dao.RiderDao;
import com.rbm.dao.implementation.AdminDaoImple;
import com.rbm.dao.implementation.DriverDaoImple;
import com.rbm.dao.implementation.RideDaoImple;
import com.rbm.dao.implementation.RiderDaoImple;
import com.rbm.entity.Driver;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.util.AppUtil;
import com.rbm.util.UiUtil;

public class AdminServices {
	AdminDao ad = new AdminDaoImple();
	Scanner sc = AppUtil.getScanner();
	DriverDao dd = new DriverDaoImple();
	UiUtil ui = new UiUtil();
	RideDao rd = new RideDaoImple();
	RiderDao rid = new RiderDaoImple();
	public void adminDashboard(int adminId, String greetings) {
		while(true) {
			System.out.println("**********************************");
			System.out.println("**                              **");
			System.out.println("**   Ride Booking Application   **");
			System.out.println("**                              **");
			System.out.println("**********************************");
			System.out.println("==================================");
			System.out.println("==                              ==");
			ui.printCentered("ADMIN DASHBOARD");
			System.out.println("==                              ==");
			ui.printCentered(greetings);
			System.out.println("==                              ==");
			System.out.println("==================================");
			System.out.println("|                                |");
			System.out.println("|    1.  View Total Revenue      |");
			System.out.println("|    2.  View All Payments       |");
			System.out.println("|    3.  View All Riders         |");
			System.out.println("|    4.  View All Drivers        |");
			System.out.println("|    5.  View All Rides          |");
			System.out.println("|    6.  View All Pending Rides  |");
			System.out.println("|    7.  View All Accepted Rides |");
			System.out.println("|    8.  View Completed Rides    |");
			System.out.println("|    9.  View Cancelled Rides    |");
			System.out.println("|    10. Remove Rider            |");
			System.out.println("|    11. Remove Driver           |");
			System.out.println("|    12. View Profile            |");
			System.out.println("|    13. Update Profile          |");
			System.out.println("|    14. Logout                  |");
			System.out.println("|                                |");
			System.out.println("==================================");
			System.out.print("Enter Your Choice(1, 2, 3, ..., 14): ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				List<Rider> riders = ad.getAllRiders();
				for(Rider r:riders) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Rider ID                 : "+r.getUserId());
					System.out.println("Name                     : "+r.getName());
					System.out.println("Email                    : "+r.getEmail());
					System.out.println("Phone Number             : "+r.getPhoneNUmber());
					System.out.println("Accounted Creation Date  : "+r.getCreatedAt());
					System.out.println("Total Rides              : "+rid.getTotalRides(r.getUserId()));
					System.out.println("----------------------------------------------------------------");
				}
				break;
			case 4:
				List<Driver> drivers = ad.getAllDrivers();
				for(Driver d:drivers) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Driver ID                : "+d.getUserId());
					System.out.println("Name                     : "+d.getName());
					System.out.println("Phone Number             : "+d.getPhoneNUmber());
					System.out.println("Rating                   : "+d.getAverageRating());
					System.out.println("Driver Availability      : "+d.getDriverAvailability());
					System.out.println("Vehicle Type             : "+d.getVehicle().getVehicleType());
					System.out.println("Total Earnings           : "+d.getTotalEarnings());
					System.out.println("Total Rides              : "+dd.getTotalRides(d.getUserId()));
					System.out.println("----------------------------------------------------------------");
				}
				break;
			case 5:
				List<Ride> rides = ad.getAllRides();
				for(Ride r:rides) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Ride ID                : "+r.getRideId());
					System.out.println("Rider Name             : "+r.getRider().getName());
					System.out.println("Driver Name            : "+r.getDriver().getName());
					System.out.println("From                   : "+r.getPickUpLoc());
					System.out.println("To                     : "+r.getDestination());
					System.out.println("Fare                   : "+r.getFare());
					System.out.println("Status                 : "+r.getRideStatus());
					System.out.println("----------------------------------------------------------------");
				}
				break;
			case 6:
				List<Ride> pendingRides = ad.getPendingRides();
				for(Ride r:pendingRides) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Ride ID                : "+r.getRideId());
					System.out.println("Rider Name             : "+r.getRider().getName());
					System.out.println("From                   : "+r.getPickUpLoc());
					System.out.println("To                     : "+r.getDestination());
					System.out.println("Fare                   : "+r.getFare());
					System.out.println("----------------------------------------------------------------");
				}
				break;
			case 7:
				List<Ride> acceptedRides = ad.getAcceptedRides();
				for(Ride r:acceptedRides) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Ride ID                : "+r.getRideId());
					System.out.println("Rider Name             : "+r.getRider().getName());
					System.out.println("Driver Name            : "+r.getDriver().getName());
					System.out.println("From                   : "+r.getPickUpLoc());
					System.out.println("To                     : "+r.getDestination());
					System.out.println("Fare                   : "+r.getFare());
					System.out.println("----------------------------------------------------------------");
				}
				break;
			case 8:
				List<Ride> completedRides = ad.getCompletedRides();
				for(Ride r:completedRides) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Ride ID                : "+r.getRideId());
					System.out.println("Rider Name             : "+r.getRider().getName());
					System.out.println("Driver Name            : "+r.getDriver().getName());
					System.out.println("From                   : "+r.getPickUpLoc());
					System.out.println("To                     : "+r.getDestination());
					System.out.println("Fare                   : "+r.getFare());
					System.out.println("----------------------------------------------------------------");
				}
				break;
			case 9:
				List<Ride> cancelledRides = ad.getCancelledRides();
				for(Ride r:cancelledRides) {
					System.out.println("----------------------------------------------------------------");
					System.out.println("Ride ID                : "+r.getRideId());
					System.out.println("Rider Name             : "+r.getRider().getName());
					System.out.println("Driver Name            : "+r.getDriver().getName());
					System.out.println("From                   : "+r.getPickUpLoc());
					System.out.println("To                     : "+r.getDestination());
					System.out.println("Fare                   : "+r.getFare());
					System.out.println("----------------------------------------------------------------");
				}
				break;
			case 10:
				System.out.println();
				System.out.print("Enter Rider ID: ");
				int riderId = sc.nextInt();
				ad.deleteRider(riderId);
				break;
			case 11:
				System.out.println();
				System.out.print("Enter Driver ID: ");
				int driverId = sc.nextInt();
				ad.deleteRider(driverId);
				break;
			case 12:
				System.out.println("--------------------------------------------------------------------");
				ad.viewAdminProfile();
				System.out.println("--------------------------------------------------------------------");
				break;
			case 13:
				System.out.println();
				ad.updateAdmin();
				break;
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
