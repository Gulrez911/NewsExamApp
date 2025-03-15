package com.gul.schedule;

 
public class ThreadScheduler {

	public static void main(String[] args) {

		System.out.println("Main thread");
		X x = new X();
		Y y = new Y();
	 

		Thread t = Thread.currentThread();
		System.out.println(t.getName());

		t.setPriority(Thread.MAX_PRIORITY);
		y.setPriority(Thread.MIN_PRIORITY);
	 

		x.start();
		y.start();
		 

	}

}
