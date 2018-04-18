package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.dao.PurchaseSerivceDAO;


public class UpdatePurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("=================================update����===================================");
		System.out.println("upPurchaseȮ��:::::::"+request.getParameter("tranNo"));
		Purchase purchase = new PurchaseSerivceDAO().findPurchase(Integer.parseInt(request.getParameter("tranNo").trim()));
		
		
		
		System.out.println("�������� Ȯ��!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+purchase);
		
				
		String str = purchase.getDivyDate().substring(0,purchase.getDivyDate().indexOf(" "));
		String temp = str.replaceAll("-", "");
		
		System.out.println(temp+"parsing�Ѱ� ���!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		purchase.setDivyDate(temp);
		
		request.setAttribute("purchaseVO", purchase);	
		
		System.out.println("=================================update����Ϸ�===================================");
		
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

	
}
