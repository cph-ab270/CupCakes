<%@ page import="java.util.List" %>
<%@ page import="model.entity.Invoice" %><% List<Invoice> invoices = ((List<Invoice>) request.getAttribute("invoices")); %>
<div class="container">
    <h2>Your previous orders:</h2>
    <table class="table table-condensed">
        <thead>
        <tr>
            <th>Ordered at</th>
            <th>price</th>
        </tr>
        </thead>
        <tbody>
        <%for (Invoice invoice : invoices) {%>
        <tr>
            <td><%=invoice.getOrderedAt()%>
            </td>
            <td><%=invoice.getPrice()%>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>