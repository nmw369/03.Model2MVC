package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Search search = new Search();
		int page=1;
		
		HttpSession session = request.getSession(false);
		
		User userVO = (User) session.getAttribute("user");
		//현재 로그인한 아이디의 데이터를 담은 객체가 저장되어있는 user를 호출해서 넣는다.
		System.out.println(userVO+"____________출력해보기");
		
		
		///page처리 확인
		
		if(request.getParameter("page")!=null) {
			page=Integer.parseInt(request.getParameter("page"));
		}else {
			page=1;
		}
		search.setCurrentPage(page);
		
		String pageUnit = getServletContext().getInitParameter("pageSize");
		search.setPageSize(Integer.parseInt(pageUnit));
		
		System.out.println("ListPurchaseAction : " + search);
		System.out.println("ccccccccccccccccccccccccccccccc :"+userVO.getUserId());
		PurchaseService service = new PurchaseServiceImpl();
		HashMap<String, Object> map = service.getPurchaseList(search, userVO.getUserId());
		
		request.setAttribute("map", map);
		request.setAttribute("Search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
