package com.dc.service;

import com.dc.receiveMessage.BANKListenerMessage;

public class CoreBankServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BANKListenerMessage().start();
	}

}
