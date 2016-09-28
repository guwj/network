package com.bit2016.network.thread;

public class MultiThreadEx01 {

	public static void main(String[] args) {

		Thread digitThread = new DigitThread();
		digitThread.start();
		
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);		// 1초 쉬는 것
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
