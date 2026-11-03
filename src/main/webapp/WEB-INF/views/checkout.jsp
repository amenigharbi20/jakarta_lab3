<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:include page="header.jsp" />

<h2>Checkout</h2>
<p style="color:red">${errorMessage}</p>

<c:choose>
    <c:when test="${not empty confirmationName}">
        <p><strong>Thank you, <c:out value="${confirmationName}"/>! Your order has been received.</strong></p>
        <p><a href="${pageContext.request.contextPath}/catalog/products">Back to products</a></p>
    </c:when>
    <c:otherwise>
        <form method="post" action="${pageContext.request.contextPath}/checkout">
            <p>
                <label>Name:<br>
                    <input type="text" name="name" required>
                </label>
            </p>
            <p>
                <label>Email:<br>
                    <input type="email" name="email" required>
                </label>
            </p>
            <p>
                <label>Address:<br>
                    <textarea name="address" rows="3" required></textarea>
                </label>
            </p>
            <button type="submit">Place order</button>
        </form>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp" />