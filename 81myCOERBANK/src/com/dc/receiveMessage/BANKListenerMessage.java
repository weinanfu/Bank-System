package com.dc.receiveMessage;

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

import com.dc.DAO.ClassPath;
import com.dc.DAO.SplitString;
import com.dc.bean.DoSthBank;


public class BANKListenerMessage extends Thread implements MessageListener{
	private MessageConsumer consumer = null;
	private String IP = "127.0.0.1";
	
	public BANKListenerMessage(){
		System.out.println("欢迎光临 来到 COREBANK 监听！！！！！！！！！！！！！！");
		try {
			ConnectionFactory factory = 
				new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
						ActiveMQConnection.DEFAULT_PASSWORD, "tcp://"+ this.IP +":61616");

			Connection connection = factory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("ESB2BANK");
			
			this.consumer = session.createConsumer(destination);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if(message instanceof TextMessage){
			TextMessage objMsg = (TextMessage)message;
			try {
				String ID = message.getStringProperty("MessageID");
				String response = (String)objMsg.getText();
				System.out.println("BANKE端listenerMessage收到的消息是：" + response);
				System.out.println("消息ID是：" + ID);
				System.out.println("----------------------------------------");
				//业务处理！
				//
				String back = null;
				String server_id = new SplitString(response).getMessages()[1][1]; 
				System.out.println(server_id);
				ClassPath classPath = new ClassPath();
				String path = classPath.getClassPath(server_id);
				System.out.println(path);
				DoSthBank doSthBank = 
					(DoSthBank) getClass().getClassLoader().loadClass(path).newInstance();
				back = doSthBank.doIt(response);
				//
				//业务处理！
				new SendMessage(back, "BANK2ESB", ID).run();
				System.out.println("BANKE端处理完发送消息是：" + back);
				System.out.println("消息ID是：" + ID);
				System.out.println("----------------------------------------");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.consumer.setMessageListener(this);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
