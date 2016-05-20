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
</head>
<body>
  <center>
    <h3>내용보기</h3>
    <table id="table_content" style="width:600px">
      <tr height=27>
        <th width=20%>번호</th>
        <td width=30% align=center>${dto.no }</td>
        <th width=20%>작성일</th>
        <td width=30% align=center>
         <fmt:formatDate value="${dto.regdate }"
           pattern="yyyy-MM-dd"/>
        </td>
      </tr>
      <tr height=27>
        <th width=20%>이름</th>
        <td width=30% align=center>${dto.name }</td>
        <th width=20%>조회수</th>
        <td width=30% align=center>${dto.hit }</td>
      </tr>
      <tr height=27>
        <th width=20%>제목</th>
        <td width=30% align=left colspan="3">${dto.subject }</td>
      </tr>
      <c:if test="${dto.filecount>0 }">
       <tr height=27>
        <th width=20%>첨부파일</th>
        <td width=30% align=left colspan="3">
         <ul style="list-style-type: none;">
           <c:forEach var="fvo" items="${dto.flist}">
             <li><a href="databoard_download.do?fn=${fvo.filename }">${fvo.filename }</a>(${fvo.filesize }Bytes)</li>
           </c:forEach>
         </ul>
        </td>
       </tr>
      </c:if>
      <tr>
        <td colspan="4" align="left" valign="top" height=200>
          <pre>${dto.content }</pre>
        </td>
      </tr>
    </table>
    <table id="table_content" style="width:600px">
      <tr>
       <td align=right>
         <a href="databoard_update.do?no=${dto.no }&page=${page}">
         <img src="image/btn_modify.gif"></a>
         <a href="databoard_delete.do?no=${dto.no }&page=${page}">
         <img src="image/btn_delete.gif"></a>
         <a href="databoard_list.do?page=${page }">
         <img src="image/btn_list.gif" border=0></a>
       </td>
      </tr>
    </table>
  </center>
</body>
</html>





