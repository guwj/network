package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ChatServerThread extends Thread {

	private Socket socket;
	private String name;
	private List<PrintWriter> listPrintWriter;

	public ChatServerThread(Socket socket, List<PrintWriter> listPrintWriter) {
		this.socket = socket;
		this.listPrintWriter = listPrintWriter;
	}

	@Override
	public void run() {
		try {
			// 1. 클라이언트 IP, PORT 따오기
			InetSocketAddress remotesocketaddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			ChatServer.consoleLog("Connected by client[" + remotesocketaddress.getAddress().getHostAddress() + ":"
					+ remotesocketaddress.getPort() + "]");

			// 2. IOStream 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			// 3. 채팅 돌아가는 곳
			while (true) {
				String line = br.readLine();
				if (line == null) { // 클라이언트가 강제종료한 경우		
					doQuit(pw);
					break;
				}
				String[] tokens = line.split(":");
				if ("JOIN".equalsIgnoreCase(tokens[0])) {
					doJoin(tokens[1], pw);
				} else if ("QUIT".equalsIgnoreCase(tokens[1])) {
					doQuit(pw);
					break;
				} else if ("MESSAGE".equalsIgnoreCase(tokens[0])) {
					doMessage(tokens[1]);
				}
			}

		} catch (UnsupportedEncodingException e) {
			ChatServer.consoleLog("오류발생3 : " + e);
		} catch (IOException e) {
			ChatServer.consoleLog("오류발생4 : " + e);
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				ChatServer.consoleLog("오류발생5 : " + e);
			}
		}
	}

	private void doJoin(String name, PrintWriter printWriter) {
		// 1. 닉네임 저장
		this.name = name;

		// 2. broadcasting
		String message = name + "님이 입장하였습니다.";

		broadcastMessage(message);

		// 3. PrintWriter add
		addPrintWriter(printWriter);

		// 4. ack
		printWriter.println("JOIN:OK");
	}

	private void doMessage(String message) {
		broadcastMessage(name + " : " + message);
	}

	private void doQuit(PrintWriter printWriter) {
		deletePrintWriter(printWriter);

		String message = name + "님이 퇴장하셨습니다";
		broadcastMessage(message);
	}

	private void addPrintWriter(PrintWriter printWriter) {
		synchronized (listPrintWriter) {
			listPrintWriter.add(printWriter);
		}
	}

	private void deletePrintWriter(PrintWriter printWriter) {
		synchronized (listPrintWriter) {
			listPrintWriter.remove(printWriter);
		}
	}

	private void broadcastMessage(String message) {
		synchronized (listPrintWriter) {
			for (PrintWriter printWriter : listPrintWriter) {	//---------------이러면 pw 다 읽는거 아닌지??
				printWriter.println(message);
			}
		}
	}
}
