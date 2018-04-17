package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.dao.PurchaseSerivceDAO;

public class DeletePurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
			
		PurchaseSerivceDAO pd = new PurchaseSerivceDAO();
		
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		pd.deletePurchase(tranNo);
		
		
		
		return "forward:/listPurchase.do";
	}

}
