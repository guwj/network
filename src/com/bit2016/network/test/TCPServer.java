package com.bit2016.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

	private static final int PORT = 5000;

	public static void main(String[] args) {
		// 설명 요망
		ServerSocket serverSocket = null;
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			// 2. binding( 소켓에 소켓주소(IP + port)를 바인딩한다 )
			InetAddress inetaddress = InetAddress.getLocalHost();	//hostname, ip,**--------------InetAddress는 무엇? 정보들 가져오는 util
			String hostAddress = inetaddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));	//---------InetSocketAddress는 무엇? host ip , port
			System.out.println("[server] binding " + hostAddress + ":" + PORT);

			// 3. accept( 클라이언트로부터 연결요청을 기다리는 상태)
			Socket socket = serverSocket.accept(); // block 됨(프로그램이 정지됨)

			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			InetAddress inetRemotHostAddress = inetSocketAddress.getAddress();	//-------------getAddress는 무엇?
			String remoteHostAddress = inetRemotHostAddress.getHostAddress();	//-------------getHostAddress는 무엇?
			int remoteHostPort = inetSocketAddress.getPort();

			System.out.println("[server] connected by client[" + remoteHostAddress + ":" + remoteHostPort + "]");
			try {
				// 4. IOStream 받아오기
				InputStream inputStream = socket.getInputStream();		//------------왜 new InputStream이 아니고?
				OutputStream outputStream = socket.getOutputStream();	//------------

				while (true) {
					// 5. 데이터 읽기

					// 이 과정 대신 inputStreamReader를 사용하면 편리
					byte[] buffer = new byte[256];
					int readByteCount = inputStream.read(buffer); // block
					if (readByteCount == -1) {
						// 정상 종료( remote 소켓이 close()를 불러서 정상적으로 소켓을 닫았다는 의미 )
						System.out.println("[server] closed by client");
						break;
					}
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[server] received : " + data);

					// 6. 쓰기
					outputStream.write(data.getBytes("UTF-8"));
				}
			} catch (SocketException ex) {
				System.out.println("[server] abnormally closed by client");

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					// 5. 자원정리(소켓 닫기)
					socket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) { // 두번
																				// 닫지
																				// 않기
																				// 위해
																				// isClosed()로
																				// 확인
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
