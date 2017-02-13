<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="table-responsive col-md-8 col-md-offset-2">
  <table class="table table-striped">
    <th class="text-center">Code</th>
    <th class="text-center">Description</th>
    <th class="text-center">Price</th>

    <c:set var="isCashier" value='<%= request.isUserInRole("cashier")%>'/>
    <c:set var="isShelver" value='<%= request.isUserInRole("shelver")%>'/>
    <c:set var="isManager" value='<%= request.isUserInRole("manager")%>'/>

    <c:if test="${isShelver}">
      <th></th>
    </c:if>
    <c:if test="${isManager}">
      <th></th><th></th>
    </c:if>

    <c:forEach var="product" items="${products}">
      <tr>
        <td>${product.code}</td>
        <td>${product.description}</td>
        <td>${product.price}</td>

        <c:if test="${isShelver || isManager}">
          <td><a class="btn btn-sm btn-info text-center"
                 href="<c:url value="/products/edit?code=${product.code}" />"/>
            Edit
          </td>
        </c:if>

        <c:if test="${isManager}">
          <td><a class="btn btn-sm btn-warning text-center"
                 href="<c:url value="/products/delete?code=${product.code}" />"/>
            Delete
          </td>
        </c:if>
      </tr>
    </c:forEach>
  </table>

  <c:if test="${isCashier || isShelver || isManager}">
    <form action="/products/add" method="get">
      <input class="btn btn-success btn-lg btn-block"
             type="submit" value="Add Product"/>
    </form>
  </c:if>
</div>