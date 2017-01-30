<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row col-md-8 col-md-offset-2">
  <form action="/products/add" method="post">
    <div class="form-group">
      <label class="control-label" for="code">Code: </label>
      <input class="form-control"
             id="code" type="text" name="code"
             placeholder="Code" value="${product.code}"
             <c:if test="${edit}">readonly</c:if> />
      <c:if test="${errorMessages.codeError != null}">
        <div class="alert alert-danger">${errorMessages.codeError}</div>
      </c:if>
    </div>

    <div class="form-group">
      <label class="control-label" for="description">Description: </label>
      <input class="form-control"
             id="description" type="text" name="description"
             placeholder="Description" value="${product.description}"/>
      <c:if test="${errorMessages.descError != null}">
        <div class="alert alert-danger">${errorMessages.get("descError")}</div>
      </c:if>
    </div>

    <div class="form-group">
      <label class="control-label" for="price">Price: </label>
      <input class="form-control"
             id="price" type="text" name="price"
             placeholder="Price" value="${product.price}"/>
      <c:if test="${errorMessages.priceError != null}">
        <div class="alert alert-danger">${errorMessages.get("priceError")}</div>
      </c:if>
    </div>
    <div class="text-right">
      <div class="form-group">
        <input type="hidden" name="edit" value="${edit}"/>
        <input class="btn btn-lg btn-primary"
               type="submit" name="confirm" value="Update"/>
        <a class="btn btn-lg btn-danger" href="<c:url value="/products" />">Cancel</a>
      </div>
    </div>
  </form>
</div>
