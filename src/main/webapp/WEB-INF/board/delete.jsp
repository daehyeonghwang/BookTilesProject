<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/table.css">
</head>
<body>
  <center>
    <h3>�����ϱ�</h3>
    <form method=post action="board_delete_ok.do">
    <table id="table_content">
     <tr>
       <td align=right width=15%>��й�ȣ</td>
       <td align=left width=85%>
        <input type="password" name=pwd size=13>
        <input type=hidden name=no value="${no }">
        <input type=hidden name=page value="${page }">
       </td>
     </tr>
     <tr>
       <td colspan="2" align=center>
        <input type=submit value="����">
        <input type=button value="���"
        onclick="javascript:history.back()"
        >
       </td>
     </tr>
    </table>
    </form>
  </center>
</body>
</html>



