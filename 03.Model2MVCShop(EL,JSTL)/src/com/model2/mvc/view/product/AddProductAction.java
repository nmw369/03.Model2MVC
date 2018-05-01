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
		//request ��ü�� �����ϴ� "prodName"��� name�� ������ value�� �̾Ƽ� product ��ü Field�� �����ϴ� ProdName���� set
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setTotalEA(Integer.parseInt(request.getParameter("totalEA")));
		
		//��ǰ��Ͻ� ���ϰ�θ� �о ���� webContent�� images�� ������ ����
			
		String uri = request.getParameter("fileName");
		
		System.out.println("�Ѿ�� ��� !!!!!!!"+uri);
	    File file = new File(uri);
	    //����Ʈ�� �����Ҵ� FileInputStream�� ����Ʈ������ �̹��� ���� DataInputStream����Ұ�
	    byte[] bytes = new byte[(int)file.length()];
	    DataInputStream dateInput = new DataInputStream(new FileInputStream(file));
	    dateInput.readFully(bytes);
	    dateInput.close();
	    
	    String fileName = uri.substring(uri.lastIndexOf("\\")+1, uri.indexOf(".")+4);
	    
	    System.out.println(fileName+"=============================================�̹��� ���� �̸� ���");
	    
	    FileOutputStream copyfile = new FileOutputStream("C:\\Users\\Bit\\git\\03.Model2MVC\\03.Model2MVCShop(EL,JSTL)\\WebContent\\images\\uploadFiles\\"+fileName);
	    
	    copyfile.write(bytes);
	    copyfile.close();
		
		
		
		
		
		product.setFileName(fileName);
		
		
		
		System.out.println("product : "+product);
		
		ProductService service=new ProductServiceImpl();
		service.addProduct(product);
		
		request.setAttribute("product", product);
		// request ��ü�� jsp���� addProduct.jsp�� �Ѱ��ֱ� ���ؼ� product ��ü�� "product"��� �̸����� �Ӽ� setting
		
		System.out.println("/////AddProductAction Action class execute end : b4 return/////");
		
		return "forward:/product/addProduct.jsp";
	}
}