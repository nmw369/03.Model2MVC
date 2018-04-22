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
			System.out.println("currentPage : "+currentPage); // currentPage 가 나와줘야한다. 2누르면 2
		}
		
		
		
		///상품가격 순서를 위한 로직
		String sorting = "low";
		System.out.println("sorting를 위한 parameter!!!!!!!!!!!!!!!!!+++++++:::::"+sorting);
		
		if(request.getParameter("sorting")!=null && request.getParameter("sorting").equals("high")) {
			search.setSorting(request.getParameter("sorting"));
			sorting = request.getParameter("sorting");
		}else {
			search.setSorting(sorting);
		}
		
		request.setAttribute("sorting", sorting);
		///
		
		
		///상품 등록날짜 순서를 위한 로직
		String daysorting = "";
		System.out.println("Daysorting를 위한 parameter!!!!!!!!!!!!!!!!!+++++++:::::"+daysorting);
		
		if(request.getParameter("daysorting")!=null && request.getParameter("daysorting").equals("highDay")) {
			search.setDaySorting(request.getParameter("daysorting"));
			daysorting = request.getParameter("daysorting");
		}else {
			daysorting = request.getParameter("daysorting");
			search.setDaySorting(daysorting);
		}
		
		request.setAttribute("daysorting", daysorting);
		///
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml meta-data 로 부터 상수 추출
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));//3
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));//5
		search.setPageSize(pageSize);
		
		// Business logic 수행
		ProductService productService = new ProductServiceImpl();
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
									//3		  ,          
		System.out.println("listUserAction ::"+resultPage);
		
		//Model 과 View 연결
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("total", map.get("totalCount"));
		
		//상품번호를위한 것
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		System.out.println(currentPage+":"+pageSize+":::::rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr현재 페이지:PageSize");
		
					
		///user와 admin 계정 접속시 보여주는 화면 다르게 하기위해 분리
		HttpSession session = request.getSession(false);
		
		User user = (User) session.getAttribute("user");
		
		String role = "user";
		
		if(user!=null) {
		role = user.getRole();
		}
		//System.out.println("======================접속아이디체크==============================="+role);
		/*String uri = "";
		if(role.equals("admin")) {
			uri="/product/listProduct.jsp";
		}else {
			uri="/product/listProductUser.jsp";
		}*/
		
		
		System.out.println("===============Map에 담긴값::::"+map.get("list"));
		System.out.println("/////listProduct Action class execute end : b4 return/////"); 
		
		
		
		return "forward:/product/listProduct.jsp";
	}
}
