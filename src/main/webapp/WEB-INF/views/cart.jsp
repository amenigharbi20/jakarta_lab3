<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<jsp:include page="header.jsp" />

<h2>Your cart</h2>

<c:choose>
  <c:when test="${empty cart}">
    <p>Your cart is empty.</p>
  </c:when>
  <c:otherwise>
    <ul>
      <c:forEach var="item" items="${cart}" varStatus="st">
        <li>
            ${item.name} -
          <fmt:formatNumber value="${item.price}" type="currency"/>
          <form method="post" action="${pageContext.request.contextPath}/cart/remove" style="display:inline">
            <input type="hidden" name="index" value="${st.index}">
            <button type="submit">Remove</button>
          </form>
        </li>
      </c:forEach>
    </ul>
<c:if test="${not empty cart}"><p>Subtotal: ${subtotal} EUR | After 10% discount: <b>${discountedTotal} EUR</b></p></c:if>
    <p><a href="${pageContext.request.contextPath}/checkout">Proceed to checkout</a></p>
  </c:otherwise>
</c:choose>

<p><a href="${pageContext.request.contextPath}/catalog/products">Continue shopping</a></p>

<jsp:include page="footer.jsp" />