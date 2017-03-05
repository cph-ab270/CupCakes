<%@ page import="model.entity.User" %>
<% User user = ((User) session.getAttribute("user")); %>
<% boolean isAdmin = ((Boolean) request.getAttribute("admin")).booleanValue(); %>
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
    <link rel="shortcut icon" type="image/png" href="${assets}favicon.ico"/>
    <link rel="shortcut icon" type="image/png" href="${assets}favicon.ico"/>
</head>
<body class="bg">
<nav class="navbar navbar-default navBg">
    <div class="container">
        <div class="navbar-header">
            <button materialType="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${root}"><img src="http://lukasjay.me/cc/logo.png" height="20" width="20"> </a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${root}">Home</a>
                </li>
                <li>
                    <a href="${root}store">Order Cupcakes</a>
                </li>
                <% if (user == null) {%>
                <li>
                    <a href="${root}sign/in">Sign in</a>
                </li>
                <li>
                    <a href="${root}sign/up">Create an account</a>
                </li>
                <% } else {%>
                <li>
                    <a href="${root}sign/out">Sign out, <%= user.getName() %> <%= user.getSurname() %>
                    </a>
                </li>
                <% if (isAdmin) {%>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">Admin options <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="${root}topping/add">Add topping</a>
                            </li>
                        </ul>
                    </li>
                <% } %>
                <% } %>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <% if (user != null) {%>
                <li>
                    <a href="${root}balance">Balance: <b> <%= user.getBalance() %> DKK</b>
                    </a>
                </li>
                <li><a href="${root}invoices"> Invoices</a></li>
                <% } %>
                <li><a href="${root}cart">Cart (<jsp:include page="/WEB-INF/components/cart.jsp"/>)</a></li>
            </ul>
        </div>
    </div>
</nav>
<% if (request.getAttribute("alerts") != null) {%>
${alerts}
<% } %>
<div class="container bgContainer">
    <jsp:include page="/WEB-INF/pages/${template}.jsp"/>
</div>
<footer class="navbar navbar-default container-fluid text-center footer">
    <p>Copyright <b>Adam Becvar & Lukas Jurgelionis</b></p>
</footer>
</body>
</html>
