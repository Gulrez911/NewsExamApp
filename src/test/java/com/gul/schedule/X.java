package com.gul.schedule;

public class X extends Thread {
	int a = 1;

	public void run() {
		System.out.println("Thread X started");
		while (a <= 3) {
			System.out.println("Value: " + a + " in Thread X");
			a++;
		}
		System.out.println("Thread X completed");
	}
}
