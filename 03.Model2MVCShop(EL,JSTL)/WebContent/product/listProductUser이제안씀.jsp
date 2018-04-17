<%@ page contentType="text/html; charset=euc-kr" %>

<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	
	List<Product> list=(List<Product>)request.getAttribute("list");
	Page resultPage = (Page)request.getAttribute("resultPage");
	
	User user = (User) session.getAttribute("user");
	
	String menu = request.getParameter("menu");
	Search search=(Search)request.getAttribute("search");
	
	//==> null 을 ""(nullString)으로 변경
	System.out.println("listProduct JSP 시작 지점 search(VO) : "+search);//
	
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	String role = "";
	
	if(user != null){
		role = user.getRole();
	}	
%>


<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css" >
<style>

body{
    background: url("/images/mountain.jpg") no-repeat center center fixed; 
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
} 
</style>

<script type="text/javascript">
	function fncGetProductList(currentPage){
		
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
	
/* 	function fncGetProductListPage(currentPage,searchCondition,searchKeyword){
		
		document.getElementById("currentPage").value = currentPage;
		document.getElementById("searchCondition").value =searchCondition; 
		document.getElementById("searchKeyword").value =searchKeyword;
		document.detailForm.submit();
	} */
</script>
</head>


<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do" method="post">
<input name="menu" value=<%=menu%> type="hidden">
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<%if(menu.equals("manage")){ %>
							<td width="93%" class="ct_ttl01">상품 관리</td>
					<%}else{ %>
							<td width="93%" class="ct_ttl01">상품 목록조회</td>
					<%} %>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>



<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	<%
		if(search.getSearchCondition() != null) {
	%>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
		<%
				if(searchCondition.equals("1")) {
		%>
				<option value="1" selected>상품명</option>
				<option value="2">상품가격</option>
		<%
				}else{
					
		%>
				<option value="1">상품명</option>
				<option value="2"selected>상품가격</option>
		<%} %>
			</select>
			<input 	type="text" name="searchKeyword"  value="<%=search.getSearchKeyword()%>" 
							class="ct_input_g" style="width:200px; height:19px" >
		</td>
	<%
		}else{
	%>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="1">상품명</option>
				<option value="2">상품가격</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" onkeypress="if(event.keyCode == 13){fncGetProductList('1')};">
		</td>
	<%
		}
	%>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px; ">
						<a href="javascript:fncGetProductList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체  <%= resultPage.getTotalCount()%> 건수, 현재 <%=resultPage.getCurrentPage() %> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">상품이미지</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">등록일</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">현재상태</td>
		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<% 	
	
		int no=list.size();
		for(int i=0; i<list.size(); i++) {
			Product vo = (Product)list.get(i);
			int tranCode = Integer.parseInt(vo.getProTranCode().trim());
	%>
	<tr class="ct_list_pop" bgcolor="#ffffff">
		<td align="center"><%=no--%></td>
		<td></td>
		<td align="center">
		<a href="/getProduct.do?prodNo=<%=vo.getProdNo() %>&menu=<%=menu %>">
		<img src = "/images/uploadFiles/<%=vo.getFileName()%>" width = "150"></a></td>
		<td></td>
		<td align="left">
				<a href="/getProduct.do?prodNo=<%=vo.getProdNo() %>&menu=<%=menu %>"><%= vo.getProdName() %></a>
		</td>
		<td></td>
		<td align="center"><%= vo.getPrice() %></td>
		<td></td>
		<td align="center"><%= vo.getRegDate() %></td>
	
		<td align="center">
					<%if(tranCode==1){ %>
					<td align="center">판매중</td>					
					<% }else if (tranCode==2){ %>
						<% if(role.equals("user")){ %>
								<td align="center">구매완료</td>	
							<% } %>
						<% }else if (tranCode==3){ %>
								<td align="center">재고없음</td>	
						<% }else if (tranCode==4){ %>
								<td align="center">재고없음</td>	
						<% } %>
					</tr>
					<tr>
						<td colspan="11" bgcolor="D6D7D6" height="1"></td>
					</tr>
					
				<%} %>
				
	</tr>
	
	
	
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>

</table>


<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value="<%=resultPage.getCurrentPage()%>"/>
			
			<%System.out.println("listProduct 내부 resultPage 확인출력 : "+resultPage); %>
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					이전
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">이후 </a>
			<% } %>	
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->
</form>
</div>

</body>
</html>