<%@ page import="java.util.List" %>
<%@ page import="model.entity.Topping" %>
<form method = "post">
<div class="form-group">
    <label for="bottom">Select your bottom:</label>
    <select class="form-control" id="bottom" required>
        <option>Chocolate</option>
        <option>Vanilla</option>
        <option>Nutmeg</option>
        <option>Pistacio</option>
        <option>Almond</option>
    </select>
</div>

<div class="form-group">
    <label for="topping">Bottoms:</label>
    <select class="form-control" id="topping" required>
        <% for (Topping topping : ((List<Topping>) request.getAttribute("toppings"))) { %>
          <option value="<%=topping.getId()%>"><%=topping.getName()%> <%=topping.getPrice()%> DKK</option>
        <% } %>
    </select>
</div>

<div class="form-group">
    <label for="amount">Enter your amount of cupcakes:</label>
    <input type="text" class="form-control" id="amount" required>
</div>

<hr>
Current Price: <b>X</b> DKK.
    <button type="button" class="btn btn-success">Continue</button>   </form>