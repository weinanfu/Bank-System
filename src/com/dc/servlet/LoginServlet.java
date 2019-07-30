package com.dc.servlet;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dc.DAO.LoginDAO;
import com.dc.bean.LoginBean;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String IP = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("GBK");
		resp.setContentType("text/html;charset=GBK");
		
		String user_name = req.getParameter("user_name");
		String user_password = req.getParameter("user_password");
		String org_id = req.getParameter("org_id");
		String net_id = req.getParameter("net_id");
		String login_no = req.getParameter("login_no");
		
		LoginBean loginBean = new LoginBean();
		
		loginBean.setUser_name(user_name);
		loginBean.setUser_password(user_password);
		loginBean.setOrg_id(org_id);
		loginBean.setNet_id(net_id);
		loginBean.setLogin_no(login_no);
		
		LoginDAO loginDAO = new LoginDAO(loginBean);
		System.out.println(loginDAO.isLogin()+"µÇÂ¼³É¹¦Óë·ñ");
		if(loginDAO.isLogin()){
			this.IP = InetAddress.getLocalHost().getHostAddress();
			HttpSession session = req.getSession(true);
			session.setAttribute("user", loginBean);
			int n = loginDAO.addToLoginTable(IP);
			System.out.println(n);
			resp.sendRedirect("main.jsp");
		} else {
			resp.sendRedirect("login.jsp");
		}
	}

}
