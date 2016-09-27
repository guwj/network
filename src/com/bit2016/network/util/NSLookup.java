package com.bit2016.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {

	public static void main(String[] args) {
		
		
		try {
			String host = "www.naver.com";
			InetAddress[] inetaddresses = InetAddress.getAllByName(host);
			
			for(InetAddress inetaddress : inetaddresses){
				System.out.println(inetaddress.getHostAddress());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
