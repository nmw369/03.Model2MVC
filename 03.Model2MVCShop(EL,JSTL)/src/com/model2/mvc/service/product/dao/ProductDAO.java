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

	public void insertProduct(Product product) throws Exception { // ��ǰ���
		
		System.out.println("/////insertProduct method start/////");		
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE,'1',?,?,?)";
		
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
		
		String sql ="select p.prod_no,p.prod_name,p.prod_detail,p.manufacture_day,p.price,p.image_file,p.reg_date,p.nea, nvl(t.tran_status_code,1)"
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
			product.setnEA(rs.getInt(8));
			product.setProTranCode(rs.getString(9).trim());
			
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
		
		String sql = "select p.prod_no,p.prod_name,p.prod_detail,p.manufacture_day,p.price,p.image_file,p.reg_date,p.lookup,p.nea,nvl(t.tran_status_code,1)"
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
		
		
		System.out.println("=============�־ȳ�����???::::"+search.getDaySorting());
		
		
		if(search.getDaySorting()!=null&&search.getDaySorting().equals("highDay")){
			
			search.setSorting("a");
			sql += " order by p.reg_date desc nulls last";
			
			System.out.println(search.getSorting()+"!!!!!!!!!!!!!!!!!!!!!!!!!�������");
			
		}else if(search.getDaySorting()!=null&&search.getDaySorting().equals("lowDay")){
			
			search.setSorting("a");
			System.out.println(search.getSorting()+"++++++���ð�Ȯ��+++++++");
			sql += " order by p.reg_date asc nulls last";
		}else if(search.getSorting()!="a"){		
		
			if(search.getSorting().equals("high")) {
				sql += " order by p.price desc nulls last";
			}else if(search.getSorting().equals("low")){
				sql += " order by p.price asc nulls last";
			}
		}
		
		System.out.println(search.getDaySorting()+"ccccccccccccccccc"+search.getSorting()+"!!!!!!!!!!�����Ѱ�");
		
		
		
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount :: "+totalCount);
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
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
			product.setLookup(rs.getInt(8));
			product.setnEA(rs.getInt(9));
			int temp = Integer.parseInt(rs.getString(10).trim());
			System.out.println(temp+":::tranCode ���°� Ȯ��");
			if(temp >= 5) {
				product.setProTranCode("1");
			}else {
			product.setProTranCode(rs.getString(10).trim());
			}
			list.add(product);
		}
		
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		
		System.out.println(map.get("totalCount"));
		
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("/////getProductList method end..../////");
		
		return map;
	}

	public void updateProduct(Product product) throws Exception { // ��ǰ����
		
		System.out.println("=====updateProduct method start=====");
		System.out.println("updateProduct����=> method���ο� ����� productVO : "+product);
		
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
		
		System.out.println("updateProduct method ���� reg_date Ȯ����� : "+product.getRegDate());
		pStmt.close();
		con.close();
		
		
		System.out.println("=====updateProduct method end....=====");
	}
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
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
		
		// �Խ��� currentPage Row ��  return 
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
			
			//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
			
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
			
			//==> totalCount ���� ����
//			map.put("totalCount", new Integer(totalCount));
			//==> currentPage �� �Խù� ���� ���� List ����
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
			
			String sql="update product set lookup = lookup+1 , today=? where prod_no=?";
						
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, date.format(today));
			stmt.setInt(2, prodNo);
			ResultSet rs = stmt.executeQuery();
			
			System.out.println("��ȸ�� ���� �Ϸ��!!!!!!");
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
			
			
			String sql = "select * from lookup where lookup_date=?";
			
			sql += " order by prod_Name desc nulls last";
			
					
			System.out.println("ProductDAO::Original SQL :: " + sql);
			
			/*int totalCount = this.getTotalCount(sql);
			System.out.println("ProductDAO :: totalCount :: "+totalCount);*/
			
			//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
			
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
			
			//==> totalCount ���� ����
//			map.put("totalCount", new Integer(totalCount));
			//==> currentPage �� �Խù� ���� ���� List ����
			map.put("lookuplist", lookuplist);
			
			rs.close();
			pStmt.close();
			con.close();
			
			System.out.println("/////getlookuplist method end..../////");
			
			return map;
			
		}
		
		
		
		
		
		
		
		//���ұ��� �� ������Ʈ
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
		
		
}
