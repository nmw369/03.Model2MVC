package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("/////AddProductAction Action class execute start/////");
		
		Product product=new Product();
		product.setProdName(request.getParameter("prodName")); 
		//request 객체에 존재하는 "prodName"라는 name을 가지는 value를 뽑아서 product 객체 Field에 존재하는 ProdName으로 set
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		product.setTotalEA(Integer.parseInt(request.getParameter("totalEA")));
		
		System.out.println("product : "+product);
		
		ProductService service=new ProductServiceImpl();
		service.addProduct(product);
		
		request.setAttribute("product", product);
		// request 객체를 jsp에게 addProduct.jsp로 넘겨주기 위해서 product 객체를 "product"라는 이름으로 속성 setting
		
		System.out.println("/////AddProductAction Action class execute end : b4 return/////");
		
		return "forward:/product/addProduct.jsp";
	}
}