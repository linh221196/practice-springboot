<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <!-- Latest compiled and minified CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet" />

    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <link href="/css/demo.css" rel="stylesheet" />
  </head>
  <body>
    <div class="container mx-auto pt-5">
      <h1>User name ${editUser.name} Info</h1>
      <hr />
      <div class="card mb-5" style="width: 60%">
        <ul class="list-group list-group-flush">
          <li class="list-group-item">ID: ${editUser.id}</li>
          <li class="list-group-item">Email: ${editUser.email}</li>
          <li class="list-group-item">Name: ${editUser.name}</li>
          <li class="list-group-item">Phone: ${editUser.phone}</li>
        </ul>
      </div>

      <form:form
        method="post"
        action="/admin/user/edit/${editUser.id}"
        modelAttribute="User">
        <div class="mb-3">
          <label class="form-label">Email:</label>
          <form:input
            type="email"
            class="form-control"
            path="email"
            value="${editUser.email}"
            disabled="true" />
        </div>
        <div class="mb-3">
          <label class="form-label">Password:</label>
          <form:input
            type="password"
            class="form-control"
            path="password"
            value="${editUser.password}"
            disabled="true" />
        </div>
        <div class="mb-3">
          <label class="form-label">Name:</label>
          <form:input
            type="text"
            class="form-control"
            path="name"
            value="${editUser.name}" />
        </div>
        <div class="mb-3">
          <label class="form-label">Phone:</label>
          <form:input
            type="text"
            class="form-control"
            path="phone"
            value="${editUser.phone}" />
        </div>
        <div class="text-center">
          <button class="btn btn-primary" type="submit">Submit</button>
        </div>
      </form:form>

      <form:form action="/admin/user">
        <button class="btn btn-success">Return to Homepage</button>
      </form:form>
    </div>
  </body>
</html>
