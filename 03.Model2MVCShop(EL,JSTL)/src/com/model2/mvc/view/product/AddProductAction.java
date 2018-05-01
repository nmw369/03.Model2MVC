package com.model2.mvc.view.product;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
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
		product.setTotalEA(Integer.parseInt(request.getParameter("totalEA")));
		
		//상품등록시 파일경로를 읽어서 현재 webContent의 images로 보내는 로직
			
		String uri = request.getParameter("fileName");
		
		System.out.println("넘어온 경로 !!!!!!!"+uri);
	    File file = new File(uri);
	    //바이트에 동적할당 FileInputStream에 바이트넣으면 이미지 깨짐 DataInputStream사용할것
	    byte[] bytes = new byte[(int)file.length()];
	    DataInputStream dateInput = new DataInputStream(new FileInputStream(file));
	    dateInput.readFully(bytes);
	    dateInput.close();
	    
	    String fileName = uri.substring(uri.lastIndexOf("\\")+1, uri.indexOf(".")+4);
	    
	    System.out.println(fileName+"=============================================이미지 파일 이름 출력");
	    
	    FileOutputStream copyfile = new FileOutputStream("C:\\Users\\Bit\\git\\03.Model2MVC\\03.Model2MVCShop(EL,JSTL)\\WebContent\\images\\uploadFiles\\"+fileName);
	    
	    copyfile.write(bytes);
	    copyfile.close();
		
		
		
		
		
		product.setFileName(fileName);
		
		
		
		System.out.println("product : "+product);
		
		ProductService service=new ProductServiceImpl();
		service.addProduct(product);
		
		request.setAttribute("product", product);
		// request 객체를 jsp에게 addProduct.jsp로 넘겨주기 위해서 product 객체를 "product"라는 이름으로 속성 setting
		
		System.out.println("/////AddProductAction Action class execute end : b4 return/////");
		
		return "forward:/product/addProduct.jsp";
	}
}