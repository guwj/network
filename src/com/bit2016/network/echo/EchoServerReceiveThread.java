package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;

	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
			System.out.println("[server#" + getId() + "] connected by client[" + isa.getAddress().getHostAddress() + ":"
					+ isa.getPort() + "]");

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
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
