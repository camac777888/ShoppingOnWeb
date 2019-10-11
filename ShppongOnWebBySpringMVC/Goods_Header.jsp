<%@ page pageEncoding="UTF-8"%>
<table width="100%" border="0" align="center">
  <tr>
    <td width="616"><img src="images/${param.image}" align="absmiddle" /></td>
    <td width="734" align="right">
    <img src="images/mycar1.jpg"align="absmiddle"/><a href="${pageContext.request.contextPath}/customer/cart.action">&nbsp;購物車</a>    
    | <a href="${pageContext.request.contextPath}/customer/list.action">商品列表</a>
    | <a href="${pageContext.request.contextPath}/customer/logout.action">帳號登出</a> 
    </td>
  </tr>
</table>