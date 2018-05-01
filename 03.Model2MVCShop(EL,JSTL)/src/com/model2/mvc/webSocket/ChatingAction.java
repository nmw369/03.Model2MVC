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
		// �ڽ��� IP ���
	     
	      System.out.println(request.getRemoteAddr()+"::ä�ÿ� ������ Ŭ���̾�Ʈ IP ����");
	      String ip = request.getRemoteAddr();
	      
	      /*request.setAttribute("clientIP", request.getRemoteAddr());*/
	      /*ServletContext application = this.getServletContext();
	      if(ip.equals("127.0.0.1")) {
	    	  application.setAttribute("adminIP", application.getAttribute("serverIP"));
	    	  System.out.println("��µǳ���?????123"+application.getAttribute("adminIP"));
	    	  
	      }else if(application.getAttribute("serverIP")!=request.getRemoteAddr()){
	      application.setAttribute("clientIP", request.getRemoteAddr());
	      System.out.println("��µǳ���?????789::"+application.getAttribute("clientIP"));
	      
	      }*/
	      
	      //������ client ip systemlog io�� txt�� �Է��ϱ�
	      
	      
	      
	      
	      HttpSession session = request.getSession(false);
	      User user = (User)session.getAttribute("user");
	      
	      //ipcheck 
	      new UserDao().checkIP(ip);
	      
	      /*Map<String, Object> map = new UserDao().ip*/
	      
	      request.setAttribute("clientIP", request.getRemoteAddr());
	      
	      
	      
	      
	      return "forward:/webSocket/chating.jsp";
	}
	

}
