<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="header.jsp" />

<h2>${product.name}</h2>
<p>Category: ${product.category}</p>
<p>Price: <fmt:formatNumber value="${product.price}" type="currency"/></p>

<form method="post" action="${pageContext.request.contextPath}/cart/add">
  <input type="hidden" name="productId" value="${product.id}">
  <button type="submit">Add to cart</button>
</form>

<p><a href="${pageContext.request.contextPath}/catalog/products">&larr; Back to products</a></p>

<jsp:include page="footer.jsp" />