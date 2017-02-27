<%@ page import="model.entity.User" %><% User user = ((User) session.getAttribute("user")); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="../assets/style.css">
    <title>${title}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${assets}bootstrap.min.css">
    <link rel="stylesheet" href="${assets}style.css">
    <script src="${assets}jquery-3.1.1.min.js"></script>
    <script src="${assets}bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button materialType="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Material library</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${root}">Homepage</a>
                </li>
                <% if (user == null) {%>
                <li>
                    <a href="${root}sign/in">Sign in</a>
                </li>
                <li>
                    <a href="${root}sign/up">Create new account</a>
                </li>
                <% } else {%>
                <li>
                    <a href="${root}sign/out">Sign out <%= user.getName() %> <%= user.getSurname() %>
                    </a>
                </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>
<% if (request.getAttribute("alert") != null) {%>
${alert}
<% } %>
<div class="container">
    <jsp:include page="/WEB-INF/pages/${template}.jsp"/>
</div>
<footer class="navbar navbar-default container-fluid text-center footer">
    <p>Material library <b>Copyright Adam Becvar</b> :)</p>
</footer>
</body>
</html>
