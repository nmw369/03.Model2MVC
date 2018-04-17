package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.dao.ProductDAO;


public class AddPurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Product prod = new ProductDAO().findProduct(Integer.parseInt(request.getParameter("prodNo")));		
		//상품 정보가 필요하니까 전page인 readProduct에서 prodNo를 받아와서 보여준다.
		//로그인한 사용자 정보는 LoginAction에 보면 session으로 이미 설정되어있다.
		request.setAttribute("prodVO", prod);
		
		System.out.println("PurView 완료 ::"+prod);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
}
