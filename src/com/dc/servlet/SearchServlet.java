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

import com.dc.bean.Reference;

public class SearchServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	private String IP = Reference.IP;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("GBK");
		resp.setContentType("text/html;charset=GBK");
		String server_id = req.getParameter("server_id");
		String card_id = req.getParameter("card_id");
		String searchAccountMessage ="server_id="+server_id+";flow_no=null;card_id="+card_id+";card_password=null;getMoney=null";
		System.out.println(server_id);
		Socket socket = new Socket(IP, 9999);
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out.println(searchAccountMessage);
		System.out.println("Teller�ˣ����͵������� |"+searchAccountMessage);
		out.flush();
		
		
		String myResp = in.readLine();
		socket.close();
		System.out.println("Teller�ˣ��յ��������� |"+myResp);
		req.setAttribute("result", myResp);
		req.getRequestDispatcher("checkUser.jsp").forward(req, resp);
	}

}
