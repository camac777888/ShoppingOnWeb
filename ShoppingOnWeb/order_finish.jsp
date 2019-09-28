<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>訂單提交成功</title>
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
<div align="center" >
  <p class="text7">謝謝您的購買!</p>
  <p class="text7">您的訂單編號為:${orderid}</p>
  <p class="text7">您可以繼續購物了!</p>
   <p class="text7">
       <a href="controller?action=main">返回主頁面</a>
  </p>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
