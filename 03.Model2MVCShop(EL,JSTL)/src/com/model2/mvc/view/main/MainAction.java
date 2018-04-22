package com.model2.mvc.view.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.dao.ProductDAO;

public class MainAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new ProductDAO().mainlist();
		request.setAttribute("list", map.get("list"));
		//��¥�� ��ȸ�� ����Ʈ�� ���� ����
		if(request.getParameter("manuDate")!=null) {
		//��ȸ������Ʈ ����� ��¥
		request.setAttribute("day", request.getParameter("manuDate"));
		
		String day = request.getParameter("manuDate").replaceAll("-", "");
		//�޷°� ��¥
		request.setAttribute("pday", day);
		
		map = new ProductDAO().lookuplist(day);
		request.setAttribute("lookuplist", map.get("lookuplist"));
		
		System.out.println(day+"=====::parsing�� ��¥");
		
		}
		
		System.out.println("��ȸ�� ����Ʈ üũ:::"+request.getAttribute("lookuplist"));
		
		
		System.out.println("mainAction ��� : "+request.getAttribute("list"));
		
		//��¥ üũ�ؼ� ���� ��¥�� �ٸ��� ������ ����� ��ȸ���� lookup table �� �ű��� �ٽ� 1���� ����
		
		Date today = new Date();
	    System.out.println(today);
	        
	    SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
	    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
	        
	    System.out.println("Date: "+date.format(today));
	    System.out.println("Time: "+time.format(today));
	    System.out.println("��¥ ��� Ȯ���ϱ�");
	    
	    new ProductDAO().daylookup(date.format(today));
	    
	   
		
	    HttpSession session = request.getSession(false);
	    User user = (User)session.getAttribute("user");
	    if(user== null) {
	    	user = new User();
	    	user.setRole("user");
	    }
	    
	    System.out.println(user.getRole()+"::::adminCheck");
	    //��Ʈ�� ��ȸ�� ����
	    String start = request.getParameter("start");
	    
	    System.out.println(start+":::startüũ");
	    
	    if(start!=null && start.equals("yes")) {
	    request.setAttribute("start", start);
	    }else {
	    request.setAttribute("start", start);
	    }
	    
	    System.out.println(request.getAttribute("start")+":::startüũ2");
	    
	    
	    ///��Ʈ�� ����Ʈ ��¥ �� ��ȸ
	    System.out.println(request.getParameter("manuDate")+"::::��¥ üũ!!!");
	    request.setAttribute("manuDate", request.getParameter(date.format(today)));
	    
		return "forward:/main/mainView.jsp";
	   
	}

}
