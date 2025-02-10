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
      <div class="row">
        <div class="d-flex justify-content-between">
          <h1>Table Users</h1>
          <a href="/admin/user/create" class="btn btn-primary align-self-center"
            >Create User</a
          >
        </div>
        <hr />
        <table class="table table-bordered table-hover">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Actions</th>
            </tr>
            <c:forEach var="user" items="${User}">
              <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>
                  <a class="btn btn-warning" href="/admin/user/edit/${user.id}"
                    >Edit</a
                  >
                  <form action="/admin/user/delete/${user.id}" method="post">
                    <button class="btn btn-danger" type="submit">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </thead>
        </table>
      </div>
      <div class="d-flex justify-content-end">
        <form:form action="/admin/user">
          <button class="btn btn-success">Return to Homepage</button>
        </form:form>
      </div>
    </div>
  </body>
</html>
