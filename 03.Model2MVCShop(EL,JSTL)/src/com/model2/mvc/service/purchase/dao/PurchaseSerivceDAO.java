package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.user.dao.UserDao;






public class PurchaseSerivceDAO {
	
	
	
	public void insertPurchase(Purchase purchase) throws Exception{
				
		String sql = "";
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		//getPurchaseProd()안에 객체로 넣어서 객체안의 값 불러온것 아래도 같음
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode());
		stmt.setString(9, purchase.getDivyDate());
		stmt.setInt(10, purchase.getsEA());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
	}
	public Purchase findPurchase(int tranNo) throws Exception{
		
		String sql = "select *from transaction where Tran_no = ?";
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Purchase vo = new Purchase();
		while(rs.next()) {
			
			vo.setTranNo(rs.getInt(1));
			vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt(2)));
			vo.setBuyer(new UserDao().findUser(rs.getString(3)));
			vo.setPaymentOption(rs.getString(4));
			vo.setReceiverName(rs.getString(5));
			vo.setReceiverPhone(rs.getString(6));
			vo.setDivyAddr(rs.getString(7));
			vo.setDivyRequest(rs.getString(8));
			vo.setTranCode(rs.getString(9));
			vo.setOrderDate(rs.getDate(10));
			vo.setDivyDate(rs.getString(11));
			vo.setsEA(rs.getInt(12));
			System.out.println(rs.getString(11)+"====================================!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");	
			
//		vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
//		vo.setBuyer(new UserDAO().findUser(rs.getString("buyer_id")));
//		vo.setTranNo(rs.getInt("tran_no"));
//		vo.setPaymentOption(rs.getString("payment_option"));
//		vo.setReceiverName(rs.getString("receiver_name"));
//		vo.setReceiverPhone(rs.getString("receiver_phone"));
//		vo.setDivyAddr(rs.getString("demailaddr"));
//		vo.setDivyRequest(rs.getString("dlvy_request"));
//		vo.setOrderDate(rs.getDate("order_data"));
//		vo.setDivyDate(rs.getString("dlvy_date"));
//		vo.setTranCode(rs.getString("tran_status_code"));
		}
		
		System.out.println(vo+":::::::::::::::::::::DAO find 값확인");
		rs.close();
		stmt.close();
		con.close();
					
		return vo;
	} 
	public HashMap<String, Object> getPurchaseList(Search search,String userId) throws Exception {
		
		String sql = "select buyer_id ,receiver_name,receiver_phone,tran_status_code,tran_no,prod_no,sea from transaction where BUyer_id='"+userId+"'";
		Connection con = DBUtil.getConnection();
		
		System.out.println(sql+"::::::=====0");
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount :: "+totalCount);
		
		System.out.println(sql+"::::::=====1");
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		
		System.out.println(sql+"::::::=====2");
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		//받은 총row수 저장	
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("totalCount", new Integer(totalCount));
		
		
		/////////////
				
		List<Purchase> list = new ArrayList<Purchase>();
		
		//List<Product> proVo = new ArrayList<Product>();
		
		while(rs.next()) {
			
				Purchase vo = new Purchase();
				vo.setBuyer(new UserDao().findUser(rs.getString(1)));
				vo.setReceiverName(rs.getString(2));
				vo.setReceiverPhone(rs.getString(3));
				vo.setTranCode(rs.getString(4));
				vo.setTranNo(rs.getInt(5));
				vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt(6)));
				vo.setsEA(rs.getInt(7));
				
				list.add(vo);
				
				
			}
		
		/*Product vo2 = new Product();
		String sql2 = "Select*from product where prod_no=?";
		PreparedStatement stmt2 = con.prepareStatement(sql2);
		stmt2.setInt(1,rs.getInt(6));
		ResultSet rs2 = stmt2.executeQuery();
		while(rs.next()) {
			vo2.setProdNo(rs2.getInt(1));
			vo2.setProdName(rs2.getString(2));
			vo2.setProdDetail(rs2.getString(3));
			vo2.setManuDate(rs2.getString(4));
			vo2.setPrice(rs2.getInt(5));
			vo2.setFileName(rs2.getString(6));
			vo2.setRegDate(rs2.getDate(7));
			
			proVo.add(vo2);
		}
		map.put("vo2",vo2);
		System.out.println(vo2);*/
		
		System.out.println("list.size() : "+ list.size());
		//list 값 전송
		map.put("list", list);
		
		
			
		System.out.println("map().size() : "+ map.size());
		System.out.println("list들어간값 확인 : "+list.get(0).getReceiverName());
		System.out.println("list들어간값 확인!!!!!! : "+list.get(0).getTranNo());
		System.out.println("list들어간 ProdNo 확인!!!!!! : "+list.get(0).getPurchaseProd().getProdNo());
			
		
		rs.close();
		stmt.close();
		con.close();
		
		return map;
				
	}
	public HashMap<String, Object> getSaleList(Search search){
		
		
		
		return null;
	}
	
	public void updatePurchase(Purchase purchase)throws Exception {
		
		String sql ="update transaction set payment_option=? , receiver_name=?, receiver_phone=? ,demailaddr=?,dlvy_request=?,dlvy_date=?,sea=? " + 
				" where tran_no =?";
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		stmt.setInt(8, purchase.getsEA());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
	}
	public void updateTranCode(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update transaction set tran_status_code=? where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}
	
	public void deletePurchase(Purchase purchase) throws Exception{
		//동일한 물품번호의 상품인데 구매번호가 다르므로 각각제거
		Connection con = DBUtil.getConnection();
		
		String sql = "update transaction set tran_status_code='5' where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getTranNo());
		stmt.executeUpdate();
		
		new ProductDAO().cancelEA(purchase.getsEA(), purchase.getPurchaseProd());
		
		
		stmt.close();
		con.close();
		
	}
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
			private int getTotalCount(String sql) throws Exception {
				
				System.out.println("=====getTotalCount method start=====");
				
				sql = "SELECT COUNT(*) FROM ( " +sql+ ") countTable";
				
								
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
			//구매취소 리스트를 위한 로직
			public Map<String , Object> cancelList() throws Exception{
				
				
				
				Connection con = DBUtil.getConnection();
				String sql = "select * from transaction where tran_status_code >= '5' ";
				
								
				PreparedStatement stmt = con.prepareStatement(sql);
				
				ResultSet rs = stmt.executeQuery();
				System.out.println(sql+"::::::=====0");
				sql="select * from transaction where tran_status_code>='5'";
				//==> TotalCount GEt
				int totalCount = this.getTotalCount(sql);
				System.out.println("PurchaseDAO :: totalCount :: "+totalCount);
				System.out.println(totalCount+"::cancel카운트");
				//받은 총row수 저장	
				HashMap<String, Object> map = new HashMap<String,Object>();
				map.put("totalCount", new Integer(totalCount));
				
				
				/////////////
						
				List<Purchase> list = new ArrayList<Purchase>();
				
				//List<Product> proVo = new ArrayList<Product>();
				
				while(rs.next()) {
					
						Purchase vo = new Purchase();
						/*vo.setBuyer(new UserDao().findUser(rs.getString(1)));
						vo.setReceiverName(rs.getString(2));
						vo.setReceiverPhone(rs.getString(3));
						vo.setTranCode(rs.getString(4));
						vo.setTranNo(rs.getInt(5));
						vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt(6)));
						vo.setsEA(rs.getInt(7));*/
						
						vo.setTranNo(rs.getInt(1));
						vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt(2)));
						vo.setBuyer(new UserDao().findUser(rs.getString(3)));
						vo.setPaymentOption(rs.getInt(4)+"");
						vo.setReceiverName(rs.getString(5));
						vo.setReceiverPhone(rs.getString(6));
						vo.setTranCode(rs.getInt(9)+"");
						vo.setsEA(rs.getInt(12));
						
						list.add(vo);
						
						
					}
				
				
				System.out.println("list.size() : "+ list.size());
				//list 값 전송
				map.put("list", list);
				
				System.out.println("canclelist들어간 ProdNo 확인!!!!!! : "+list.get(0).getPurchaseProd().getProdNo());
					
				
				rs.close();
				stmt.close();
				con.close();
				
				return map;
			}
	
}
