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
import com.rbm.entity.Admin;
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
			System.out.println("|    14. View System Analytics   |");
			System.out.println("|    15. Logout                  |");
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
				boolean deleteRider = ad.deleteRider(riderId);
				if(deleteRider) {
					System.out.println("Rider Removed Successfully!");
				}else {
					System.err.println("Removing Rider Failed...!");
				}
				break;
			case 11:
				System.out.println();
				System.out.print("Enter Driver ID: ");
				int driverId = sc.nextInt();
				boolean deleteDriver = ad.deleteDriver(driverId);
				if(deleteDriver) {
					System.out.println("Driver Removed Successfully...!");
				}else {
					System.err.println("Removing Driver Failed!");
				}
				break;
			case 12:
				System.out.println("--------------------------------------------------------------------");
				Admin adminProfile = ad.viewAdminProfile();
				System.out.println("======== ADMIN PROFILE ========");
				System.out.println("ID      : " + adminProfile.getUserId());
				System.out.println("Name    : " + adminProfile.getName());
				System.out.println("Email   : " + adminProfile.getEmail());
				System.out.println("Phone   : " + adminProfile.getPhoneNUmber());
				System.out.println("--------------------------------------------------------------------");
				break;
			case 13:
				System.out.println();
				Admin admin = ad.getAdminById(adminId);
				while(true) {
					System.out.println("Press 1 to Update Email");
					System.out.println("Press 2 to Update Password");
					System.out.println("Press 3 to Update Contact Number");
					System.out.println("Press 4 to go back to previous menu");
					int choice1 = sc.nextInt();
					switch (choice1) {
					case 1:
						System.out.print("Enter New Email: ");
						String email = sc.next();
						admin.setEmail(email);
						boolean updateAdmin1 = ad.updateAdmin(admin);
						if(updateAdmin1) {
							System.out.println("Admin Updated Successfully!");
						}else {
							System.err.println("Updating Admin failed...!");
						}
						break;
					case 2:
						System.out.print("Enter New Password: ");
						String password = sc.next();
						admin.setPassword(password);
						boolean updateAdmin2 = ad.updateAdmin(admin);
						if(updateAdmin2) {
							System.out.println("Admin Updated Successfully!");
						}else {
							System.err.println("Updating Admin failed...!");
						}
						break;
					case 3:
						System.out.print("Enter New Contact Number: ");
						long phoneNum = sc.nextLong();
						admin.setPhoneNUmber(phoneNum);
						boolean updateAdmin3 = ad.updateAdmin(admin);
						if(updateAdmin3) {
							System.out.println("Admin Updated Successfully!");
						}else {
							System.err.println("Updating Admin failed...!");
						}
						break;
					case 4:
						return;

					default:
						System.err.println("Invaid Choice...!");
						break;
					}
				}
			case 14:
				System.out.println("========== SYSTEM ANALYTICS ==========");

				System.out.println("Total Drivers       : " + ad.getTotalDrivers());

				System.out.println("Total Riders        : " + ad.getTotalRiders());

				System.out.println("Completed Rides     : " + ad.getCompletedRideCount());

				System.out.println("Cancelled Rides     : " + ad.getCancelledRideCount());

				System.out.println("Total Revenue       : ₹" + ad.getRevenue());


				Driver driver = ad.getTopRatedDriver();

				if (driver != null) {
				    System.out.println("Top Rated Driver    : "
				            + driver.getName()
				            + " (" + driver.getRatingReceived() + ")");
				} else {
				    System.out.println("Top Rated Driver    : N/A");
				}
			case 15:
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
