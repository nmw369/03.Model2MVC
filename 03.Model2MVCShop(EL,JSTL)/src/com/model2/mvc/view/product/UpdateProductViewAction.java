package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductViewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("/////UpdateProductViewAction class execute start/////");
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		String menu = request.getParameter("menu");
		System.out.println("UpdateProductViewAction »Æ¿Œ  menu : "+menu);
		
		ProductService productService=new ProductServiceImpl();
		Product product=productService.getProduct(prodNo);
		
		request.setAttribute("vo", product);
		
		System.out.println("/////UpdateProductViewAction class execute end : b4 return/////");
		
		return "forward:/product/updateProduct.jsp";
	}
}
