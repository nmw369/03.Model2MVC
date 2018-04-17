package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

		@Override
		public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

			int tranCode = Integer.parseInt(request.getParameter("tranCode").trim());
			String role = "";
			HttpSession session = request.getSession(false);
			User user = (User) session.getAttribute("user");

			if (user != null) {
				role = user.getRole();
			}
			
			System.out.println(":::UPcode------"+user);
			
			String uri="";
			
			Purchase purchase = null;
			if (role.equals("admin")) {
				int prodNo = Integer.parseInt(request.getParameter("prodNo"));
				//admin���� ���������� ��ǰ������ ������� ���
				uri += "listProduct.do?menu=manage";
				purchase = new PurchaseServiceImpl().getPurchase2(prodNo);
			} else if (role.equals("user")) {
				int tranNo = Integer.parseInt(request.getParameter("tranNo"));
				uri += "listPurchase.do";
				purchase = new PurchaseServiceImpl().getPurchase(tranNo);
			}
			System.out.println("�����ڵ�::" + tranCode);
			tranCode++;
			System.out.println("������ڵ�::" + tranCode);

			String tran = tranCode+"";
			System.out.println(tran+"=====tran��!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			purchase.setTranCode(tran);
			new PurchaseServiceImpl().updateTranCode(purchase);

			return "forward:/"+uri;
		}
	}

