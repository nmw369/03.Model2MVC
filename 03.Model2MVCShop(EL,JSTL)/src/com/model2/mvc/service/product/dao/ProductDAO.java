package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;


public class ProductDAO {
	
	public ProductDAO(){
	}

	public void insertProduct(Product product) throws Exception { // 상품등록
		
		System.out.println("/////insertProduct method start/////");		
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate().replaceAll("-", ""));
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println("/////insertProduct method end/////");
	}

	public Product findProduct(int prodNo) throws Exception {
		
		System.out.println("/////findProduct method start/////");
		
		Connection con = DBUtil.getConnection();

		//String sql = "SELECT * FROM product WHERE prod_no=?";
		
		String sql ="select p.prod_no,p.prod_name,p.prod_detail,p.manufacture_day,p.price,p.image_file,p.reg_date, nvl(t.tran_status_code,1)"
				+ " from transaction t, product p"
				+ " where t.prod_no(+)=p.prod_no and p.prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);

		ResultSet rs = pStmt.executeQuery();

		Product product = null;
		
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt(1));
			product.setProdName(rs.getString(2));
			product.setProdDetail(rs.getString(3));
			product.setManuDate(rs.getString(4));
			product.setPrice(rs.getInt(5));
			product.setFileName(rs.getString(6));
			product.setRegDate(rs.getDate(7));
			product.setProTranCode(rs.getString(8).trim());
		}
		
		System.out.println("product.getProdNo() : "+product.getProdNo());
		
		rs.close();
		pStmt.close();
		con.close();
		
		
		System.out.println("/////findProduct method end/////");
		
		return product;
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		
		System.out.println("/////getProductList method start/////");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		/*String sql = "SELECT * FROM product ";
		
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_no" + " like '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1")  && !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_name" + " like '%" + search.getSearchKeyword() + "%'";
			}
		}
		
		sql += " ORDER BY prod_no";*/
		
		String sql = "select p.prod_no,p.prod_name,p.prod_detail,p.manufacture_day,p.price,p.image_file,p.reg_date, nvl(t.tran_status_code,1)"
				+ " from transaction t, product p"
				+ " where t.prod_no(+)=p.prod_no";
		
		/*String sql = "select p.*, nvl(t.tran_status_code,1)"
				+ " from transaction t, product p"
				+ " where t.prod_no(+)=p.prod_no";*/
		
		if (search.getSearchCondition() != null) {
			System.out.println("searchVO.getSearchCondition() : " + search.getSearchCondition());
			// Search product number
			if (search.getSearchCondition().equals("0")) {
				sql += " and p.PROD_NO Like '%" + search.getSearchKeyword()+"%'";
			}
			// Search product name
			else if (search.getSearchCondition().equals("1")) {
				sql += " and p.PROD_NAME LIKE '%" + search.getSearchKeyword() + "%'";
			} 
			// Search product price
			else if(search.getSearchCondition().equals("2")){
				sql += " and p.PRICE Like '%" + search.getSearchKeyword()+"%'";
			}
		}
		sql += " order by p.REG_DATE desc nulls last";
		
		
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount :: "+totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		
		System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUU::::"+sql);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);
		
		List<Product>	list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdNo(rs.getInt(1));
			product.setProdName(rs.getString(2));
			product.setProdDetail(rs.getString(3));
			product.setManuDate(rs.getString(4));
			product.setPrice(rs.getInt(5));
			product.setFileName(rs.getString(6));
			product.setRegDate(rs.getDate(7));
			product.setProTranCode(rs.getString(8).trim());
			list.add(product);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("/////getProductList method end..../////");
		
		return map;
	}

	public void updateProduct(Product product) throws Exception { // 상품수정
		
		System.out.println("=====updateProduct method start=====");
		System.out.println("updateProduct시작=> method내부에 저장된 productVO : "+product);
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product SET prod_name=?,prod_detail=?,"
				+ "manufacture_day=?, price=?,image_file=?,"
				+ "reg_date=SYSDATE WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.setInt(6, product.getProdNo());
		pStmt.executeUpdate(); //////////////
		System.out.println("pStmt.executeUpdate() : "+pStmt.executeUpdate());
		
		System.out.println("updateProduct method 내부 reg_date 확인출력 : "+product.getRegDate());
		pStmt.close();
		con.close();
		
		
		System.out.println("=====updateProduct method end....=====");
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
			
			System.out.println("=====getTotalCount method start=====");
			
			sql = "SELECT COUNT(*) "+
			          "FROM ( " +sql+ ") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if( rs.next() ){
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			
			System.out.println("=====getTotalCount method end....=====");
			
			return totalCount;
		}
		
		// 게시판 currentPage Row 만  return 
		private String makeCurrentPageSql(String sql , Search search){
			
			System.out.println("=====makeCurrentPageSql method start=====");
			
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("ProductDAO :: make SQL :: "+ sql);	
			
			System.out.println("=====makeCurrentPage method end....=====");
			
			return sql;
		}
}
