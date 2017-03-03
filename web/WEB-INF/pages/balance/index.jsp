<%@ page import="model.entity.User" %><% User user = ((User) session.getAttribute("user")); %>
<% if(user != null) { %>
<h2> Your balance: <%= user.getBalance() %></h2>
<form method = "POST">
    <input id ="amount" label = "amount">
<button type ="button" class = "btn btn-default">Add Funds.</button>
</form>
        <% } %>


