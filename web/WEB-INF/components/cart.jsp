<%@ page import="java.util.List" %>
<%@ page import="model.entity.Cupcake" %>
<% List<Cupcake> cupcakes = ((List<Cupcake>) session.getAttribute("cupcakes"));%>

<% if(cupcakes != null) { %>
    <b><%= cupcakes.size() %></b> Item/s
<% } %>