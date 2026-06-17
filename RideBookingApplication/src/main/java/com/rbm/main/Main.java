package com.rbm.main;

import java.util.Scanner;

import com.rbm.controller.DriverController;
import com.rbm.controller.RiderController;
import com.rbm.util.AppUtil;

public class Main {
	public static void main(String[] args) {
		Scanner sc = AppUtil.getScanner();
		System.out.println("******************************");
		System.out.println("**                          **");
		System.out.println("** Ride Booking Application **");
		System.out.println("**                          **");
		System.out.println("******************************");
		System.out.println("==============================");
		System.out.println("==                          ==");
		System.out.println("==   Welcome To Main Menu   ==");
		System.out.println("==                          ==");
		System.out.println("==============================");
		System.out.println("|                            |");
		System.out.println("|     1. Login As Admin      |");
		System.out.println("|     2. Login As Rider      |");
		System.out.println("|     3. Login As Driver     |");
		System.out.println("|     4. Register Rider      |");
		System.out.println("|     5. Register Driver     |");
		System.out.println("|     6. Exit                |");
		System.out.println("|                            |");
		System.out.println("==============================");
		System.out.print("Enter Your Choice(1, 2, 3, 4, 5, 6): ");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			
			break;
		case 2:
			RiderController rc1 = new RiderController();
			System.out.print("Enter Your Email: ");
			String email2 = sc.next();
			System.out.print("Enter Your Password: ");
			String password2 = sc.next();
			rc1.riderLogin(email2, password2);
			break;
		case 3:
			DriverController dc1 = new DriverController();
			System.out.println();
			System.out.print("Enter Your Email: ");
			String email3 = sc.next();
			System.out.println();
			System.out.print("Enter Your Password: ");
			String password3 = sc.next();
			System.out.println();
			dc1.driverLogin(email3, password3);
			break;
		case 4:
			
			break;
		case 5:
			DriverController dc2 = new DriverController();
			dc2.driverRegistration();
			break;
		case 6:
			System.out.println("Exiting Application...!");
			System.exit(0);
			break;

		default:
			break;
		}
	}
}
