package com.model2.mvc.view.purchase;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;

import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.dao.UserDao;


public class AddPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//���ſ�û�� �ؾ��� 
		Purchase purchase = new Purchase();
		
//		ProductDAO productDAO = new ProductDAO();
//		ProductVO vo = productDAO.findProduct(Integer.parseInt(request.getParameter("prodNo")));
				
		purchase.setPurchaseProd(new ProductDAO().findProduct(Integer.parseInt(request.getParameter("prodNo"))));
		purchase.setBuyer(new UserDao().findUser(request.getParameter("buyerId")));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode("2");
		purchase.setsEA(Integer.parseInt(request.getParameter("sEA").trim()));
		System.out.println(purchase+"////////////////////PurAction �� ���� �Ϸ��");
		
		PurchaseService service=new PurchaseServiceImpl();
		
		service.addPurchase(purchase);
		
		//������ ���� ������ ���� ����
		Product prod = purchase.getPurchaseProd();
		
		new ProductDAO().updateEA(purchase.getsEA(), prod);
		
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@addpurchase���� ������::"+purchase);
		
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
}
