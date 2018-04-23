package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.dao.PurchaseSerivceDAO;

public class CancelListAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new PurchaseSerivceDAO().cancelList();
		
		System.out.println(map.get("list")+"::cancel 확인");
		System.out.println(map.get("totalCount")+"::cancel 확인");
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("totalCount", map.get("totalCount"));		
		
		return "forward:/purchase/cancelList.jsp";
	}

}
