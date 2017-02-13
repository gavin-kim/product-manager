<div class="footer col-md-8 col-md-offset-2 text-right">
  <h3 class="text-center bg-info">
    <small>${sessionScope.get("footer")}</small>
  </h3>
  ${sessionScope.remove("footer")}
  <h5>&copy; Yoonkwan</h5>
  <h6><%= new java.util.Date() %>
  </h6>
</div>



