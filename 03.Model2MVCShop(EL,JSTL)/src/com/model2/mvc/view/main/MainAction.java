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
		
		/*request.setAttribute("a", "자전거");
		request.setAttribute("b", "노트북");*/
		
		System.out.println("mainAction 출력 : "+request.getAttribute("list"));
		
		//날짜 체크해서 현재 날짜와 다르면 그전에 저장된 조회수를 lookup table 에 옮긴후 다시 1부터 시작
		
		Date today = new Date();
	    System.out.println(today);
	        
	    SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
	        
	    System.out.println("Date: "+date.format(today));
	    System.out.println("Time: "+time.format(today));
	    System.out.println("날짜 출력 확인하기");
	    
	    new ProductDAO().daylookup(date.format(today));
	    
	    request.setAttribute("a", "자전거");
		
	    HttpSession session = request.getSession(false);
	    User user = (User)session.getAttribute("user");
	    
	    if(user!=null && user.getRole()=="admin") {
	    	return "forward:/main/mainChart.jsp";
	    }else {
	        
		return "forward:/main/mainView.jsp";
	    }
	}

}
