<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %> <base href="<%=basePath%>">

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>商品列表</title>
</head>
<link rel="stylesheet" type="text/css" href="css/public.css">
<style type="text/css">
	table {
		border-collapse: collapse;
	}

	
	.col1 {
		padding-top: 5px;
		border-top: 1px dashed #666666;
		text-indent: 40px
	}

	
	.col2 {
		padding-top: 5px;
		border-top: 1px dashed #666666;
		text-align: right;
	}

	
	.col3 {
		padding-top: 5px;
		border-top: 1px dashed #666666;
		text-align: center;
	}
</style>
<body>
<jsp:include page="Goods_Header.jsp" flush="true">
	<jsp:param name="image" value="list.jpg"/>
</jsp:include>
<hr width="100%" />
<div class="text3" align="center">從商品列表中挑選您喜愛的商品</div>
<br>
<table width="100%" border="0" align="center">
  <tr bgcolor="#b4c8ed">
    <th>商品目錄</th>
    <th width="5%" >商品價格</th>
    <th width="15%" >添加到購物車</th>
  </tr>
  
  <c:forEach var="goods" items="${goodlist}" varStatus="status">
  <tr 
  			<c:if test="${status.index % 2 == 0}">
  						bgcolor='#ffffff'
  			</c:if>
  			<c:if test="${status.index % 2 != 0}">
  						bgcolor='#edf8ff'
  			</c:if>
  		>
    <td class="col1"><a href="${pageContext.request.contextPath}/customer/detail/${goods.id}.action">
    	${goods.description}</a></td>
    <td class="col2">${goods.price}</td>
    <td class="col3"><a href="${pageContext.request.contextPath}/customer/add/list/${goods.id}/${goods.name}/${goods.price}.action">添加到購物車</a></td>
  </tr> 
  </c:forEach>

</table>

<hr/>
<div align="center">
<ul class="pagination">

 	 <li><a href="${pageContext.request.contextPath}/customer/paging/prev.action">«</a></li>
 	 <c:forEach var="page" begin="1" end="${totalPageNumber}">
 		 <li><a 
  		<c:if test="${page == currenPage}">
  		class="active"
  		</c:if>
  		href="${pageContext.request.contextPath}/customer/paging/${page}.action">${page}</a></li>
 	 </c:forEach>
   <li><a href="${pageContext.request.contextPath}/customer/paging/next.action">»</a></li>
</ul>
<%@include file="footer.jsp" %>
</body>
</html>
