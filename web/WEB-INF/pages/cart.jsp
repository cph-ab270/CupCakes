<%@ page import="model.entity.Cupcake" %>
<%@ page import="java.util.List" %>

<% List<Cupcake> cupcakes = (List<Cupcake>) session.getAttribute("cupcakes"); %>
<div class="container">
    <h2>Your Cart:</h2>
    <% if (cupcakes == null) { %>
        <strong>Cart is empty</strong>
    <% } else { %>
        <table class="table table-condensed">
            <thead>
            <tr>
                <th>Topping</th>
                <th>Bottom</th>
                <th>Amount</th>
            </tr>
            </thead>
            <tbody>

            <%for (Cupcake cupcake : cupcakes) {%>
            <tr>
                <td><%=cupcake.getTopping().getName()%>
                </td>
                <td><%=cupcake.getBottom().getName()%>
                </td>
                <td><%=cupcake.getAmount()%>
                </td>
              <td><%=(cupcake.getTopping().getPrice() + cupcake.getTopping().getPrice()) * cupcake.getAmount()%></td>
            </tr>
            <% } %>

            </tbody>
        </table>

        <hr>
        <form method="POST" action="${root}cart/empty-cart">
             <p>Total Price: <b>${finalPrice}</b> <button class="btn btn-default">BUY</button></p>
        </form>
    <% } %>
</div>