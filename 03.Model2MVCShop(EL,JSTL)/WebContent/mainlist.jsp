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
	// ��Ʈ�� ����ϱ� ���� �غ��Դϴ�.
		google.charts.load('current', {packages:['corechart']});	
	</script>

	<div id="chart_div"></div><!-- ���⿡ ��Ʈ�� �����˴ϴ�. -->

	<script type="text/javascript">
		
		
	// �ε� �Ϸ�� �Լ� �����Ͽ� ��Ʈ ����
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
	
		var test2 = document.getElementById('a').value;
		
		
		var test ="abcd";
		
	// ��Ʈ ������ ����
		var data = google.visualization.arrayToDataTable([
			['�׸�', '�ٸ���'], // �׸� ����
			[test2, 4], // �׸�, �� (���� ���ڷ� �Է��ϸ� �׷����� ������)
			['�޶ѱ�', 6],
			['����', 8],
			['��¡��', 10],
			['���� ���', 2]
			]);

	// �׷��� �ɼ�
				var options = {
				title : '�ٸ� ����', // ����
				width : 600, // ���� px
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
  
  </c:if>
  </head>
<body>
<c:if test="${user.role=='admin'}">
	
	<input type="text"  id="a" value="${a}">
	<button onclick="javascript:drawChart()" />
</c:if>
<c:if test="${user.role!= 'admin' }">
<h3 align="center">�ڿ����� �α��ǰ��</h3>
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
			��ǰ��ȣ <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
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
			��ǰ�� <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle">
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
		<td width="104" class="ct_write">��ǰ������</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${pro.prodDetail}</td>
	</tr>

	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">��������</td>
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
		<td width="104" class="ct_write">����</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${pro.price}</td>
	</tr>
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	<tr>
		<td width="104" class="ct_write">�������</td>
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