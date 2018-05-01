package com.model2.mvc.webSocket;

import java.net.InetAddress;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.dao.UserDao;

public class ChatingAction extends Action{

	public ChatingAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// 자신의 IP 출력
	     
	      System.out.println(request.getRemoteAddr()+"::채팅에 접속한 클라이언트 IP 정보");
	      String ip = request.getRemoteAddr();
	      
	      /*request.setAttribute("clientIP", request.getRemoteAddr());*/
	      /*ServletContext application = this.getServletContext();
	      if(ip.equals("127.0.0.1")) {
	    	  application.setAttribute("adminIP", application.getAttribute("serverIP"));
	    	  System.out.println("출력되나요?????123"+application.getAttribute("adminIP"));
	    	  
	      }else if(application.getAttribute("serverIP")!=request.getRemoteAddr()){
	      application.setAttribute("clientIP", request.getRemoteAddr());
	      System.out.println("출력되나요?????789::"+application.getAttribute("clientIP"));
	      
	      }*/
	      
	      //접속한 client ip systemlog io로 txt에 입력하기
	      
	      
	      
	      
	      HttpSession session = request.getSession(false);
	      User user = (User)session.getAttribute("user");
	      
	      //ipcheck 
	      new UserDao().checkIP(ip);
	      
	      /*Map<String, Object> map = new UserDao().ip*/
	      
	      request.setAttribute("clientIP", request.getRemoteAddr());
	      
	      
	      
	      
	      return "forward:/webSocket/chating.jsp";
	}
	

}
