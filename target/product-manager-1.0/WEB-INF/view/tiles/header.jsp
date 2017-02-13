<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="<c:url value="/" />">Product Maintenance</a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav navbar-right">
        <c:choose>
          <c:when test='<%= request.getUserPrincipal() == null %>'>
            <li><a href="<c:url value="/admin" />">Login</a></li>
          </c:when>
          <c:otherwise>
            <li><a href="<c:url value="/login/out" />">Logout</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</div>

<div class="header jumbotron col-md-8 col-md-offset-2 text-center">
  <h2 class="text-muted">${subject}</h2>
</div>
