<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>商品資訊</title>
<link rel="stylesheet" type="text/css" href="css/public.css">
<style type="text/css">
.title {
	font-size: 20px;
	color: #FF6600;
	font-style: italic;
}
</style>
</head>
<body>
<jsp:include page="Goods_Header.jsp" flush="true">
	<jsp:param name="image" value="info.jpg"/>

</jsp:include>
<hr width="100%" />
<div class="text3" align="center">${inventory.description}</div>
<table width="100%" border="0" align="center">
  <tr>
    <td width="40%" align="right"><div><img src="goods_images/${inventory.image}" width="360px" height="360px" /></div>
      <br></td>
    <td><div align="center" class="text4">不二價:<span class="title">¥${inventory.price}元</span></div>
      <br>
      <table width="80%" height="200" border="0">
        <tbody>
          <tr>
            <td  width="25%" class="text5" >電腦品牌:</td>
            <td width="25%" class="text6" >${inventory.brand}</td>
            <td width="25%" class="text5" >CPU品牌:</td>
            <td width="25%" class="text6" >${inventory.cpuBrand}</td>
          </tr>
          <tr>
            <td class="text5" >內存容量:</td>
            <td class="text6" >${inventory.memoryCapacity}</td>
            <td class="text5" >CPU型號:</td>
            <td class="text6" >${inventory.cpuType}</td>
          </tr>
          <tr>
            <td class="text5" >硬碟容量:</td>
            <td class="text6" >${inventory.hdCapacity}</td>
            <td class="text5" >顯示卡類型:</td>
            <td class="text6" >${inventory.cardModel}</td>
          </tr>
          <tr>
            <td class="text5" >顯示器尺寸:</td>
            <td class="text6" >${inventory.displaysize}</td>
            <td class="text5" >&nbsp;</td>
            <td class="text6" >&nbsp;</td>
          </tr>
        </tbody>
      </table>
      <br>
      <br>
      <div><a href="controller?action=add&pagename=detail&id=${inventory.id}&name=${inventory.name}&price=${inventory.price}">
      <img src="images/button.jpg"/></a></div></td>
  </tr>
</table>
<%@include file="footer.jsp" %>
</body>
</html>
