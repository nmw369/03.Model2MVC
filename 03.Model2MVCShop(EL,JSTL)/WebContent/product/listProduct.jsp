<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<html>
<head>
<title>상품 목록조회</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css" >
<!-- <style>


 body{
    background: url("/images/sunset.jpg") no-repeat center center fixed; 
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
    
     
} 
</style> -->

<script type="text/javascript">
	function fncGetList(currentPage){
		
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
	function fncGetList2(currentPage,searchKeyword,searchCondition){
			
		document.getElementById("currentPage").value = currentPage;
		document.getElementById("searchKeyword").value = searchKeyword;
		document.getElementById("searchCondition").value = searchCondition;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do" method="post">
<input name="menu" value="${param.menu }" type="hidden">
<table width="100%" height="37" border="0"  cellpadding="0"	cellspacing="0" >
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<%-- <%if(menu.equals("manage")){ %>
							<td width="93%" class="ct_ttl01">상품 관리</td>
					<%}else{ %>
							<td width="93%" class="ct_ttl01">상품 목록조회</td>
					<%} %> --%>
					
					<td width="93%" class="ct_ttl01">
					${param.menu== "manage"? "상품관리":"상품 목록조회"}
					</td>
									
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
	
	
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${search.searchCondition =="0"? "selected" : ""}>상품번호</option>
				<option value="1" ${search.searchCondition =="1"? "selected" : ""}>상품명</option>
				<option value="2" ${search.searchCondition =="2"? "selected" : ""}>상품가격</option>
			</select>
			<input type="text" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px"  
			onkeypress="if(event.keyCode == 13){ javascript:fncGetList('1')};">
		</td>
		
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">검색</a>
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
		<td colspan="11" >
		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage} 페이지
		</td>
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
	
	<c:set var="i" value="0"></c:set>
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${i+1}"></c:set>
		
	<tr class="ct_list_pop" bgcolor="#ffffff">
		<td align="center">${i}</td>
		<td></td>
		<td align="center">
		<img src = "/images/uploadFiles/${product.fileName}" width = "150"></td>
		<td></td>
		<td align="center">
		<a href="/getProduct.do?prodNo=${product.prodNo}&menu=${param.menu}&tranCode=${product.proTranCode}">${product.prodName}</a>
		</td>
		<td></td>
		<td align="center">${product.price}</td>
		<td></td>
		<td align="center">${product.regDate}</td>
	
		<td align="center">
					
				<c:if test="${product.proTranCode == '1' }">
						<td align="center">판매중</td>
					</c:if>					
					<c:if test="${product.proTranCode == '2' }">
						<c:if test="${user.role=='admin' }">
							<td align="center">판매완료
								<c:if test="${param.menu=='manage'}">
										<a href="/updateTranCodeByProd.do?prodNo=${product.prodNo}&tranCode=${product.proTranCode}">배송하기</a>
									</c:if>
								</td>
								</c:if>
								<c:if test="${user.role=='user' }">
								<td align="center">재고없음</td>	
								</c:if>		
							
							</c:if>
						<c:if test="${product.proTranCode == '3' }">
							<c:if test="${user.role=='admin' }">
								<td align="center">배송중</td>
								</c:if>		
							<c:if test="${user.role=='user' }">
								<td align="center">재고없음</td>	
								</c:if>
							</c:if>
						<c:if test="${product.proTranCode == '4' }">
							<c:if test="${user.role=='admin' }">
								<td align="center">배송완료</td>
								</c:if>		
							<c:if test="${user.role=='user'}">
								<td align="center">재고없음</td>	
							</c:if>
						</c:if>
					</tr>
					<tr>
						<td colspan="11" bgcolor="D6D7D6" height="1"></td>
					</tr>
				
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>


<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			
			
			<jsp:include page="../common/pageNavigator.jsp"/>
			
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->
</form>
</div>

</body>
</html>