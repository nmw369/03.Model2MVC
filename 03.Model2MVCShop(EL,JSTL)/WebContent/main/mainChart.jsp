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
	// ��Ʈ�� ����ϱ� ���� �غ��Դϴ�.
		google.charts.load('current', {packages:['corechart']});	
	</script>
	<c:if test="${start=='start'}">
	<div id="chart_div"></div><!-- ���⿡ ��Ʈ�� �����˴ϴ�. -->
	</c:if>
	<script type="text/javascript">
	
	
		
	// �ε� �Ϸ�� �Լ� �����Ͽ� ��Ʈ ����
	
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
		
		var start = document.getElementById("start").value;
		
	// ��Ʈ ������ ����
		var data = google.visualization.arrayToDataTable([
			['��ǰ��', '��ȸ��'], // �׸� ����
			<c:forEach var="pro" items="${list}" varStatus="status">
			
				<c:if test="${!status.last}">
				['${pro.prodName}', parseInt('${pro.lookup}')], // �׸�, �� (���� ���ڷ� �Է��ϸ� �׷����� ������)
				</c:if>				
				<c:if test="${status.last}">
				['${pro.prodName}', parseInt('${pro.lookup}')]
				</c:if>
			</c:forEach>
			]);

	// �׷��� �ɼ�
				var options = {
				title : '�ǽð� ��ǰ�� ��ȸ��', // ����
				width : 1100, // ���� px
				height : 400, // ���� px
				bar : {
					groupWidth : '80%' // �׷��� �ʺ� ���� %
				},
				legend : {
					position : 'none' // �׸� ǥ�� ���� (���� ������ ����)
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
		<td class="ct_list_b" width="100">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">��ȸ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">�Ǹŷ�</td>
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