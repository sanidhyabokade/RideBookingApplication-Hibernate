package com.rbm.util;

import java.util.Scanner;

import com.rbm.enums.VehicleType;

public class AppUtil {
	private static final Scanner sc = new Scanner(System.in);
	
	public static Scanner getScanner() {
		return sc;
	}
	
	public static double calculateFare(
	        double distance,
	        VehicleType type) {

	    switch(type) {

	    case BIKE:
	        return distance * 8;

	    case AUTO:
	        return distance * 12;

	    case MINI:
	        return distance * 15;

	    case SEDAN:
	        return distance * 20;

	    case SUV:
	        return distance * 25;

	    default:
	        return 0;
	    }
	}
}
