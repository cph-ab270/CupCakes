<%@ page import="model.entity.Bottom" %>
<%@ page import="model.entity.Topping" %>
<%@ page import="java.util.List" %>
<form method="post">
    <div class="form-group">
        <label for="bottom">Select your Topping:</label>
        <select class="form-control" id="bottom" name="topping" required>
            <% for (Topping topping : ((List<Topping>) request.getAttribute("toppings"))) { %>
            <option data-price="<%=topping.getPrice()%>"
                    value="<%=topping.getId()%>"><%=topping.getName()%>; <%=topping.getPrice()%> DKK
            </option>
            <% } %>
        </select>
    </div>

    <div class="form-group">
        <label for="topping">Select your Bottom:</label>
        <select class="form-control" id="topping" name="bottom" required>
            <% for (Bottom bottom : ((List<Bottom>) request.getAttribute("bottoms"))) { %>
            <option data-price="<%=bottom.getPrice()%>"
                    value="<%=bottom.getId()%>"><%=bottom.getName()%>; <%=bottom.getPrice()%> DKK
            </option>
            <% } %>
        </select>
    </div>

    <div class="form-group">
        <label for="amount">Enter your amount of cupcakes:</label>
        <input type="number" class="form-control" id="amount" name="amount" value="1" required>
    </div>

    <hr>
    Current Price: <span id="price">0</span> DKK.
    <button type="button" class="btn btn-success">Continue</button>
</form>
<script>
    var bottom = getOptionPrice($('#bottom'));
    var topping = getOptionPrice($('#topping'));
    var amount = $('#amount').val();
    $('#bottom').on("change",function () {
        bottom = getOptionPrice(this);
        recalculate();
    });
    $('#topping').on("change",function () {
        topping = getOptionPrice(this);
        recalculate();
    });
    function getOptionPrice(e) {
        return parseInt($('option:selected', e).attr('data-price'));
    }

    $('#amount').on("keyup paste",function () {
        amount = $('#amount').val();
        recalculate();
    });

    function recalculate() {
        price = (bottom+topping)*amount;
        $("#price").text(price);
    }
</script>