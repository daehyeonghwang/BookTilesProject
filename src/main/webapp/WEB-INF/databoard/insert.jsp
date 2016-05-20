<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/table.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
/*
 *   selector : 내장객체 , 태그명( $('태그명')=>$('tr')) , 
                ID명 ($('#ID명')) ,
                Class명 ($('.class명'))
             $(document)
 */
$(function(){
	$('#sendBtn').click(function(){
		var name=$('#name').val();
		if(name.trim()=="")
		{
			$('#name').focus();
			$('#name').val("");
			return;
		}
		var subject=$('#subject').val();
		if(subject.trim()=="")
		{
			$('#subject').focus();
			$('#subject').val("");
			return;
		}
		var content=$('#content').val();
		if(content.trim()=="")
		{
			$('#content').focus();
			$('#content').val("");
			return;
		}
		var pwd=$('#pwd').val();
		if(pwd.trim()=="")
		{
			$('#pwd').focus();
			$('#pwd').val("");
			return;
		}
		
		$('#frm').submit();
	});
	var fileIndex=0;
	$('#addBtn').click(function(){
		$('#fileView').append(
		  '<tr id=f'+fileIndex+'>'
		  +'<td width=10%>파일'+(fileIndex+1)+'</td>'
		  +'<td width=90% align=left>'
		  +'<input type=file name=files['+fileIndex+'] size=25>'
		  +'</td>'
		  +'</tr>'
		); 
		fileIndex=fileIndex+1;
	});
	$('#removeBtn').click(function(){
		$('#f'+(fileIndex-1)).remove();
		fileIndex=fileIndex-1;
	});
});
</script>
</head>
<body>
  <center>
    <h3>글쓰기</h3>
    <form method=post action="databoard_insert_ok.do" id="frm"
     enctype="multipart/form-data">
    
          <table id="table_content" width=500>
           <tr>
            <td width=15% align="right">이름</td>
            <td width=85% align="left">
             <input type="text" name=name size=12 id="name">
            </td>
           </tr>
           <tr>
            <td width=15% align="right">제목</td>
            <td width=85% align="left">
             <input type="text" name=subject size=45 id="subject">
            </td>
           </tr>
           <tr>
            <td width=15% align="right">내용</td>
            <td width=85% align="left">
             <textarea rows="10" cols="50" name=content id="content"></textarea>
            </td>
           </tr>
           <tr>
            <td width=15% align="right">첨부파일</td>
            <td width=85% align="left">
             <table border=0 width=400>
              <tr>
               <td colspan="2" align="right">
                 <input type="button" value="추가" id="addBtn">
                 <input type="button" value="삭제" id="removeBtn">
               </td>
              </tr>
             </table>
             <table border=0 width=400 id="fileView">
             </table>
            </td>
           </tr>
           <tr>
            <td width=15% align="right">비밀번호</td>
            <td width=85% align="left">
             <input type="password" size=10 name=pwd id="pwd">
            </td>
           </tr>
           <tr>
            <td colspan="2" align="center">
             <input type="button" value="글쓰기" id="sendBtn">
             <input type="button" value="취소"
              onclick="javascript:history.back()">
            </td>
           </tr>
          </table>
        
    </form>
   </center>
</body>
</html>







