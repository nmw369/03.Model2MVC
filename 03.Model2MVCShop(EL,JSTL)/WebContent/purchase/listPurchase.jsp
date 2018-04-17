
<%@page import="com.model2.mvc.service.product.dao.ProductDAO"%>
<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="com.model2.mvc.service.domain.Purchase"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.HashMap"%>
<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<!DOCTYPE>

<%
	HashMap<String, Object> map = (HashMap<String, Object>) request.getAttribute("map");
	Search searchVO = (Search) request.getAttribute("Search");
	
	int total = 0;
	int pageNumber = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
	
	ArrayList<Purchase> list = null;
	if (map != null) {
		total = ((Integer) map.get("count")).intValue();
		list = (ArrayList<Purchase>) map.get("list");
	}
	int currentPage = searchVO.getCurrentPage();
	
	System.out.println(currentPage+"::::LISTPURCHASE 확인!@@!@!!@!@!@@!!@");
	
	int totalPage = 0;
	if (total > 0) {
		totalPage = total / searchVO.getPageSize();
		if (total % searchVO.getPageSize() > 0)
			totalPage += 1;
	}
	
	int start = 1 + ( pageNumber * ((searchVO.getCurrentPage() - 1) / pageNumber) );
	int end = start + pageNumber - 1;
	if(end > totalPage){
		end = totalPage;
	}
	
%>
<html>
	<head>
	<title>구매 목록조회</title>
		
		<link rel="stylesheet" href="/css/admin.css" type="text/css">
		
		<script type="text/javascript">
			function fncListPurchase() {
				document.detailForm.submit();
			}
		</script>
	</head>
	<style>

body{
    background: url("/images/clock2.jpg") no-repeat center center fixed; 
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
}
</style>
	
	
	
	
	<body bgcolor="#ffffff" text="#000000">
	
		<div style="width: 98%; margin-left: 10px;">
		
		<form name="detailForm" action="/listPurchase.do" method="post">
		
		<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
			<tr>
				<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
				<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="93%" class="ct_ttl01">구매 목록조회</td>
						</tr>
					</table>
				</td>
				<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
			</tr>
		</table>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
			<tr>
				<td colspan="11">전체 <%=total%> 건수, 현재 <%=currentPage%> 페이지</td>
			</tr>
			<tr>
				<td class="ct_list_b" width="100">No</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="100">상품이미지</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">구매상품정보</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">상품가격</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">구매정보확인/수정</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b">배송현황</td>
				
			</tr>
							<%
								int no = total - ( ( searchVO.getCurrentPage() - 1 ) * searchVO.getPageSize() ) ;
								for (int i = 0; i < list.size(); i++) {
									Purchase vo = (Purchase) list.get(i);
									int tranCode = Integer.parseInt(vo.getTranCode().trim());
									System.out.println("qqqqqqqqqqqqqqqqqqqqq::listPurJSP::"+vo.getPurchaseProd().getProdNo());
									Product vo2 = new ProductDAO().findProduct(vo.getPurchaseProd().getProdNo());
							%>
							<tr class="ct_list_pop" bgcolor="#ffffff">
								
								<td class="ct_list_test1" align="center">
									<a href="/getPurchase.do?tranNo=<%=vo.getTranNo()%>">
										<%=no--%>
									</a>
								</td>
								<td></td>
								<td align="center">
								<img src = "/images/uploadFiles/<%=vo.getPurchaseProd().getFileName()%>" width = "150"></td>
								<td></td>
								
								<td class="ct_list_test1" align="left">
									<a	href="/getProduct.do?prodNo=<%=vo2.getProdNo()%>&menu=search&cancel=yes">
										<%=vo2.getProdName()%>
									</a>
								</td>
								<td></td>
								
								<td class="ct_list_test1" align="left"><%=vo2.getPrice()%></td>
								<td></td>
								
								<td class="ct_list_test1" align="left">
								<a href="/getPurchase.do?tranNo=<%=vo.getTranNo()%>">
										구매정보</a>
								</td>
								<td></td>
								
								<% if(tranCode==2){ %>
									<td class="ct_list_test1" align="left">구매완료</td>
								<% }else if(tranCode==3){ %>
									<td class="ct_list_test1" align="left">배송중 ::
									<a href="/updateTranCodeByProd.do?tranNo=<%=vo.getTranNo()%>&tranCode=<%=vo.getTranCode()%>">물건도착</a></td>
								<% }else if(tranCode==4){ %>
									<td class="ct_list_test1" align="left">배송완료</td>
								<% }else{ %>
									<td class="ct_list_test1" align="left"></td>	
								<% } %>
								<td></td>
								
								
								
							</tr>
							<tr>
								<td colspan="11" bgcolor="D6D7D6" height="1"></td>
							</tr>
							<% } %>
		</table>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
			<tr>
				<td align="center">
				<%--  <%if(start > 1){ %>
										<a href="javascript:fncListPurchase('<%=start-1%>');">
										&nbsp;
										<
										Previous
										&nbsp;
										</a> 
									<% } %>
									<% for (int i = start ; i <= end; i++) { %> 
										<a href="javascript:fncListPurchase('<%=i%>');">
										&nbsp;
										<%= i %>
										&nbsp;
										</a> 
									<% } %>
									<%if(end < 9) { %>
										<a href="javascript:fncListPurchase('<%=end + 1%>');">
										&nbsp;
										Next
										>
										&nbsp;
										</a>
									<% } %> --%>
									<%
			for(int i=1;i<=totalPage;i++){
		%>
		
			
		<a href="/listPurchase.do?page=<%=i%>"><%=i %></a>
			
			
		<%
			}
		%>	
									
				 
				 
				 
				 
				</td>
			</tr>
		</table>
		
		<!--  페이지 Navigator 끝 -->
		</form>
		
		</div>
		
	</body>
</html>