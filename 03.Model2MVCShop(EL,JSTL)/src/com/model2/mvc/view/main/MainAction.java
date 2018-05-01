package com.model2.mvc.view.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
		//날짜별 조회수 리스트를 위한 로직
		
		if(request.getParameter("manuDate")!=null) {
		//조회수리스트 제목용 날짜
		request.setAttribute("day", request.getParameter("manuDate"));
		
		String day = request.getParameter("manuDate").replaceAll("-", "");
		//달력값 날짜
		
			
		request.setAttribute("pday", day);
		
		
		map = new ProductDAO().lookuplist(day);
		
		
		request.setAttribute("lookuplist", map.get("lookuplist"));
		
		System.out.println(day+"=====::parsing한 날짜");
		
		}else {
			Date today = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			
			String day = date.format(today);
			//달력값 날짜
			
				
			request.setAttribute("pday", day);
			
			
			map = new ProductDAO().lookuplist(day);
			
			
			request.setAttribute("lookuplist", map.get("lookuplist"));
		}
		
		System.out.println("조회수 리스트 체크:::"+request.getAttribute("lookuplist"));
		
		
		System.out.println("mainAction 출력 : "+request.getAttribute("list"));
		
		//날짜 체크해서 현재 날짜와 다르면 그전에 저장된 조회수를 lookup table 에 옮긴후 다시 1부터 시작
		
		Date today = new Date();
	    System.out.println(today);
	        
	    SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
	    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
	        
	    System.out.println("Date: "+date.format(today));
	    System.out.println("Time: "+time.format(today));
	    System.out.println("날짜 출력 확인하기");
	    
	    new ProductDAO().daylookup(date.format(today));
	    
	   
		
	    HttpSession session = request.getSession(false);
	    User user = (User)session.getAttribute("user");
	    if(user== null) {
	    	user = new User();
	    	user.setRole("user");
	    }
	    
	    System.out.println(user.getRole()+"::::adminCheck");
	    //차트랑 조회수 구분
	    String start = request.getParameter("start");
	    
	    System.out.println(start+":::start체크");
	    
	    if(start!=null && start.equals("yes")) {
	    request.setAttribute("start", start);
	    }else {
	    request.setAttribute("start", start);
	    }
	    
	    System.out.println(request.getAttribute("start")+":::start체크2");
	    
	    
	    ///차트랑 리스트 날짜 별 조회
	    System.out.println(request.getParameter("manuDate")+"::::날짜 체크!!!");
	    request.setAttribute("manuDate", request.getParameter(date.format(today)));
	    
	    request.setAttribute("a" ,"자전거");
	    
	    //접속한 client ip systemlog io로 txt에 입력하기
  		  System.out.println(request.getRemoteAddr()+"::접속한 클라이언트 IP 정보");
  	      String ip = request.getRemoteAddr();
  		
  	      BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Bit\\git\\03.Model2MVC\\03.Model2MVCShop(EL,JSTL)\\IPlog\\IpLog.txt",true));
  	      //true 쓰면 기존 파일내용에 뒤에 추가되는 내용이 append된다. default가 false이므로 써준것
  	      
  	      String logday = date.format(today)+"-"+time.format(today);
  	      
  	      String ipLog = ip+"날짜-시간:"+logday+"//접속ID:"+user.getUserId();
  	      
  	      bw.write(ipLog);
  	      bw.newLine();
  	      bw.flush();
  	      
  	      bw.close();
	  
	    
	    
	    
		return "forward:/main/mainView.jsp";
	   
	}

}
