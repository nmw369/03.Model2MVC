
<%@page import="com.model2.mvc.service.domain.Purchase"%>
<%@ page contentType="text/html;charset=EUC-KR"%>


<%
	Purchase purchaseVO = (Purchase) request.getAttribute("purchase");
	String pay = "";
	if(purchaseVO != null){
		if(purchaseVO.getPaymentOption().equals("1")){
			pay = "���ݱ���";
		}else{
			pay = "�ſ뱸��";
		}
	}
%>
<html>
	<head>
		<title>Insert title here</title>
	</head>

	<body>

		<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=<%=purchaseVO.getTranNo() %>" method="post">
		
		������ ���� ���Ű� �Ǿ����ϴ�.
		
		<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>��ǰ��ȣ</td>
				<td><%=purchaseVO.getPurchaseProd().getProdNo() %></td>
				<td></td>
			</tr>
			<tr>
				<td>�����ھ��̵�</td>
				<td><%=purchaseVO.getBuyer().getUserId() %></td>
				<td></td>
			</tr>
			<tr>
				<td>���Ź��</td>
				<td>
				<%= pay %>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>�������̸�</td>
				<td><%=purchaseVO.getReceiverName() %></td>
				<td></td>
			</tr>
			<tr>
				<td>�����ڿ���ó</td>
				<td><%=purchaseVO.getReceiverPhone() %></td>
				<td></td>
			</tr>
			<tr>
				<td>�������ּ�</td>
				<td><%=purchaseVO.getDivyAddr() %></td>
				<td></td>
			</tr>
				<tr>
				<td>���ſ�û����</td>
				<td><%=purchaseVO.getDivyRequest() %></td>
				<td></td>
			</tr>
			<tr>
				<td>����������</td>
				<td><%=purchaseVO.getDivyDate() %></td>
				<td></td>
			</tr>
		</table>
		</form>
	
	</body>
</html>