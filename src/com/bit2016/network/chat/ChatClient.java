package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private static final int SERVER_PORT = 5004;
	private static final String SERVER_IP = "192.168.1.4";

	public static void main(String agrs[]) {
		Socket socket = null;
		
		try {
			Scanner scanner = new Scanner(System.in);
			
			// 1. 소켓 생성
			socket = new Socket();

			// 2. 접속 시도
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			
			// 3. IOStream 받기
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			System.out.print("닉네임을 입력하세요 :");
			String str = scanner.nextLine();
			pw.println("join:" + str);
			
			Thread thread = new ListenThread(socket);
			thread.start();
			while(true){
				
				// 4. 입력
				String line = scanner.nextLine();
				
				// 5. 송신
				pw.println("message:"+ line);
				
				System.out.println(line);
				if("quit".equalsIgnoreCase(line)){
					break;
				}
			}
			
		} catch (IOException e) {
			ChatServer.consoleLog("오류발생7 : " + e);
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				ChatServer.consoleLog("오류발생8 : " + e);
			}
		}

	}
}
