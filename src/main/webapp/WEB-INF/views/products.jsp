<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="header.jsp" />

<h2>Products</h2>

<p>
    Filter:
    <a href="${pageContext.request.contextPath}/catalog/products">All</a> |
    <a href="${pageContext.request.contextPath}/catalog/products?category=electronics">Electronics</a> |
    <a href="${pageContext.request.contextPath}/catalog/products?category=books">Books</a> |
    <a href="${pageContext.request.contextPath}/catalog/products?category=home">Home</a> |
    <a href="${pageContext.request.contextPath}/catalog/products?category=accessories">Accessories</a>
</p>

<c:forEach var="product" items="${products}">
    <div class="product-card">
        <h3>
            <a href="${pageContext.request.contextPath}/catalog/product?id=${product.id}">
                    ${product.name}
            </a>
        </h3>
        <p><fmt:formatNumber value="${product.price}" type="currency"/></p>
    </div>
</c:forEach>

<jsp:include page="footer.jsp" />