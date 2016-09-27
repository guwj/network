package com.bit2016.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetaddress = InetAddress.getLocalHost();
			String hostName = inetaddress.getHostName();
			String hostAddress = inetaddress.getHostAddress();
			
			System.out.println(hostName + ": " + hostAddress);
			
			byte[] addresses = inetaddress.getAddress();		//	192 | 168 | 1 | 2
			for(byte address : addresses){
				System.out.print(address & 0x00000ff);
				System.out.print(".");
			}
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
