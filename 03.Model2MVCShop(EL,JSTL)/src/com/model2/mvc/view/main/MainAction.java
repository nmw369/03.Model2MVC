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
		
		/*request.setAttribute("a", "������");
		request.setAttribute("b", "��Ʈ��");*/
		
		System.out.println("mainAction ��� : "+request.getAttribute("list"));
		
		//��¥ üũ�ؼ� ���� ��¥�� �ٸ��� ������ ����� ��ȸ���� lookup table �� �ű��� �ٽ� 1���� ����
		
		Date today = new Date();
	    System.out.println(today);
	        
	    SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
	        
	    System.out.println("Date: "+date.format(today));
	    System.out.println("Time: "+time.format(today));
	    System.out.println("��¥ ��� Ȯ���ϱ�");
	    
	    new ProductDAO().daylookup(date.format(today));
	    
	    request.setAttribute("a", "������");
		
	    HttpSession session = request.getSession(false);
	    User user = (User)session.getAttribute("user");
	    
	    if(user!=null && user.getRole()=="admin") {
	    	return "forward:/main/mainChart.jsp";
	    }else {
	        
		return "forward:/main/mainView.jsp";
	    }
	}

}
