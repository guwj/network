package com.bit2016.network.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private static final int PORT = 5004;
	public static void main(String[] args) {
		List<PrintWriter> listPrintWriter = new ArrayList<PrintWriter>();
		ServerSocket serversocket = null;
		
		try {
			// 1. 서버소켓 생성
			serversocket = new ServerSocket();
			
			// 1-1. set option SO_REUSEADDR ( 종료 후 빨리 binding 하기 위함, 필수 )
			serversocket.setReuseAddress(true);
			
			// 2. binding
			String localhost = InetAddress.getLocalHost().getHostAddress();
			serversocket.bind(new InetSocketAddress(localhost, PORT));		// backlog : 스레드를 생성해서 소켓을 태워 보내는 순간에 커넥트리퀘스트가 들어오면 그걸 받아놓는 것을 말한다
			consoleLog("binding " + localhost +":" + PORT);
			
			while(true){
			// 3. Connect Request 대기
				Socket socket = serversocket.accept();
				
				Thread thread = new ChatServerThread(socket, listPrintWriter);
				thread.start();
			}
		} catch (IOException e) {
			consoleLog( "오류발생1 : " + e);
		}finally{
			try{
				if(serversocket != null && serversocket.isClosed() == false){
					serversocket.close();
				}
			}catch(IOException e){
				consoleLog("오류발생2 : " + e);
			}
		}
	}

	public static void consoleLog(String message){
		System.out.println("[chat server] " + message);
	}
}
