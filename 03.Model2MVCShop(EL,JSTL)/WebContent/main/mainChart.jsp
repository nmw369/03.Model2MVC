<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>

<html>
<head>
	<link rel="stylesheet" href="/css/admin.css" type="text/css" >
 

	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script>
	// 차트를 사용하기 위한 준비입니다.
		google.charts.load('current', {packages:['corechart']});	
	</script>
	<c:if test="${start=='start'}">
	<div id="chart_div"></div><!-- 여기에 차트가 생성됩니다. -->
	</c:if>
	<script type="text/javascript">
	
	
		
	// 로딩 완료시 함수 실행하여 차트 생성
	
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
		
		var start = document.getElementById("start").value;
		
	// 차트 데이터 설정
		var data = google.visualization.arrayToDataTable([
			['상품명', '조회수'], // 항목 정의
			<c:forEach var="pro" items="${list}" varStatus="status">
			
				<c:if test="${!status.last}">
				['${pro.prodName}', parseInt('${pro.lookup}')], // 항목, 값 (값은 숫자로 입력하면 그래프로 생성됨)
				</c:if>				
				<c:if test="${status.last}">
				['${pro.prodName}', parseInt('${pro.lookup}')]
				</c:if>
			</c:forEach>
			]);

	// 그래프 옵션
				var options = {
				title : '실시간 상품별 조회수', // 제목
				width : 1100, // 가로 px
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
  
 
  </head>
<body>
<p>
<input type="button" value="chart" onclick="javascript:drawChart()"> 
</p>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td class="ct_list_b" width="100">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">조회수</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">판매량</td>
		<td class="ct_line02"></td>
	</tr>
<c:forEach var="pro" items="${list}">
	<tr>
	<td align="center">${pro.prodName}</td>
	<td></td>
	<td align="center">${pro.lookup }</td>
	<td></td>
	<td align="center"></td>
	<td></td>
	</tr>
</c:forEach>
</table>
    
   </body>
</html>