package com.bit2016.network.thread;

public class UppercaseAlphabetRunnableimpl extends UppercaseAlphabet implements Runnable{	//Runnable을 이용하여 일반 클래스를 메소드에 태우기 위함

	@Override
	public void run() {
		print();
	}
	
}
