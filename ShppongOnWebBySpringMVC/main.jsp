<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %> <base href="<%=basePath%>">

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>大廳</title>
<link rel="stylesheet" type="text/css" href="css/public.css">
<style type="text/css">
a:link {
	font-size: 18px;
	color: #DB8400;
	text-decoration: none;
	font-weight: bolder;
}
a:visited {
	font-size: 18px;
	color: #DB8400;
	text-decoration: none;
	font-weight: bolder;
}
a:hover {
	font-size: 18px;
	color: #DB8400;
	text-decoration: underline;
	font-weight: bolder;
}
</style>
</head>

<body>
<div class="header">網路購物商城</div>
<hr width="100%" />
<div>
  <p class="text1"> <img src="images/4.jpg"   align="absmiddle" /> <a href="${pageContext.request.contextPath}/customer/list.action">商品列表</a> </p>
  <p class="text2">您可以從產品列表中瀏覽感興趣的商品進行購買</p>
</div>
<hr width="100%" />
<div>
  <p class="text1"> <img src="images/mycar1.jpg" align="absmiddle"  /> <a href="controller?action=cart">購物車</a> </p>
  <p class="text2">把感興趣的商品放入購物車中</p>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
