<%@page contentType="text/html; charset=UTF-8" %>>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %> <base href="<%=basePath%>">

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>購物車</title>
    <link rel="stylesheet" type="text/css" href="css/public.css">
    <style type="text/css">
       table {
            border-collapse: collapse;
        }

        .threeboder {
            border: 1px solid #5B96D0;
        }

        .trow {
            border-right: 1px solid #5B96D0;
            border-bottom: 1px solid #5A96D6;
        }

        .theader {
            background-color: #A5D3FF;
            font-size: 14px;
            border-right: 1px solid #5B96D0;
            border-bottom: 1px solid #5A96D6;
        }
    </style>
</head>

<body>
<jsp:include page="Goods_Header.jsp" flush="true">
	<jsp:param name="image" value="mycar.jpg"/>
</jsp:include>

<hr width="100%"/>
<div class="text3" align="center">您選好的商品</div>
<br>
<form action="${pageContext.request.contextPath}/customer//sub_ord.action" method="post">
<table width="100%" border="0" align="center" class="threeboder">
    <tr bgcolor="#A5D3FF">
        <td height="50" align="center" class="theader">商品名稱</td>
        <td width="15%" align="center" class="theader">取消</td>
        <td width="8%" align="center" class="theader">數量</td>
        <td width="15%" align="center" class="theader">單價</td>
        <td width="15%" align="center" class="theader">小計</td>
    </tr>
    <c:forEach var="cow" items="${cart}">
    <tr>    
   		 <tr>    
        <td height="50" align="left" class="trow">&nbsp;&nbsp;${cow.goodsname}</td>
<td align="center" class="trow">&yen;<a href="${pageContext.request.contextPath}/customer/delete/${cow.goodsid}.action">刪除</a></td>
         <td height="50" align="left" class="trow">&nbsp;&nbsp;${cow.quantity}</td>
         <td height="50" align="left" class="trow">&nbsp;&nbsp;${cow.price}</td>
         <td height="50" align="left" class="trow">&nbsp;&nbsp;${cow.price*cow.quantity}</td>
        
        </tr>
    </c:forEach>
    
    <tr>
        <td height="50" colspan="5" align="right">合計&yen;<span id="total">${total}</span>&nbsp;&nbsp;</td>
    </tr>
</table>
<br>
<div align="center">
<c:if test="${not empty cart}">
<input	type="image" src="images/submit_order.jpg" border="0">&nbsp;&nbsp;
    </c:if>
    <input type="hidden" name="action" value="sub_ord">
</form>    
</div>
<%@include file="footer.jsp" %>
</body>
</html>
