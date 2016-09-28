package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {

	private static final int PORT = 5001;

	public static void main(String[] args) {
		Socket socket = null;
		ServerSocket serversocket = null;
		try {
			// 1. 서버소켓 생성
			serversocket = new ServerSocket();

			// 2. 바인딩
			InetAddress inetaddress = InetAddress.getLocalHost();
			String ip = inetaddress.getHostAddress();
			InetSocketAddress inetsocketaddress = new InetSocketAddress(ip, PORT);
			serversocket.bind(inetsocketaddress);

			while (true) {
				// 3. accept
				socket = serversocket.accept();

				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}

		} catch (SocketException ex) {
			System.out.println("[server] abnormally closed by client");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serversocket != null && serversocket.isClosed() == false) {
					serversocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
