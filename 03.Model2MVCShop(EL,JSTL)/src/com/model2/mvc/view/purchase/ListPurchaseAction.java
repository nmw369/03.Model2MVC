package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		
		User userVO = (User) session.getAttribute("user");
		System.out.println(userVO+"____________����غ���");
		//���� �α����� ���̵��� �����͸� ���� ��ü�� ����Ǿ��ִ� user�� ȣ���ؼ� �ִ´�.
		
		
		///������ ó��
		Search search = new Search();
		
		int currentPage=1;
			
		//������������ �����ϱ� ���� ����		
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : "+currentPage); // currentPage �� ��������Ѵ�. 2������ 2
		}
		
		//���������� ����
		search.setCurrentPage(currentPage);
		//xml�� metadata ȭ�� ���� ������
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));//3
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));//5
		//������ ��������
		search.setPageSize(pageSize);
		
		System.out.println("ListPurchaseAction : " + search);
		
		//list ������ ǥ���ϱ����� �ν��Ͻ� ������ ������ seting �� ������ ������ map�� �����Ѵ�. 
		PurchaseService service = new PurchaseServiceImpl();
		HashMap<String, Object> map = service.getPurchaseList(search, userVO.getUserId());
		
		//page ���� �����ϱ����� ��������
		System.out.println(currentPage+"!!!!!!"+((Integer)map.get("totalCount")).intValue()+"!!!!!!!!!!"+ pageUnit+"!!!!!!!!!"+ pageSize+"��������!!!!!!");
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		
		//���� ������ ���� ������ request�� ��Ƽ� ����
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("Search", search);
		
		//Purchase pur = (Purchase)map.get("list");
		//list ������ȸ�� ���� Product ����
				
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
