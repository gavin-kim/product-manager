<div class="jumbotron table-responsive col-md-8 col-md-offset-2">
  <table class="table">
    <th class="text-center">Code</th>
    <th class="text-center">Description</th>
    <th class="text-center">Price</th>
    <tr class="text-center">
      <td>${product.code}</td>
      <td>${product.description}</td>
      <td>${product.price}</td>
    </tr>
  </table>
  <form class="text-right" action="/products/delete" method="post">
    <input type="hidden" name="code" value="${product.code}"/>
    <input class="btn btn-lg btn-primary" type="submit" name="confirm"
           value="Yes"/>
    <input class="btn btn-lg btn-danger" type="submit" name="confirm"
           value="No"/>
  </form>
</div>