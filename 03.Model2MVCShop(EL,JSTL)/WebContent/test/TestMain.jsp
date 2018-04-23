<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page pageEncoding="EUC-KR"%>
<!DOCTYPE>

<html>
<head>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>

  <script>
    $(document).ready(function(){
      $('.slider').bxSlider();
    });
  </script>
  

	 <!-- <script type="text/javascript">
    $(document).ready(function(){
        //메인/서브 상단 좌측 상품배너 롤링
        $('.index_banner').bxSlider({  // 해당 class 
            autoControls:false,
//          pager:false,
            auto:true,
//          autoHover:false,
            pause:5000  // 5초마다
          });
    });

</script>  -->
<!-- <style>
#index_banner_area{ width:990px; margin:auto; padding-top:10px;}
#index_banner_area .banner_loader{ float:left; width:990px; height:250px; border:1px solid #838383; position:relative; overflow:hidden;}
#index_banner_area .fixed_banner{ width:277px; height:337px; float:right; padding:3px 0 0 3px; background:url(../images/common/bg_fixedbanner.png) no-repeat; position:relative;}
#index_banner_area .fixed_banner .ico_hotdeal{ position:absolute; background:url(../images/common/ico_hot_deal.png) no-repeat; width:72px; height:82px; right:0; top:3px;}

.banner_loader .bx-wrapper .bx-pager { text-align:center; margin-top:-30px; position:absolute; z-index:100; width:100%;}
.banner_loader .bx-wrapper .bx-pager .bx-pager-item,
.banner_loader .bx-wrapper .bx-controls-auto .bx-controls-auto-item {display: inline-block; *zoom: 1;   *display: inline;}
.banner_loader .bx-wrapper .bx-pager.bx-default-pager a {
    text-indent: -9999px;
    display: block;
    width: 17px;
    height: 17px;
    margin: 0 5px;
    background:url(../images/common/bg_banner_paging.png) no-repeat;
}
.banner_loader .bx-wrapper .bx-pager.bx-default-pager a:hover,
.banner_loader .bx-wrapper .bx-pager.bx-default-pager a.active { background-position:0 -20px;}
.banner_loader .bx-wrapper .bx-controls-direction a {
    position: absolute;
    top: 50%; margin-top:-50px;
    outline: 0;
    text-indent: -9999px;
    z-index: 99;
    width:39px; height:100px;
}
.banner_loader .bx-wrapper .bx-prev {left:0px; background: url(../images/common/btn_banner_prev.png) no-repeat;}
.banner_loader .bx-wrapper .bx-next {right: 0px;background: url(../images/common/btn_banner_next.png) no-repeat;}
.banner_loader .bx-wrapper .bx-prev:hover {background: url(../images/common/btn_banner_prev_over.png) no-repeat;}
.banner_loader .bx-wrapper .bx-next:hover {background: url(../images/common/btn_banner_next_over.png) no-repeat;}
</style> --> 
</head>
<body>

  <div class="slider">
    <div><img src="/images/clock.jpg"></div>
    <div><img src="/images/clock2.jpg"></div>
  </div>
 <%-- <div id="index_banner_area" class="clearfix">
     <div class="banner_loader">
     <ul class="index_banner">
      <li>
       <img src="<c:url value='/'/>/images/clock.jpg" width="990" height="250" alt=""> 
          <p class="index_txt">
          <img src="<c:url value='/'/>/images/common/txt_visual.png" alt="글자이미지">
          </p>
      </li>
      <li>
       <img src="<c:url value='/'/>/images/clock2.jpg" width="990" height="250" alt=""> 
          <p class="index_txt">
          <img src="<c:url value='/'/>/images/common/txt_visual.png" alt="글자이미지">
          </p>
      </li>
      <li>
       <img src="<c:url value='/'/>/images/common/Tulips.jpg" width="990" height="250" alt=""> 
          <p class="index_txt">
         <img src="<c:url value='/'/>/images/common/txt_visual.png" alt="글자이미지">
          </p>
      </li>
      <li>
       <img src="<c:url value='/'/>/images/common/Penguins.jpg" width="990" height="250" alt=""> 
          <p class="index_txt">
          <img src="<c:url value='/'/>/images/common/txt_visual.png" alt="글자이미지">
          </p>
      </li>
     </ul>

</div>  --%>


</body>
</html>