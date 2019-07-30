package com.dc.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.dc.sendMessage.ESBListenerMessage;

public class ESBservice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("ª∂”≠π‚¡Ÿ ¿¥µΩ ESB º‡Ã˝------------------------------------------------------");
			ServerSocket serverSocket = new ServerSocket(9999);
			while(true){
				Socket cs = serverSocket.accept();
				new ESBListenerMessage(cs).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
