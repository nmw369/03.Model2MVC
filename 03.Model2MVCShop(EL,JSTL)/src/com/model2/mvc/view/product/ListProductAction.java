package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class ListProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println("/////listProduct Action class execute start/////");
		
		Search search=new Search();
		
		int currentPage = 1;
		
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : "+currentPage); // currentPage �� ��������Ѵ�. 2������ 2
		}
		
		
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml meta-data �� ���� ��� ����
		// 
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));//3
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));//5
		search.setPageSize(pageSize);
		
		// Business logic ����
		ProductService productService = new ProductServiceImpl();
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
									//3		  ,          
		System.out.println("listUserAction ::"+resultPage);
		
		//Model �� View ����
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		///user�� admin ���� ���ӽ� �����ִ� ȭ�� �ٸ��� �ϱ����� �и�
		HttpSession session = request.getSession(false);
		
		User user = (User) session.getAttribute("user");
		
		String role = user.getRole();
		
		System.out.println("======================���Ӿ��̵�üũ==============================="+role);
		/*String uri = "";
		if(role.equals("admin")) {
			uri="/product/listProduct.jsp";
		}else {
			uri="/product/listProductUser.jsp";
		}*/
		
		
		System.out.println("===============Map�� ��䰪::::"+map.get("list"));
		System.out.println("/////listProduct Action class execute end : b4 return/////"); 
		
		//return "forward:"+uri;
		return "forward:/product/listProduct.jsp";
	}
}
