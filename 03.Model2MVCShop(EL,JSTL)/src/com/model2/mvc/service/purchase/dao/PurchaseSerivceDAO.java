package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.user.dao.UserDao;






public class PurchaseSerivceDAO {
	
	
	
	public void insertPurchase(Purchase purchase) throws Exception{
				
		String sql = "insert into transaction values(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,to_date(?,'YYYY-MM-DD'))";
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
		
		String sql = "select tran_no,buyer_id ,receiver_name,receiver_phone,tran_status_code,prod_no from transaction where BUyer_id=?";
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,	ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, userId);
		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		System.out.println("PurchaseServiceDAO 로우수:"+total);
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(search.getCurrentPage()* search.getPageSize() - search.getPageSize()+1);
		//~.absolute(): 주어진 열로 커서를 이동시킨다.
		
		System.out.println("DAOsearch.getPage():" + search.getCurrentPage());
		System.out.println("DAOsearch.getPageUnit():" + search.getPageSize());
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		if(total>0) {
			for (int i = 0; i < search.getPageSize(); i++) {
				Purchase vo = new Purchase();
				
				vo.setBuyer(new UserDao().findUser(rs.getString("buyer_id")));
				vo.setReceiverName(rs.getString("receiver_name"));
				vo.setReceiverPhone(rs.getString("receiver_phone"));
				vo.setTranCode(rs.getString("tran_status_code"));
				vo.setTranNo(rs.getInt("tran_no"));
				vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
				
				list.add(vo);
				if(!rs.next()) {
					break;
				}
			}
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() : "+ map.size());
			System.out.println("list들어간값 확인 : "+list.get(0).getReceiverName());
			System.out.println("list들어간값 확인!!!!!! : "+list.get(0).getTranNo());
			System.out.println("list들어간 ProdNo 확인!!!!!! : "+list.get(0).getPurchaseProd().getProdNo());
			
		}
		
		rs.close();
		stmt.close();
		con.close();
		
		return map;
				
	}
	public HashMap<String, Object> getSaleList(Search search){
		
		
		
		return null;
	}
	
	public void updatePurchase(Purchase purchase)throws Exception {
		
		String sql ="update transaction set payment_option=? , receiver_name=?, receiver_phone=? ,demailaddr=?,dlvy_request=?,dlvy_date=? " + 
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
	
	public void deletePurchase(int tranNo) throws Exception{
		//동일한 물품번호의 상품인데 구매번호가 다르므로 각각제거
		Connection con = DBUtil.getConnection();
		
		String sql = "DELETE transaction WHERE TRAN_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		stmt.executeQuery();
		
		stmt.close();
		con.close();
		
	}
	
}
