package com.model2.mvc.webSocket;

import java.net.InetAddress;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;

public class ChatingAction extends Action{

	public ChatingAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// 자신의 IP 출력
	      InetAddress local = InetAddress.getLocalHost();
	      System.out.println("My PC IP :" + local.getHostAddress());
	      String IP = local.getHostAddress();
	      
	      ServletContext application = this.getServletContext();
	      application.setAttribute("serverIP", IP);
	      
		return "forward:/webSocket/chating.jsp";
	}
	

}
