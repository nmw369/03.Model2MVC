<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Insert title here</title>
	<script src="//www.google.com/jsapi"></script>
<script>
var data = [
	['��ǰ��', '��ȸ��'],

  	['����', 8.94],
  	['��', 10.49],
  	['��', 19.30],
  	['���', 21.45],
  	['���', 21.45],
  	['���', 21.45],
 	 ['���', 21.45],
  	['���', 21.45],
  	['���', 21.45],
  	['���', 21.45],
  	['���', 21.45],
  	['���', 21.45],
  	['���', 21.45],
  	['���', 21.45],
];
var options = {
  title: 'ǰ�� ��ȸ�� ',
  width: 700, height: 700
};
google.load('visualization', '1.0', {'packages':['corechart']});
google.setOnLoadCallback(function() {
  var chart = new google.visualization.ColumnChart(document.querySelector('#chart_div'));
  chart.draw(google.visualization.arrayToDataTable(data), options);
});
</script>
<div id="chart_div"></div>
</head>
<body>
	<div id="chart_div" style="width:900px; height: 500px;"></div>
</body>
</html>