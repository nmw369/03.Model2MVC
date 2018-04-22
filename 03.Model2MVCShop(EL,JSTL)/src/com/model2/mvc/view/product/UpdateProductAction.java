package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession; 여기에 session이 필요있나?

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("/////UpdateProductAction class execute start/////");

		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println("prodNo : "+prodNo);//
		
		Product product=new Product();
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		product.setnEA(Integer.parseInt(request.getParameter("nEA")));
		
		ProductService productService=new ProductServiceImpl(); // regData가 null 이므로 DAO를 추상화한 Service instance 생성하여 불러온다.
		productService.updateProduct(product); // updateProduct후에 이 service instance는 사라진다.
		
		//HttpSession session = request.getSession();
		//int sessionNo = ((Product)session.getAttribute("product")).getProdNo();
		
		//System.out.println("UpdateProductAction에서 sessionNo : "+sessionNo);
		
		//if(sessionNo == prodNo){
			//session.setAttribute("product", product);
		//}
		
		request.setAttribute("vo", productService.getProduct(prodNo));
		
		System.out.println("/////UpdateProductAction class execute end : b4 return"); 
		
		return "forward:/product/getProduct.jsp?prodNo="+prodNo+"&menu=manage";
	}
}
