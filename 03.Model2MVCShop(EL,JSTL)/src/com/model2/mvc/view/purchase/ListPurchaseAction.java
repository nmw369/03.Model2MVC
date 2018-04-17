package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		
		User userVO = (User) session.getAttribute("user");
		System.out.println(userVO+"____________출력해보기");
		//현재 로그인한 아이디의 데이터를 담은 객체가 저장되어있는 user를 호출해서 넣는다.
		
		
		///페이지 처리
		Search search = new Search();
		
		int currentPage=1;
			
		//누른페이지로 변경하기 위한 로직		
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : "+currentPage); // currentPage 가 나와줘야한다. 2누르면 2
		}
		
		//현재페이지 세팅
		search.setCurrentPage(currentPage);
		//xml에 metadata 화한 정보 가져옴
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));//3
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));//5
		//가져온 정보전송
		search.setPageSize(pageSize);
		
		System.out.println("ListPurchaseAction : " + search);
		
		//list 정보를 표현하기위해 인스턴스 생성후 위에서 seting 된 값들을 전송해 map에 저장한다. 
		PurchaseService service = new PurchaseServiceImpl();
		HashMap<String, Object> map = service.getPurchaseList(search, userVO.getUserId());
		
		//page 값을 전달하기위한 로직수행
		System.out.println(currentPage+"!!!!!!"+((Integer)map.get("totalCount")).intValue()+"!!!!!!!!!!"+ pageUnit+"!!!!!!!!!"+ pageSize+"점검하자!!!!!!");
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		
		//로직 수행후 받은 값들을 request에 담아서 전송
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("Search", search);
		
		//Purchase pur = (Purchase)map.get("list");
		//list 정보조회를 위한 Product 정보
				
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
