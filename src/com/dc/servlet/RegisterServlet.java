package com.dc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dc.DAO.ChangeFlow;
import com.dc.DAO.GetWorkday;
import com.dc.DAO.TellerWriteFlow;
import com.dc.bean.Reference;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String IP = Reference.IP;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("GBK");
		resp.setContentType("text/html;charset=GBK");
		String server_id = req.getParameter("server_id");
		
		System.out.println(server_id);
		
		
		Socket socket = new Socket(IP, 9999);
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		if(server_id.equals("06")){
			
			String flow_no = req.getParameter("flowNo");
			String guest_id = req.getParameter("guest_id");
			String guest_name = req.getParameter("guest_name");
			String guest_sex = req.getParameter("guest_sex");
			String guest_home = req.getParameter("guest_home");
			String guest_tel = req.getParameter("guest_tel");
			String guest_age = req.getParameter("guest_age");
			String guest_demo = req.getParameter("guest_demo");
			String guest_birthday = req.getParameter("guest_birthday");
			String registerMessage ="server_id="+server_id+";flow_no="+ flow_no+";guest_id="+guest_id+";guest_name="+guest_name+";guest_sex="+guest_sex
			+";guest_home="+guest_home+";guest_tel="+guest_tel+";guest_age="+guest_age+";guest_demo="+guest_demo
			+";guest_birthday="+guest_birthday;
			
			
			GetWorkday workday = new GetWorkday();
			String day = workday.get();
			TellerWriteFlow flow = new TellerWriteFlow(flow_no, 0, "-", guest_id, "-", "�ȴ�", day, server_id);
			//TellerWriteFlow flow = new TellerWriteFlow("112", 100, "card_id", "user_id", "demo", "state", "2011-8-25", "01");
			flow.write();
			
			System.out.println(registerMessage);
			out.println(registerMessage);
			System.out.println("Teller�ˣ����͵������� |"+registerMessage);
			out.flush();
			
			String myResp = in.readLine();
			System.out.println("Teller�ˣ��յ��������� |"+myResp);
			String[] mess = myResp.split(";");
			String[] falg = mess[0].split("=");
			
			ChangeFlow changeFlow = new ChangeFlow();
			System.out.println(falg[1]);
			if(falg[1].equals("1")){
				String flag = changeFlow.changeState(flow_no, day, "1");
				System.out.println("Teller�ˣ���ˮ�޸ĳɹ����|"+flag);
			}
			req.setAttribute("result",myResp);
			req.getRequestDispatcher("register.jsp").forward(req, resp);
			
		} 
	}

}
