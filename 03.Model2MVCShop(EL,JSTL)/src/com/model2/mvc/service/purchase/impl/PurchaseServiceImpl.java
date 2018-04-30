package com.model2.mvc.service.purchase.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.model2.mvc.common.Search;

import com.model2.mvc.common.util.DBUtil;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;

import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseSerivceDAO;
import com.model2.mvc.service.user.dao.UserDao;


public class PurchaseServiceImpl implements PurchaseService {

	PurchaseSerivceDAO purchaseServiceDAO = new PurchaseSerivceDAO();
	
	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		purchaseServiceDAO.insertPurchase(purchase);
		
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		return purchaseServiceDAO.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {
		// TODO Auto-generated method stub
		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, ProdNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		
		
		while (rs.next()) {
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt(1));
			purchase.setPurchaseProd(new ProductDAO().findProduct(rs.getInt(2)));
			purchase.setBuyer(new UserDao().findUser(rs.getString(3)));
			purchase.setPaymentOption(rs.getString(4));
			purchase.setReceiverName(rs.getString(5));
			purchase.setReceiverPhone(rs.getString(6));
			purchase.setDivyAddr(rs.getString(7));
			purchase.setDivyRequest(rs.getString(8));
			purchase.setTranCode(rs.getString(9));
			purchase.setOrderDate(rs.getDate(10));
			purchase.setDivyDate(rs.getString(11));
		}
		
		rs.close();
		stmt.close();
		con.close();

		return purchase;
	}

	@Override
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		// TODO Auto-generated method stub
		return purchaseServiceDAO.getPurchaseList(search, buyerId);
	}

	@Override
	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return purchaseServiceDAO.getSaleList(search);
	}

	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		this.purchaseServiceDAO.updatePurchase(purchase);
		
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		this.purchaseServiceDAO.updateTranCode(purchase);
	}

	@Override
	public void deletePurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		this.purchaseServiceDAO.updateCancel(purchase);
		
	}
	
	
	
	
	
}
