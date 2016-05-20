<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/table.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$('#writeBtn').click(function(){
		location.href="databoard_insert.do";
	});
});
</script>
</head>
<body>
  <center>
    <h3>게시판</h3>
    <table style="width:600px">
      <tr>
       <td align=left>
        <input type="button" value="글쓰기" id="writeBtn">
       </td>
      </tr>
    </table>
    <table id="table_content" style="width:600px">
      <tr>
       <th width=10%>번호</th>
       <th width=45%>제목</th>
       <th width=15%>이름</th>
       <th width=20%>작성일</th>
       <th width=10%>조회수</th>
      </tr>
      <c:forEach var="dto" items="${list }">
        <tr class="dataTr">
          <td width=10% class="tdcenter">${dto.no }</td>
          <td width=45% class="tdleft">
             <a href="databoard_content.do?no=${dto.no }&page=${curpage}">${dto.subject }</a>
            <c:if test="${today==dto.regdate }">
              <sup><img src="image/icon_new (2).gif"></sup>
            </c:if>
          </td>
          <td width=15% class="tdcenter">${dto.name }</td>
          <td width=20% class="tdcenter">
           <fmt:formatDate value="${dto.regdate }"
             pattern="yyyy-MM-dd"/>
          </td>
          <td width=10% class="tdcenter">${dto.hit }</td>
        </tr>
      </c:forEach>
    </table>
    <table border=0 style="width:600px">
     <tr>
      <td align=right>
       ${curpage } page / ${totalpage } pages
      </td>
     </tr>
    </table>
  </center>
</body>
</html>



