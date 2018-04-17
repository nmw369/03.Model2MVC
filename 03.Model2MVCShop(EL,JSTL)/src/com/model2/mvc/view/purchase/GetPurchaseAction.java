package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class GetPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println(tranNo+":::GET의 tranNo");// 여기까지 들어옴
		PurchaseService service =new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(tranNo);
		System.out.println(purchase+"::: GetPuchase 확인!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		request.setAttribute("purchaseVO", purchase);
		
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
}
