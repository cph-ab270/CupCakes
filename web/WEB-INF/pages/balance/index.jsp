<%@ page import="model.entity.User" %><% User user = ((User) session.getAttribute("user")); %>
<% if(user != null) { %>
Your balance: <b><%= user.getBalance() %></b> DKK.<hr>
<form>
    <img src = "https://www.oplata.com/static/v1/files/logos/verified-by-visa.png" height=20% width=20%><hr>
    Enter your Credit Card Number:
    <input id = "cc" required><br>
    <hr>
    Enter your Security Code:
    <input id = "cvv" required>
    <hr>
</form>
<form method = "POST">
    Enter the amount you wish to add:
    <input id ="amount" name = "amount"> <b>DKK.</b>
    <hr>
    <button type ="button" class = "btn btn-default">Add Funds.</button>
</form>
        <% } %>


