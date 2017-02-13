<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row col-md-8 col-md-offset-2">
  <form action="j_security_check" method="post">
    <div class="form-group">
      <label class="control-label" for="username">Username: </label>
      <input class="form-control"
             id="username" type="text" name="j_username"
             placeholder="Username"/>
    </div>

    <div class="form-group">
      <label class="control-label" for="password">Password: </label>
      <input class="form-control"
             id="password" type="password" name="j_password"
             placeholder="Password"/>
    </div>

    <div class="form-group text-right">
      <input class="btn btn-primary btn-lg" type="submit" value="Submit"/>
      <input class="btn btn-danger btn-lg" type="button" value="Back"
             onclick="javascript:history.go(-1)"/>
    </div>
  </form>
</div>

