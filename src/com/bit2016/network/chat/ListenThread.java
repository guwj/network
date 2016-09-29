package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

public class ListenThread extends Thread {

	private Socket socket;

	public ListenThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			while (true) {
				String data = br.readLine();
				if (data == null) {
					break;
				}
				System.out.println(data);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(SocketException e){
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
