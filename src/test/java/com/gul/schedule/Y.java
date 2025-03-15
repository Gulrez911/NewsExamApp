package com.gul.schedule;

public class Y extends Thread {

	int b = 1;

	public void run() {
		System.out.println("Thread Y started");
		while (b <= 3) {
			System.out.println("Value: " + b + " in Thread Y");
			b++;
		}
		System.out.println("Thread Y completed");
	}
}
