<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<!DOCTYPE>
<html>
<head>
<title>Insert title here</title>
<script language="javascript">
</script>
</head>
<body>
<tr bgcolor = "#f0f0f0">
<!-- <img src = "/images/sky.gif" width = "1000">이미지 -->
 </td>
<Style>



#rightSide{

position: absolute;

top: 547px;

left: 20%;

margin: 0 0 0 510px;

	}

#rightSide #right_zzim {

position: fixed;

top: 126px;

left: 20%;

margin-left: 510px;

border:1px solid #B0B5BD;

width:114px;

height:543px;

}



#rightSide #right_zzim  div {text-align:center;}

#rightSide #right_zzim  div.recTit{line-height:1.5em;padding:5px;color:white;background-color:#505A69;}

#right_zzim #recentCnt {color:yellow;}

#rightSide #right_zzim ul {min-height:495px;}

#rightSide #right_zzim  li{text-align:center;padding:5px;position:relative;} 

#rightSide #right_zzim ul li img {border:1px solid #ccc}

#right_zzim .detail {

display: none;

position: absolute;

top: 3px;

right: 20px;

xheight: 40px;

xpadding: 15px 11px 0;

xbackground: #404a59;

color: #fff;

xtext-align: left;

white-space: nowrap;



}



#right_zzim li:hover .detail {display:block}

#right_zzim li .btn_delete {

position: absolute;

top: 3px;

right: -1px;

width: 11px;

height: 11px;

background: url(/img/sp.png) no-repeat -193px -111px;

text-indent: -9000px;

}

#right_zzim  #currentPage {color:#505A69;font-weight:bold}

#right_zzim  #totalPageCount {color:#CBC8D2;font-weight:bold}

.noData {color:#ccc;text-align:center;margin-top:223px;}



}

#paging {display:;position:relative;line-height:1em;}

#paging .btn_prev {

position: absolute;

top: 526px;

left: 4px;

width: 13px;

height: 11px;

background: url(/images/ico_arrow.png)  no-repeat ;

text-indent: -9000px;

border:1px solid #CCC;

display:inline-block;

}



#paging .btn_next {

position: absolute;

top: 526px;

right: 4px;

width: 13px;

height: 11px;

background: url(/images/ico_arrow.png) -11px 0px;

text-indent: -9000px;

border:1px solid #CCC;

display:inline-block;

}



</style>





본문 html 코딩 

<div id="rightSide">

	<div id="right_zzim">

		<div  class="recTit">최근본상품 <span id=recentCnt></span></div>

			<ul>
			<br>
					<br>
					<%
						request.setCharacterEncoding("euc-kr");
						response.setCharacterEncoding("euc-kr");
						
						String history = null;
						
						Cookie[] cookies = request.getCookies();
						
						if (cookies!=null && cookies.length > 0) {
							for (int i = 0; i < cookies.length; i++) {
								Cookie cookie = cookies[i];
								if (cookie.getName().equals("history")) {
									history = cookie.getValue();
								}
							}
							if (history != null) {
								String[] h = history.split(",");
								for (int i = 0; i < h.length; i++) {
									if (!h[i].equals("null")) {
					%>
						<a href="/getProduct.do?prodNo=<%=h[i]%>&menu=search"	target="rightFrame"><%=h[i]%></a>
					<br>
					<%
									}
								}
							}
						}
					%>
			</ul>    <!-- 본 상품이 뿌려질 부분  -->

		<div id="paging"><a class="btn_prev" style="cursor:pointer" >이전</a>
		<span  id="currentPage"></span><span id="totalPageCount">
		</span><a class="btn_next" style="cursor:pointer" >다음</a></div>

	</div>

</div> 









<script>

	//

	// recent item    

	 var Cpage;   // 현재 페이지 

	var pagingSize = 4;   // 원하는 페이지 사이즈로 조정하세용 

	function chkRecent(a){

	var itemID = getCookie("history");

	$("#right_zzim ul").html('');    // 일단 Ul 내용 지우기... 

	if(itemID){

		var totcount = itemID.split(',').length-1;   //

		var totpage = Math.ceil(totcount / pagingSize) *1;

		

		Cpage = (totpage >= a )? a:1;

		Cpage = (Cpage <1)? totpage:Cpage;

		

		var start = (Cpage-1) * pagingSize;    

	

		for (i = start ; i <= start+(pagingSize-1) ;i++){

		var  thisItem = itemID.split('&')[i];

			if(thisItem){

				var itemId = thisItem.split(':')[0];

				var itemImg = thisItem.split(':')[1];

			$("#right_zzim ul").append('<li><a href="/_detail.php?id='+itemId+'" target="_top"><img src="http://www.xxx.com/images/s'+itemImg+'"  width="75" border=1></a><div class="detail"><a href="javascript:removeRecentItem(\''+thisItem+'\')" class="btn_delete">삭제</a></div></li>')

			}

		}

		

		$("#paging").show();

	}else{

		$("#right_zzim ul").append('<p class="noData">최근 본 상품이<br> 없습니다.</p>');

		$("#paging").hide();$("#recentCnt").text('');

	}

	

	

	updateRecentPage(totcount, Cpage);

	

}

chkRecent(1);



	function removeRecentItem(itemname){

		var itemID = getCookie("itemID");

		itemID = itemID.replace(itemname+"&","");			

		setCookie("itemID",itemID,1);

		chkRecent(Cpage);

	}





	function updateRecentPage(totcount,Cpage){  //  

	

		$("#recentCnt").text(totcount);  //

		

		$("#totalPageCount").text("/"+Math.ceil((totcount / pagingSize) *1)); 

		if(Math.ceil((totcount / pagingSize) *1) < Cpage){

		$("#currentPage").text(Math.ceil((totcount / pagingSize) *1));

		}else{

		$("#currentPage").text(Cpage);  //

		}

	}



	$(".btn_next").on('click',function(){

	chkRecent(Cpage + 1);

	 

	});

	

	$(".btn_prev").on('click',function(){

	chkRecent(Cpage - 1);

	});

	



쿠키를 저장하는 페이지는...    아래 코드만 삽입 



<script type="text/javascript">

function checkCookie() {



    var itemID = getCookie("history");

	var thisItem='<?=$bookID?>:<?=$itemImage ?>';   // 제품 아이디와 이미지 이름을 저장  2차원 배열처럼 쓸려고 짱구를 굴림...  json 형태로 저장도 가능할텐데.... 그건 취향대로 

	

	if (thisItem){

		if (itemID != "" && itemID != null) {

			if (itemID.indexOf(thisItem) ==-1 ){ //값이 없으면 

					setCookie("itemID",thisItem+","+itemID,1);

			 }

		} else {

			if (itemID == "" || itemID == null) {

				setCookie("itemID",thisItem+",",1);

			}

		}

	}

}

checkCookie();



</script>



</body>
</html>

