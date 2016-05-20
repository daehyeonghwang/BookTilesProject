<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script type="text/javascript">
$(function () {
    $('#bar').highcharts({
    	
        chart: {
            type: 'column',
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                viewDistance: 25,
                depth: 40
            }
        },

        title: {
            text: '서적별 가격 현황'
        },

        xAxis: {
            categories: <%=request.getAttribute("title")%>
        },

        yAxis: {
            allowDecimals: false,
            min: 0,
            title: {
                text: 'Number of fruits'
            }
        },

        tooltip: {
            headerFormat: '<b>{point.key}</b><br>',
            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'
        },

        plotOptions: {
            column: {
                stacking: 'normal',
                depth: 40
            }
        },

        series: 
        	<%=request.getAttribute("json")%>
       
    });
});
</script>
</head>
<body>
  <div id="extra">
						<div class="container">
							<div class="row no-collapse-1">
							    <c:forEach var="vo" items="${list }" varStatus="status">
							     <c:if test="${status.index<3 }">
								  <section class="4u"> <a href="#" class="image featured"><img src="${vo.poster }" width=120 height=150></a>
									<div class="box">
										<p style="font-size: 7pt">${vo.review }</p>
										<a href="#" class="button">상세보기</a> </div>
								  </section>
								 </c:if>
								</c:forEach>
							</div>
							<div class="row no-collapse-1">
								<c:forEach var="vo" items="${list }" varStatus="status">
							     <c:if test="${status.index>=3 && status.index<6 }">
								  <section class="4u"> <a href="#" class="image featured"><img src="${vo.poster }" width=120 height=150></a>
									<div class="box">
										<p style="font-size: 7pt">${vo.review }</p>
										<a href="#" class="button">상세보기</a> </div>
								  </section>
								 </c:if>
								</c:forEach>
								
							</div>
						</div>
					</div>

				<!-- Main -->
					<div id="main">
						<div class="container">
							<div class="row"> 
								
								<!-- Content -->
								<div class="6u">
									<section>
										<ul class="style">
											<li class="fa fa-wrench">
												<h3>교보서적</h3>
												<span>교보문고 특별이벤트</span> </li>
											<li class="fa fa-leaf">
												<h3>알라딘</h3>
												<span>알라딘 특별이벤트</span> </li>
										</ul>
									</section>
								</div>
								<div class="6u">
									<section>
										<ul class="style">
											<li class="fa fa-cogs">
												<h3>반디앤루이스</h3>
												<span>반디앤루이스 특별이벤트</span> </li>
											<li class="fa fa-road">
												<h3>영풍문고</h3>
												<span>영풍문고 특별이벤트</span> </li>
										</ul>
									</section>
								</div>
							</div>
						</div>
					</div>
			<div id="footer" class="wrapper style2">
				<div class="container">
				<section>
					<header class="major">
						
						<!-- 시각화 -->
						<div id="bar" style="height: 400px; min-width: 310px; max-width: 800px;margin: 0 auto;"></div>
					</header>
					
				</section>
			</div>
		</div>
  
</body>
</html>