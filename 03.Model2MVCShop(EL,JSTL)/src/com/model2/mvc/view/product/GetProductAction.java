package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;


public class GetProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("/////GetProductAction class execute start/////");
		
		String menu = request.getParameter("menu"); // request객체가 가지는 "menu"의 이름을 가지는 value값을 String menu에 대입
		System.out.println("get product action 내부 request.getParameter menu : "+menu);
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		//"prodNo"이름을 가지는 parameter를 request객체로부터 불러와서 Integer로 만들어준 후, productNo에 대입.
		System.out.println("productNo : "+prodNo);
		
		ProductService service=new ProductServiceImpl(); 
		//service 라는 이름을 가지는 ProductService 객체를 생성 : ProductServiceImpl(실제로 구현된 ProductServiceImpl의 메소드를 쓸 예정)
		Product vo=service.getProduct(prodNo);
		
		request.setAttribute("vo", vo); //request 객체에 "vo" 라는 이름으로 vo 속성부여
				
		System.out.println("GetProductAction 내부 setAttribute 후 vo : "+vo);
		System.out.println("get product action if 문 직전");
		
	
		
		//구매완료후 Enduser접속시 구매취소버튼을 위한 로직
		if(request.getParameter("cancel")!=null) {
			request.setAttribute("cancel",request.getParameter("cancel"));
		}
		//구매취소시 상품번호는 같아도 구매 번호가 다른 상품이 존재 할 수 있음
		//즉 같은 상품인데 수량이 여러개인경우 이때 tranNo가 필요함 그래서 purchase 사용
		Purchase pu = new PurchaseServiceImpl().getPurchase2(vo.getProdNo());
		
		request.setAttribute("pu", pu);
		
		
		//manage 쿠키 로직
		if(menu.equals("manage")){
			System.out.println("/////AddProductAction Action class execute end : b4 return/////");
			return "forward:/updateProductView.do";
		}else{
			System.out.println("/////AddProductAction Action class execute end : b4 return/////");
			
			//manage일 경우는 필요 없다 : 쿠키리스트? 에 새롭게 만들어질 쿠키를 넣어서 history.jsp에서 모두를 출력예정
			Cookie[] cookies = request.getCookies(); //request 객체로부터 쿠키를 받아서 Cookies[] data type의 cookies 라는 참조변수에 대입
			Cookie cookie = null; // Cookie data type의 cookie instance 생성하고 초기값 null
			for(int i = 0; i < cookies.length; i++){ //cookies라는 list에 존재하는 모든 cookie들에 대하여 
				if(cookies[i].getName().equals("history")){ // cookies[]에 각각의 cookie 이름에 "history"라는 name을 가지는 cookie가 존재한다면
					cookie = cookies[i];
					cookie.setValue(cookies[i].getValue()+","+prodNo); // 그 cookie에 history 라는 이름을 가진 cookie 객체를 생성한다.
					cookie.setMaxAge(60*10); //쿠키의 소멸예정시각 10분
					
				}
			}
			
			if(cookie == null){ //for문을 다 돌려서 모든 cookie를 검색해도 history 이름을 가진 cookie가 존재하지 않는다면,
				cookie = new Cookie("history", ""+prodNo); //
				cookie.setMaxAge(60*10);
			}
			response.addCookie(cookie);
			if(request.getParameter("tranCode")!=null) {
			request.setAttribute("tranCode", request.getParameter("tranCode"));
			}
			
			
			//!상품클릭시 조회수 상승을 위한 로직
			
			/*request.getParameter("lookup");
			int lookup = Integer.parseInt(request.getParameter("lookup"));
			System.out.println("조회수 들어온값 확인 =====:::"+lookup);*/
			//필요없어짐 prodNo만있으면됨
			String lookup = request.getParameter("lookup");
			System.out.println(lookup+"***********lookup체크한것");
			if(lookup!=null && lookup.equals("yes")) {
			ProductDAO prod = new ProductDAO();
			prod.updatelookup(prodNo);
			System.out.println("updateLookup메소드실행완료======");
			}
			
			
			
			
			return "forward:/product/getProduct.jsp?productNo"+prodNo;
		}
	}//end of execute
}
