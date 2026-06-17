package com.rbm.util;

public class UiUtil {
	public void printCentered(String text) {
		int width = 30;
		int leftPadding = (width - text.length()) / 2;
		int rightPadding = width - text.length() - leftPadding;
		
		System.out.println("=="+" ".repeat(Math.max(0, leftPadding))+text+" ".repeat(Math.max(0, rightPadding))+"==");
	}
}
