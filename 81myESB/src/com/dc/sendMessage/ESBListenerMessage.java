package com.dc.sendMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.dc.DAO.BackToTeller;
import com.dc.DAO.DoSthESB;

public class ESBListenerMessage extends Thread implements MessageListener{
	private Socket cs = null;
	private String ID = "" + Calendar.getInstance().getTimeInMillis();
	private BufferedReader in = null;
	private PrintWriter out = null;
	private MessageConsumer consumer = null;
	private String IP = "127.0.0.1";
	
	public ESBListenerMessage(Socket cs){
		this.cs = cs;
		try {
			this.in = new BufferedReader (new InputStreamReader(this.cs.getInputStream()));
			this.out = new PrintWriter(this.cs.getOutputStream());
			ConnectionFactory factory = 
				new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
						ActiveMQConnection.DEFAULT_PASSWORD, "tcp://"+ this.IP +":61616");

			Connection connection = factory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("BANK2ESB");
			
			this.consumer = session.createConsumer(destination, "MessageID='"+this.ID +"'");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void readRequest(){
		try {
			if(!(this.cs.isClosed())){
				String request = this.in.readLine();
				System.out.println(request);
				//处理request;
				DoSthESB doESB = new DoSthESB(request);
				//
				String[] toESB = doESB.doIt();
				System.out.println(toESB[0]);
				for(int i = 0; i < toESB.length; i++){
					System.out.println("消息ID是："+this.ID+",ESB发往消息队列ESB2BANK的消息是"+toESB[i]);
					System.out.println("----------------------------------------");
					new SendMessage(toESB[i], "ESB2BANK", this.ID).run();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if(message instanceof TextMessage){
			TextMessage resp = (TextMessage)message;
			try {
				String ID = message.getStringProperty("MessageID");
				String response = (String)resp.getText();
				System.out.print("消息ID是：" + ID);
				System.out.println(",ESBlistenerMessage收到消息队列BANK2ESB的消息是：" + response);
				System.out.println("----------------------------------------");
				//处理response;
				//
				//
				String back = new BackToTeller().toTeller(response);
				this.out.println(back);
				this.out.flush();
				System.out.println("ESBlistenerMessage发到前台的的消息是：" + back);
				System.out.println("----------------------------------------");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public void run() {		
		try {
			this.consumer.setMessageListener(this);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.readRequest();
	}
	
}
