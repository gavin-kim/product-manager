<html>
<head>
  <jsp:include page="tiles/head.jsp"/>
</head>
<body>

<header class="container">
  <jsp:include page="tiles/header.jsp"/>
</header>

<section class="container">
  <jsp:include page="tiles/${body}"/>
</section>

<footer class="container">
  <jsp:include page="tiles/footer.jsp"/>
</footer>
</body>
</html>
