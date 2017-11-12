<%-- 
    Document   : index
    Created on : 15/09/2017, 6:33:22 PM
    Author     : lisading
--%>

<%@ page import="java.util.*" %>
<%@ page import="org.sample.cart.*" %>

<?xml version="1.0" encoding="utf-8"?>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" language="javascript" src="cart.js"></script>
    </head>    
    <body>

        <h1>Collaborative Shopping Cart</h1>   

        <!-- Left side: Display catalog -->
        <div style="float: left; width: 500px"> 
            <h2>Catalog</h2>


            <!-- Create a table showing catalog. -->
            <table border="1">
                <thead>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Add to Cart</th>
                    <th>Remove from Cart</th>
                </thead>

                <tbody>
                    <% for (Iterator<Item> I = new Catalog().getAllItems().iterator() 
                                ; I.hasNext() 
                                ; ) {
                            Item item = I.next(); 
                    %>

                    <tr>
                        <td><%= item.getName() %></td>
                        <td><%= item.getDescription() %></td>
                        <td><%= item.getFormattedPrice() %></td>
                        <td><button onclick="addToCart('<%= item.getCode() %>')">Add</button></td>
                        <td><button onclick="removeFromCart('<%= item.getCode() %>')">Remove</button></td>
                    </tr>

                    <% } 
                    %>
                </tbody>
            </table>

        <!-- Right side: Display total cost -->
        <div style = "position: absolute; top: 0px; right: 0px; width: 250px">
            <h2>Cart Contents</h2>
            <ul id="contents"></ul>
            Total cost: <span id="total">$0.00</span>
        </div>
    </body>
</html>
