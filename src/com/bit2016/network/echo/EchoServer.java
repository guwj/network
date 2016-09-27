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

			// 3. accept
			socket = serversocket.accept();
			try {
				// 4. IOStream 받기
				InputStream inputstream = socket.getInputStream();
				OutputStream outputstream = socket.getOutputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputstream));

				while (true) {
					// 5. 데이터 읽기
					String data = br.readLine();
					if (data == null) {
						System.out.println("[server] closed by client");
						break;
					}
					System.out.println("[server] received " + data);

					// 6. 쓰기
					pw.println(data);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// 7. 자원정리
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
