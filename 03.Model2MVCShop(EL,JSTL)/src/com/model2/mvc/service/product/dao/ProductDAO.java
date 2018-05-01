package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE,'1',?,?,?,'1')";
		
		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate().replaceAll("-", ""));
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.setString(6, date.format(today));
		pStmt.setInt(7, product.getTotalEA());
		pStmt.setInt(8, product.getTotalEA());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println("/////insertProduct method end/////");
	}

	public Product findProduct(int prodNo) throws Exception {
		
		System.out.println("/////findProduct method start/////");
		
		Connection con = DBUtil.getConnection();

		//String sql = "SELECT * FROM product WHERE prod_no=?";
		
		String sql ="select prod_no,prod_name,prod_detail,manufacture_day,price,image_file,reg_date,nea,ea_code,lookup" 
				+	" from  product"
				+ " where prod_no=?";
		
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
			product.setnEA(rs.getInt(8));
			product.setEaCode(rs.getInt(9));
			product.setLookup(rs.getInt(10));
			if(product.getnEA()==0) {
				product.setEaCode(0);	
			}else {
				product.setEaCode(rs.getInt(9));
			}
			
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
		
		String sql = "select prod_no,prod_name,prod_detail,manufacture_day,price,image_file,reg_date,lookup,nea,ea_code"
				+ " from  product";
		
		/*String sql = "select p.*, nvl(t.tran_status_code,1)"
				+ " from transaction t, product p"
				+ " where t.prod_no(+)=p.prod_no";*/
		
		if (search.getSearchCondition() != null) {
			System.out.println("searchVO.getSearchCondition() : " + search.getSearchCondition());
			// Search product number
			if (search.getSearchCondition().equals("0")) {
				sql += " where PROD_NO Like '%" + search.getSearchKeyword()+"%'";
			}
			// Search product name
			else if (search.getSearchCondition().equals("1")) {
				sql += " where  PROD_NAME LIKE '%" + search.getSearchKeyword() + "%'";
			} 
			// Search product price
			else if(search.getSearchCondition().equals("2")){
				sql += " where PRICE Like '%" + search.getSearchKeyword()+"%'";
			}
		}
		
		
		
		
		
		if(search.getDaySorting()!=null&&search.getDaySorting().equals("highDay")){
			
			search.setSorting("a");
			sql += " order by reg_date desc nulls last";
			
			System.out.println(search.getSorting()+"!!!!!!!!!!!!!!!!!!!!!!!!!여기출력");
			
		}else if(search.getDaySorting()!=null&&search.getDaySorting().equals("lowDay")){
			
			search.setSorting("a");
			System.out.println(search.getSorting()+"++++++소팅값확인+++++++");
			sql += " order by reg_date asc nulls last";
		}else if(search.getSorting()!="a"){		
		
			if(search.getSorting().equals("high")) {
				sql += " order by price desc nulls last";
			}else if(search.getSorting().equals("low")){
				sql += " order by price asc nulls last";
			}
		}
		
		
		
		
		
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount :: "+totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		
		
		
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
			product.setLookup(rs.getInt(8));
			product.setnEA(rs.getInt(9));
			int temp = Integer.parseInt(rs.getString(10).trim());
			System.out.println(temp+":::tranCode 들어온거 확인");
			if(product.getnEA()<=0) {
				product.setEaCode(0);
			}else {
			product.setEaCode(rs.getInt(10));
			}
			list.add(product);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		
		System.out.println(map.get("totalCount"));
		
		//수량 check 전부팔려서 수량없으면 ProTranCode 0 넣어서 매진표시
		
		
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
		
		public Map<String,Object> mainlist() throws Exception{
			
			System.out.println("/////main method start/////");
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			Connection con = DBUtil.getConnection();
			
			
			String sql = "select * from product";
			
			sql += " order by lookup desc nulls last";
			
					
			System.out.println("ProductDAO::Original SQL :: " + sql);
			
			/*int totalCount = this.getTotalCount(sql);
			System.out.println("ProductDAO :: totalCount :: "+totalCount);*/
			
			//==> CurrentPage 게시물만 받도록 Query 다시구성
			
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			List<Product> list = new ArrayList<Product>();
			
			while(rs.next()){
				Product product = new Product();
				product.setProdNo(rs.getInt(1));
				product.setProdName(rs.getString(2));
				product.setProdDetail(rs.getString(3));
				product.setManuDate(rs.getString(4));
				product.setPrice(rs.getInt(5));
				product.setFileName(rs.getString(6));
				product.setRegDate(rs.getDate(7));
				product.setLookup(rs.getInt(8));
				product.setnEA(rs.getInt(10));
				
				list.add(product);
			}
			
			//==> totalCount 정보 저장
//			map.put("totalCount", new Integer(totalCount));
			//==> currentPage 의 게시물 정보 갖는 List 저장
			map.put("list", list);
			
			rs.close();
			pStmt.close();
			con.close();
			
			System.out.println("/////getProductList method end..../////");
			
			return map;
			
		}
		public void updatelookup(int prodNo) throws Exception{
			
			Connection con = DBUtil.getConnection();
			
			Date today = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			
			/*String sql="update product set lookup = lookup+1 , today=? where prod_no=?";*/
			String sql="update product set lookup = lookup+1 where prod_no=? and today=? ";
						
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, prodNo);
			stmt.setString(2, date.format(today));
			
			ResultSet rs = stmt.executeQuery();
			
			System.out.println("조회수 증가 완료됨!!!!!!");
			con.close();
			stmt.close();
			rs.close();
			
		}
		
		public void daylookup(String day) throws Exception{
			Connection con = DBUtil.getConnection();
			String sql="select today,prod_name,lookup from product";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			Date today = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			
			
			String temp = date.format(today);
			
			System.out.println(temp+":::::daylooup check++++++");
			while(rs.next()) {
				if(!rs.getString(1).equals(temp)) {
					System.out.println(rs.getString(1)+":::::::rsday check--------");
					String sql2="insert into lookup values(?,?,?)";
					stmt = con.prepareStatement(sql2);
					
					stmt.setString(1, rs.getString(1));
					stmt.setString(2, rs.getString(2));
					stmt.setInt(3, rs.getInt(3));
					
					stmt.executeQuery();
					
					String sql3="update product set today=? ,lookup='1' where prod_name=?";
					stmt = con.prepareStatement(sql3);
					stmt.setString(1, date.format(today));
					stmt.setString(2, rs.getString(2));
					stmt.executeQuery();
				}else{
					
				}
			}
			
			con.close();
			stmt.close();
			rs.close();
			
			
		}
		
		
		
		public Map<String,Object> lookuplist(String day) throws Exception{
			
			System.out.println("/////main method start/////");
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			Connection con = DBUtil.getConnection();
			
			Date today = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			
			String sql ="";
			String temp = date.format(today);
			System.out.println(day+"----전달받은날짜------");
			System.out.println(temp+"----비교할날짜------");
			
			
			
			if(temp.equals(day)) {
				
				sql = "select prod_name , lookup ,today from product where today=?";
				
				sql += " order by prod_Name desc nulls last";
				
				System.out.println("ProductDAO::Original SQL :: " + sql);
				
				/*int totalCount = this.getTotalCount(sql);
				System.out.println("ProductDAO :: totalCount :: "+totalCount);*/
				
				//==> CurrentPage 게시물만 받도록 Query 다시구성
				
				PreparedStatement pStmt = con.prepareStatement(sql);
				pStmt.setString(1, day);
				ResultSet rs = pStmt.executeQuery();
				
				List<Product> lookuplist = new ArrayList<Product>();
				
				while(rs.next()){
					Product product = new Product();
					product.setProdName(rs.getString(1));
					product.setLookup(rs.getInt(2));
					product.setManuDate(rs.getString(3));
					
					lookuplist.add(product);
				}
				
				//==> totalCount 정보 저장
//				map.put("totalCount", new Integer(totalCount));
				//==> currentPage 의 게시물 정보 갖는 List 저장
				
				map.put("lookuplist", lookuplist);
				
				rs.close();
				pStmt.close();
				con.close();
				
			}else {
				sql = "select * from lookup where lookup_date=?";
				
				sql += " order by prod_Name desc nulls last";
			
			
			
			
					
			System.out.println("ProductDAO::Original SQL :: " + sql);
			
			/*int totalCount = this.getTotalCount(sql);
			System.out.println("ProductDAO :: totalCount :: "+totalCount);*/
			
			//==> CurrentPage 게시물만 받도록 Query 다시구성
			
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, day);
			ResultSet rs = pStmt.executeQuery();
			
			List<Product> lookuplist = new ArrayList<Product>();
			
			while(rs.next()){
				Product product = new Product();
				product.setManuDate(rs.getString(1));
				product.setProdName(rs.getString(2));
				product.setLookup(rs.getInt(3));
				
				lookuplist.add(product);
			}
			
			//==> totalCount 정보 저장
//			map.put("totalCount", new Integer(totalCount));
			//==> currentPage 의 게시물 정보 갖는 List 저장
			
			map.put("lookuplist", lookuplist);
			
			rs.close();
			pStmt.close();
			con.close();
			}
			System.out.println("/////getlookuplist method end..../////");
			
			return map;
			
		}
		
		
		
		
		
		
		
		//분할구매 시 업데이트
		public void updateEA(int sEA,Product prod) throws Exception{
			Connection con = DBUtil.getConnection();
			
			String sql ="update product set nea=? where prod_Name=?";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, (prod.getnEA()-sEA));
			stmt.setString(2, prod.getProdName());
			stmt.executeUpdate();
			
			stmt.close();
			con.close();
			
		}
		
		public void cancelEA(int sEA,Product prod) throws Exception{
			Connection con = DBUtil.getConnection();
			
			String sql ="update product set nea=? where prod_Name=?";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, (prod.getnEA()+sEA));
			stmt.setString(2, prod.getProdName());
			stmt.executeUpdate();
			
			stmt.close();
			con.close();
			
		}
		//오늘의 실시간 차트 조회
		
		/*public Map<String , Object> todaychart(String today) throws Exception{
			Connection con = DBUtil.getConnection();
			
			String sql ="select prod_Name , lookup from product where today=?";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, today);
			stmt.executeUpdate();
			ResultSet rs = stmt.executeQuery();
			Map<String, Object> map = new HashMap<String, Object>();
			List<Product> prodlist = new ArrayList<Product>();
			
			while(rs.next()) {
				Product product = new Product();
				product.setProdName(rs.getString(1));
				product.setLookup(rs.getInt(2));
				
				prodlist.add(product);
			}
			
			map.put("prodlist", prodlist);
			
			stmt.close();
			rs.close();
			con.close();
			
			return map;
		}
		*/
		
}
