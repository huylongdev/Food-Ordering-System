<%@ page contentType="text/html" pageEncoding="UTF-8" import="model.*,java.util.*,util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Foodie-Food</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
        <link rel="stylesheet" href="./assets/font/themify-icons/themify-icons.css"/>
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/css/header-footer.css">
        <link href="./assets/css/order-history.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="./assets/js/order-history.js"></script>
    </head>
    <body>
        <%@ include file="/include/header.jsp" %>
        <div>
            <br><br>
            <div class="center container cart">
                <h1>Order History</h1>
                <c:forEach var="order" items="${orderList}">
                    <div class='order-inf'><p></p></div>
                    <form name="order" action="checkout" method="get">
                        <div class="cart-products">
                            <div class="cart-thead">
                                <div style="width: 6%"></div>
                                <div style="width: 21%;text-align: left;">ID: ${order.getOrder().getOrderId()}</div>
                                <div style="width: 21%;text-align: left;padding-left: 5px;">Products</div>
                                <div id="head2" style="width: 21%"><span>Price</span></div>
                                <div style="width: 16%">Quantity</div>
                                <div id="head3" style="width: 13%;text-align: right!important;">Amount</div>
                            </div>
                            <div class="cart-tbody">
                                <c:forEach var="item" items="${order.getOrderItem()}">
                                    <div class="cart-item">
                                        <tr>
                                        <div style="width: 6%"></div>
                                        <div style="width: 6%;text-align: left;">
                                            <img width="120" height="auto" alt="Product Image" src="${item.getImg()}"/>
                                        </div>
                                        <div style="width: 6%"></div>
                                        <div id="col1" style="width: 30%;" class="a-center cart2">
                                            <h2 class="product-name" title="${item.getProduct().getName()}">
                                                <a href="./food-detail?productId=${item.getProduct().getProductId()}">${item.getProduct().getName()}</a>
                                            </h2>
                                        </div>
                                        <div id="col2" style="width: 21%;">
                                            <span class="price">${item.getProduct().getPrice()}</span>
                                        </div>
                                        <div style="width: 16%">
                                            <div class="number-input">
                                                <input name="isSelected" type="hidden" value="${item.getProduct().getProductId()}">
                                                <input type="number" id="${item.getProduct().getProductId()}" name="quantity_${item.getProduct().getProductId()}" class="quantity" value="${item.getQuantity()}" min="1" max="10" onchange="updateAmount(this)">
                                            </div>
                                        </div>
                                        <div id="col3" style="width: 13%; text-align: right!important;align-items: flex-end; padding-right: 0;">
                                            <span class="amount"></span>
                                        </div>
                                        </tr>
                                    </div>
                                </c:forEach>
                                <input type="hidden" id="userID" name="userID" value="${user.getUserID()}">
                            </div>
                            <table class="total-table">
                                <tr>
                                    <td style="width: 5%"></td>
                                    <td class="table-left">Payment Status: <b>${order.getOrder().getStatus().toUpperCase()}</b><br>Order Status: <b>${order.getOrder().getDeliveryStatus().toUpperCase()}</b></td>
                                    <td class="table-center">Delivery Address: ${order.getOrder().getAddress()}</td>
                                    <td class="a-right"><span class="total_tt">Total:</span>
                                        <span class="totals_price">${FormatString.formatCurrency(order.getOrder().getTotalAmount())}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 5%"></td>
                                    <td class="table-left">Date: ${order.getOrder().getCreatedDate()} <br>Time Pickup: <b><fmt:formatDate value="${order.getOrder().getTimePickup()}" pattern="HH:mm:ss" /></b></td>
                                    <td class="table-center">"Thank you for your order! We hope you enjoy your meal!"</td>
                                    <td class="a-right">
                                        <c:choose>
                                            <c:when test="${order.getOrder().getDeliveryStatus() == 'PENDING'}">
                                                <a href="#" class='cancel-order' onclick="submitCancelOrderForm(${order.getOrder().getOrderId()});">Cancel Order</a>
                                            </c:when>
                                            <c:otherwise>
                                                <!-- Có thể thêm thông báo hoặc nội dung khác ở đây nếu cần -->
                                            </c:otherwise>
                                        </c:choose>
                                        <button type="submit">Buy Again</button>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form>
                    <!-- Move cancel order form here and give each order a unique ID -->
                    <form id="cancelOrderForm_${order.getOrder().getOrderId()}" action="order-history" method="post" style="display: none;">
                        <input type="hidden" name="orderId" value="${order.getOrder().getOrderId()}">
                    </form>
                </c:forEach>
            </div>
        </div>
        <%@ include file="/include/footer.jsp" %>
        <script>
            function submitCancelOrderForm(orderId) {
                var confirmation = confirm("Are you sure you want to cancel this order?");
                if (confirmation) {
                    document.getElementById("cancelOrderForm_" + orderId).submit();
                }
            }
        </script>
        <script src="js/Jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
