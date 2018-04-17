
<%@page import="com.model2.mvc.service.domain.Purchase"%>
<%@ page contentType="text/html;charset=EUC-KR"%>


<%
	Purchase purchaseVO = (Purchase) request.getAttribute("purchase");
	String pay = "";
	if(purchaseVO != null){
		if(purchaseVO.getPaymentOption().equals("1")){
			pay = "현금구매";
		}else{
			pay = "신용구매";
		}
	}
%>
<html>
	<head>
		<title>Insert title here</title>
	</head>

	<body>

		<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=<%=purchaseVO.getTranNo() %>" method="post">
		
		다음과 같이 구매가 되었습니다.
		
		<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>물품번호</td>
				<td><%=purchaseVO.getPurchaseProd().getProdNo() %></td>
				<td></td>
			</tr>
			<tr>
				<td>구매자아이디</td>
				<td><%=purchaseVO.getBuyer().getUserId() %></td>
				<td></td>
			</tr>
			<tr>
				<td>구매방법</td>
				<td>
				<%= pay %>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>구매자이름</td>
				<td><%=purchaseVO.getReceiverName() %></td>
				<td></td>
			</tr>
			<tr>
				<td>구매자연락처</td>
				<td><%=purchaseVO.getReceiverPhone() %></td>
				<td></td>
			</tr>
			<tr>
				<td>구매자주소</td>
				<td><%=purchaseVO.getDivyAddr() %></td>
				<td></td>
			</tr>
				<tr>
				<td>구매요청사항</td>
				<td><%=purchaseVO.getDivyRequest() %></td>
				<td></td>
			</tr>
			<tr>
				<td>배송희망일자</td>
				<td><%=purchaseVO.getDivyDate() %></td>
				<td></td>
			</tr>
		</table>
		</form>
	
	</body>
</html>