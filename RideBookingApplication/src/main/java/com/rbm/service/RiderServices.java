package com.rbm.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.rbm.dao.PaymentDao;
import com.rbm.dao.RatingDao;
import com.rbm.dao.RideDao;
import com.rbm.dao.RiderDao;
import com.rbm.dao.implementation.PaymentDaoImple;
import com.rbm.dao.implementation.RatingDaoImple;
import com.rbm.dao.implementation.RideDaoImple;
import com.rbm.dao.implementation.RiderDaoImple;
import com.rbm.entity.Driver;
import com.rbm.entity.Payment;
import com.rbm.entity.Rating;
import com.rbm.entity.Ride;
import com.rbm.entity.Rider;
import com.rbm.enums.PaymentMethod;
import com.rbm.enums.PaymentStatus;
import com.rbm.enums.RideStatus;
import com.rbm.enums.VehicleType;
import com.rbm.util.AppUtil;
import com.rbm.util.UiUtil;



public class RiderServices {
	Scanner sc = AppUtil.getScanner();
	UiUtil ui = new UiUtil();
	RiderDao rid = new RiderDaoImple();
	RideDao rd = new RideDaoImple();
	RatingDao rad = new RatingDaoImple();
	PaymentDao pd = new PaymentDaoImple();
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
			System.out.println("|     3. Complete Ride           |");
			System.out.println("|     4. Cancel Ride             |");
			System.out.println("|     5. View Ride History       |");
			System.out.println("|     6. View Profile            |");
			System.out.println("|     7. Update Profile          |");
			System.out.println("|     8. Logout                  |");
			System.out.println("|                                |");
			System.out.println("==================================");
			System.out.print("Enter Your Choice(1, 2, 3, ..., 8): ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				
					Rider rider = rid.getRiderById(riderId);
					
					if(rider == null) {

						System.err.println("Rider Not Found!");
						return;
					}
					
					PaymentStatus paymentStatus = rider.getRides().getLast().getPayment().getPaymentStatus();
					
					while(paymentStatus.equals(PaymentStatus.PENDING)) {
						
						Ride last = rider.getRides().getLast();

						Payment payment = last.getPayment();
						
						payment.setRide(last);
						payment.setAmount(last.getFare());

						PaymentMethod method = null;
						
						System.out.println();
						System.out.println("Your Fare is: "+last.getFare());
						System.out.println();
						System.out.println("*CHOOSE PAYMENT MODE*");
						System.out.println("1.UPI");
						System.out.println("2.CARD");
						System.out.println("3.CASH");

						int choice1 = sc.nextInt();

						switch(choice1) {

						case 1:
							method = PaymentMethod.UPI;
							payment.setPaymentMethod(method);
							payment.setPaymentStatus(PaymentStatus.SUCCESS);
							payment.setPaymentTime(LocalDateTime.now());
							break;

						case 2:
							method = PaymentMethod.CARD;
							payment.setPaymentMethod(method);
							payment.setPaymentStatus(PaymentStatus.SUCCESS);
							payment.setPaymentTime(LocalDateTime.now());
							break;

						case 3:
							method = PaymentMethod.CASH;
							payment.setPaymentMethod(method);
							payment.setPaymentStatus(PaymentStatus.SUCCESS);
							payment.setPaymentTime(LocalDateTime.now());
							break;

						default:
							System.out.println("Invalid Choice!");
							return;
						}
						
						payment.setRide(last);
						last.setPayment(payment);
						
						boolean payment2 = pd.makePayment(payment);
						if(payment2) {
							System.out.println("Payment Successful!");
						}else {
							System.err.println("Payment Failed!");
						}
						
					}
					
					System.out.println("===============================");
					System.out.println("==                           ==");
					System.out.println("==        BOOK A RIDE        ==");
					System.out.println("==                           ==");
					System.out.println("===============================");
					System.out.println();

					System.out.print("Enter Pickup Location: ");
					String pickUpLoc = sc.next();

					System.out.print("Enter Destination: ");
					String destination = sc.next();

					System.out.print("Enter Distance(km): ");
					double distance = sc.nextDouble();

					VehicleType vehicleType = chooseVehicleType();

					double fare = AppUtil.calculateFare(distance, vehicleType);

					Ride ride = new Ride();
					Payment payment = new Payment();
					ride.setPickUpLoc(pickUpLoc);
					ride.setDestination(destination);
					ride.setDistance(distance);
					ride.setFare(fare);
					ride.setBookingTime(LocalDateTime.now());
					ride.setRideStatus(RideStatus.REQUESTED);
					ride.setRider(rider);
					ride.setVehicleType(vehicleType);
					ride.setPayment(payment);
					rd.bookRide(ride);
					System.out.println("Estimated Fare : ₹"+ fare);
				
				break;
			case 2:
				Ride ride1 = rd.viewCurrentRide(riderId);
				if(ride1 == null) {
			        System.out.println("No Current Ride Found!");
			    } else {
					System.out.println("------------ Current Ride  -------------");
			    	System.out.println("Ride ID: "+ride1.getRideId());
					System.out.println("Pickup Location: "+ride1.getPickUpLoc());
					System.out.println("Drop Location: "+ride1.getDestination());
					System.out.println("Fare: "+ride1.getFare());
					System.out.println();
					System.out.println("----------------------------------------");
			    }
				break;
			case 3:
				Rider rider2 = rid.getRiderById(riderId);
				
				Ride last = rider2.getRides().getLast();
				
				int rideId2 = last.getRideId();
				
				Ride ride2 = rd.getRideById(rideId2);

				if(ride2 == null) {
					System.err.println("Ride Not Found!");
					return;
				}

				if(ride2.getRideStatus() != RideStatus.STARTED) {
					System.err.println("Ride must be IN_PROGRESS!");
					return;
				}

				Driver driver = ride2.getDriver();

				LocalDateTime endTime = LocalDateTime.now();

				ride2.setEndTime(endTime);

				int duration = (int) Duration.between(ride2.getStartTime(), endTime).toMinutes();

				ride2.setDuration(duration);

				Payment payment3 = new Payment();

				payment3.setRide(ride2);
				payment3.setAmount(ride2.getFare());

				PaymentMethod method = null;
				System.out.println("*CHOOSE PAYMENT MODE*");


				System.out.println("1.UPI");
				System.out.println("2.CARD");
				System.out.println("3.CASH");

				int choice1 = sc.nextInt();

				switch(choice1) {

				case 1:
					method = PaymentMethod.UPI;
					payment3.setPaymentMethod(method);
					payment3.setPaymentStatus(PaymentStatus.SUCCESS);
					payment3.setPaymentTime(LocalDateTime.now());
					break;

				case 2:
					method = PaymentMethod.CARD;
					payment3.setPaymentMethod(method);
					payment3.setPaymentStatus(PaymentStatus.SUCCESS);
					payment3.setPaymentTime(LocalDateTime.now());
					break;

				case 3:
					method = PaymentMethod.CASH;
					payment3.setPaymentMethod(method);
					payment3.setPaymentStatus(PaymentStatus.SUCCESS);
					payment3.setPaymentTime(LocalDateTime.now());
					break;

				default:
					System.out.println("Invalid Choice!");
					return;
				}
				
				payment3.setRide(ride2);
				ride2.setPayment(payment3);
				
				if(payment3.getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
					driver.setTotalEarnings(
							driver.getTotalEarnings()
							+ ride2.getFare());

					driver.setTotalRidesCompleted(
							driver.getTotalRidesCompleted()
							+ 1);
				}else {
					System.out.println("Payment Failed...!");
				}
				
				rd.completeRide(ride2, driver);
				
				System.out.println("Ride Completed Successfully!");
				System.out.println("Ride Duration : "
						+ duration + " Minutes");

				System.out.println();
				System.out.println("Would you like to rate your driver?");
				System.out.println("1. Yes");
				System.out.println("2. Skip");
				int choice2 = sc.nextInt();
				switch (choice2) {
				case 1:
					Rating rating = new Rating();

					System.out.print("Enter Rating (1-5): ");
					int stars = sc.nextInt();

					sc.nextLine();

					System.out.print("Enter Review : ");
					String review = sc.nextLine();

					rating.setStars(stars);
					rating.setReview(review);

					rating.setRide(ride2);
					rating.setDriver(ride2.getDriver());
					rating.setRider(ride2.getRider());

					Driver driver1 = ride2.getDriver();
					Rider rider1 = ride2.getRider();

					driver1.setTotalRatings(driver1.getTotalRatings() + 1);
					driver1.getRatingReceived().add(rating);
					rider1.getRatingsGiven().add(rating);
					ride2.setRating(rating);

					double newAverage = (driver1.getAverageRating() * (driver1.getTotalRatings() - 1) + stars) / driver1.getTotalRatings();

					driver1.setAverageRating(newAverage);
					
					boolean giveRating = rad.giveRating(rating, ride2, driver1);
					if(giveRating) {
						System.out.println("Rating Added Successfully!");
					}else {
						System.err.println("Rating failed...!");
					}

					break;
				case 2:

					return;

				default:
					break;
				}
				break;
			case 4:
				System.out.println();
				System.out.print("Enter your ride ID: ");
				int rideId = sc.nextInt();
				rd.cancelRide(rideId);
				break;
			case 5:
				List<Ride> rideHistory = rid.getRideHistory(riderId);
				for(Ride r:rideHistory) {
					if(r == null) {
				        System.out.println("No Current Ride Found!");
				    } else {
						System.out.println("------------ Ride History -------------");
				    	System.out.println("Ride ID: "+r.getRideId());
						System.out.println("Pickup Location: "+r.getPickUpLoc());
						System.out.println("Drop Location: "+r.getDestination());
						System.out.println("Fare: "+r.getFare());
						System.out.println();
						System.out.print("Do you want to give rating to this driver(y/n)?: ");
						String options = sc.next();
						System.out.println("----------------------------------------");
						if(options.equalsIgnoreCase("y")) {
							System.out.println("------------------- Give Rating ---------------");
							Rating rating = new Rating();

							System.out.print("Enter Rating (1-5): ");
							int stars = sc.nextInt();

							sc.nextLine();

							System.out.print("Enter Review : ");
							String review = sc.nextLine();

							rating.setStars(stars);
							rating.setReview(review);

							rating.setRide(r);
							rating.setDriver(r.getDriver());
							rating.setRider(r.getRider());

							Driver driver2 = r.getDriver();
							Rider rider1 = r.getRider();

							driver2.setTotalRatings(driver2.getTotalRatings() + 1);
							driver2.getRatingReceived().add(rating);
							rider1.getRatingsGiven().add(rating);
							r.setRating(rating);

							double newAverage = (driver2.getAverageRating() * (driver2.getTotalRatings() - 1) + stars) / driver2.getTotalRatings();

							driver2.setAverageRating(newAverage);
							
							boolean giveRating = rad.giveRating(rating, r, driver2);
							if(giveRating) {
								System.out.println("Rating Added Successfully!");
							}else {
								System.err.println("Rating failed...!");
							}
							System.out.println("-----------------------------------------------");
						}else {
							break;
						}
				    }
				}
				break;
			case 6:
				rid.viewProfile(riderId);
				break;
			case 7:
				Rider rider1 = rid.getRiderById(riderId);
				
				while(true) {
					System.out.println("Press 1 to Update Email");
					System.out.println("Press 2 to Update Password");
					System.out.println("Press 3 to Update Contact Number");
					System.out.println("Press 4 to go back to previous menu");
					int choice3 = sc.nextInt();
					switch (choice3) {
					case 1:
						System.out.print("Enter New Email: ");
						String email = sc.next();
						rider1.setEmail(email);
						rid.updateRider(rider1);
						break;
					case 2:
						System.out.print("Enter New Password: ");
						String password = sc.next();
						rider1.setPassword(password);
						rid.updateRider(rider1);
						break;
					case 3:
						System.out.print("Enter New Contact Number: ");
						long phoneNum = sc.nextLong();
						rider1.setPhoneNUmber(phoneNum);
						rid.updateRider(rider1);
						break;
					case 4:
						return;

					default:
						System.err.println("Invaid Choice...!");
						break;
					}
				}
			case 8:
				System.out.println("Exiting...!");
				return;
				
			default:
				System.err.println("Invlid Input...!");
				break;
			}
		}
	}
		private VehicleType chooseVehicleType() {

			while(true) {

				System.out.println("1.BIKE");
				System.out.println("2.AUTO");
				System.out.println("3.MINI");
				System.out.println("4.SEDAN");
				System.out.println("5.SUV");

				int choice = sc.nextInt();

				switch(choice) {

				case 1:
					return VehicleType.BIKE;

				case 2:
					return VehicleType.AUTO;

				case 3:
					return VehicleType.MINI;

				case 4:
					return VehicleType.SEDAN;

				case 5:
					return VehicleType.SUV;

				default:
					System.out.println("Invalid Choice!");
				}
			}
		}
}
