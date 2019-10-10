<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %> <base href="<%=basePath%>">
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>會員登入</title>
<link rel="stylesheet" type="text/css" href="css/public.css">
</head>

<body>
<div class="header"></div>
<hr width="100%" />
<!--錯誤訊息顯示 -->
<ul>
<c:forEach var="error" items="${errors}">
	<li class="error">${error}</li>

</c:forEach>

</ul>
<form action="${pageContext.request.contextPath}/customer/login.action" method="post" >
  <table align="center" class="login">
    <tr height="40" >
      <td colspan="2" align="center"><h2>登入</h2></td>
    </tr>
    <tr height="60" >
      <td width="50%" align="right" ><img src="images/3.jpg" align="absmiddle"/>&nbsp;&nbsp;用戶帳號:</td>
      <td><input type="text" name="id" class="textfield" placeholder="請輸入帳號" /></td>
    </tr>
    <tr height="40" >
      <td width="50%" align="right"><img src="images/2.jpg" align="absmiddle"/>&nbsp;&nbsp;用戶密碼:</td>
      <td><input type="password" name="password" class="textfield" placeholder="請輸入密碼" /></td>
    </tr>
    <tr height="40" >
      <td align="right">&nbsp;</td>
      <td ><input type="image" src="images/login_button.jpg" onclick="document.forms[0].fn.value='login'" />
        &nbsp;&nbsp;&nbsp;&nbsp; <a href="${pageContext.request.contextPath}/customer/reg_in.action"><img src="images/reg_button.jpg" border="0" /></a></td>
    </tr>
  </table>
  <input type="hidden" name="${pageContext.request.contextPath}/customer/reg.action">
</form>

</body>
</html>