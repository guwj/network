package com.bit2016.network.thread;

public class MultiThreadEx03 {
	public static void main(String args[]) {
		Thread thread1 = new DigitThread(); // Thread 상속
		Thread thread2 = new AlphabetThread(); // Thread 상속
		// 일반클래스와 Runnable 상속, Thread 생성자에는 Runnable을 구현한 객체가 들어갈 수 있다
		Thread thread3 = new Thread(new UppercaseAlphabetRunnableimpl());

		thread1.start();
		thread2.start();
		thread3.start();
	}
}
