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
		//��ǰ ������ �ʿ��ϴϱ� ��page�� readProduct���� prodNo�� �޾ƿͼ� �����ش�.
		//�α����� ����� ������ LoginAction�� ���� session���� �̹� �����Ǿ��ִ�.
		request.setAttribute("prodVO", prod);
		
		System.out.println("PurView �Ϸ� ::"+prod);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
}
