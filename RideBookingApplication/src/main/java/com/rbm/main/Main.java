package com.rbm.main;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("rbm");
		System.out.println("Tables Successfully Created...!");
	}
}
