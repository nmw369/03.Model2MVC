package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class UpdatePurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
				
		String tranNo = request.getParameter("tranNo");
		PurchaseService service = new PurchaseServiceImpl();
				
		Purchase purchase = new Purchase();
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		purchase.setTranNo(Integer.parseInt(tranNo));
		
		System.out.println(purchase+"@@@@@@@@@@@@updateAction");
	
		//productVO = service.getProduct(Integer.parseInt(prodno));
		
		service.updatePurcahse(purchase);
		
		request.setAttribute("tranNo", tranNo);
		
		
//		HttpSession session = request.getSession();
//		session.setAttribute("purVO", purchase);
//		session.setAttribute("tranNo", tranNo);
							
		
		return "forward:/getPurchase.do";
	}

}
