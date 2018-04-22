<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>

<html>
<head>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
  
   
  
  
<c:if test="${user.role!= 'admin' }">
  <script type="text/javascript">
    $(document).ready(function(){
      $('.slider').bxSlider();
    });
  </script>
  </c:if>

  <c:if test="${user.role=='admin'}">
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script>
	// 차트를 사용하기 위한 준비입니다.
		google.charts.load('current', {packages:['corechart']});	
	</script>

	<div id="chart_div"></div><!-- 여기에 차트가 생성됩니다. -->

	<script type="text/javascript">
		
		
	// 로딩 완료시 함수 실행하여 차트 생성
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
	
		var test2 = document.getElementById('a').value;
		
		
		var test ="abcd";
		
	// 차트 데이터 설정
		var data = google.visualization.arrayToDataTable([
			['항목', '다리수'], // 항목 정의
			[test2, 4], // 항목, 값 (값은 숫자로 입력하면 그래프로 생성됨)
			['메뚜기', 6],
			['문어', 8],
			['오징어', 10],
			['여기 운영자', 2]
			]);

	// 그래프 옵션
				var options = {
				title : '다리 갯수', // 제목
				width : 600, // 가로 px
				height : 400, // 세로 px
				bar : {
					groupWidth : '80%' // 그래프 너비 설정 %
				},
				legend : {
					position : 'none' // 항목 표시 여부 (현재 설정은 안함)
				}
			};

		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
		chart.draw(data, options);
		}

	</script>
  
  </c:if>
  </head>
<body>
<c:if test="${user.role=='admin'}">
	
	<input type="text"  id="a" value="${a}">
	<button onclick="javascript:drawChart()" />
</c:if>
<c:if test="${user.role!= 'admin' }">
<h3 align="center">★오늘의 인기상품★</h3>
  <div class="slider">
  	<c:forEach var="pro" items="${list}">
    <div>
	
	<c:if test="${!empty pro.fileName }">
    <img src="/images/uploadFiles/${pro.fileName}" width="40%"  align="left">
    </c:if>
    <c:if test="${empty pro.fileName }">
    <img src="/images/uploadFiles/notimage.gif" width="40%"  align="left">
    </c:if>
  
    <table width="40%" height="60%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		
		<td width="104" class="ct_write">
			상품번호 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105">${pro.prodNo}</td>
					<td>	</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">
			상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle">
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${pro.prodName}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">상품상세정보</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${pro.prodDetail}</td>
	</tr>

	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">제조일자</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26">${pro.manuDate}</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">가격</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${pro.price}</td>
	</tr>
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">등록일자</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${pro.regDate}</td>
	</tr>

	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>
    </div>
    </c:forEach>
    
  </div>
 </c:if> 
  </body>
</html>