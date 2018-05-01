package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.dao.PurchaseSerivceDAO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class CancelAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int cancelCode = Integer.parseInt(request.getParameter("cancelCode"));
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase = new PurchaseSerivceDAO().findPurchase(tranNo);
		
		new PurchaseSerivceDAO().updateCancel(purchase);
		
		System.out.println("cancelCode value::"+cancelCode);
		cancelCode++;
		System.out.println("cancelCode value::"+cancelCode);
		
		purchase.setCancelCode(cancelCode);
		
		new PurchaseSerivceDAO().updateCancel(purchase);
		
		HttpSession session = request.getSession(false);
		
		User user = (User)session.getAttribute("user");
		String uri="";
		if(user.getRole()!=null&&user.getRole().equals("admin")) {
			uri="cancelList.do";
		}else {
			uri="listPurchase.do";
		}
		
		
		return "forward:/"+uri;
	}

}
