package com.bit2016.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	public static final int PORT = 5004;
	public static final int BUFFER_SIZE = 0;

	public static void main(String args[]) {
		DatagramSocket socket = null;

		try {
			// 1. 데이터그램 소켓 생성
			socket = new DatagramSocket(PORT);
			SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a" );
			
			while (true) {
				// 2. 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket); // blocking
				// 3. 데이터 송신
				
				String data = format.format(new Date());
				byte[] sendData = data.getBytes("UTF-8");
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
						receivePacket.getPort());
				
				socket.send(sendPacket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(socket != null && socket.isClosed() == false){
				socket.close();
			}
		}
	}
}
