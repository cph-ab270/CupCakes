<%@ page import="model.entity.Invoice" %>
<%@ page import="model.entity.Cupcake" %>
<%@ page import="java.util.List" %>
<% int totalPrice = 0; %>
<div class="container">
    <h2>Your Cart:</h2>
    <table class="table table-condensed">
        <thead>
        <tr>
            <th>Topping</th>
            <th>Bottom</th>
            <th>Amount</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
            <% for(Cupcake cupcake : ((List<Cupcake>) session.getAttribute("cupcakes"))) {%>
            <tr>
            <td><%=cupcake.getTopping().getName()%></td>
            <td><%=cupcake.getBottom().getName()%></td>
            <td><%=cupcake.getAmount()%>
            <td><%=(cupcake.getTopping().getPrice() + cupcake.getTopping().getPrice()) * cupcake.getAmount()%></td>
            </tr>
            <% totalPrice += (cupcake.getTopping().getPrice() + cupcake.getTopping().getPrice()) * cupcake.getAmount(); } %>
        </tbody>
    </table>
    <hr>
    <form method = "POST" action ="${root}cart/empty">
        <p>Total Price: <b><%= totalPrice%></b> <button type="button" class="btn btn-default">BUY</button></p>
    </form>
</div>