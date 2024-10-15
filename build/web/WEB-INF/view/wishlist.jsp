<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Foodie - Wishlist</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="./assets/font/themify-icons/themify-icons.css" />
        <link rel="stylesheet" href="./assets/css/style.css">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="./assets/css/wishlist.css" rel="stylesheet">
    <link rel="stylesheet" href="./assets/css/header-footer.css">

    <link rel="stylesheet" href="./assets/css/wishlist.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<%@ include file="/include/header.jsp" %>

<div class="container my-5 center">
    <h1>Your Wishlist</h1>
    <div class="table-wishlist">
        <table>
            <thead>
                <tr>
                    <th class = 'product'>Product</th>
                    <th>Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items='${cart}'>
                    <tr>
                        <td class = 'product'>
                            <img class="img-product" alt="${item.getProduct().getName()}" src="${item.getImgURL()}" />
                            <span class="name-product">${item.getProduct().getName()}</span>
                        </td>
                        <td class="price">${item.getProduct().getPrice()}</td>
                        <td>
                            <button class="round-black-btn add-to-cart-btn" onclick="addToCart('${item.getProduct().getProductId()}')">Add to Cart</button>
                            <button class="round-black-btn delete-btn" onclick="deleteItem('${item.getProduct().getProductId()}')">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <p>${cartStatus}</p>
</div>

<%@ include file="/include/footer.jsp" %>

<script>
    function addToCart(productId) {
        $.ajax({
            url: '/your-add-to-cart-url', // Replace with your add to cart URL
            type: 'POST',
            data: { productId: productId },
            success: function (response) {
                alert("Product added to cart successfully!");
            },
            error: function (xhr, status, error) {
                alert("An error occurred while adding the item to the cart.");
            }
        });
    }

    function deleteItem(productId) {
        if (confirm("Are you sure you want to delete this item from your wishlist?")) {
            $.ajax({
                url: '/your-delete-url', // Replace with your delete URL
                type: 'POST',
                data: { productId: productId },
                success: function (response) {
                    location.reload(); // Reload the page after deletion
                },
                error: function (xhr, status, error) {
                    alert("An error occurred while deleting the item.");
                }
            });
        }
    }
</script>

</body>
</html>
