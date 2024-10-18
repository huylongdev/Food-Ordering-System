<%-- 
    Document   : order-detail
    Created on : Oct 18, 2024, 7:44:40 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Foodie-Food</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"
        ></script>
        <link rel="stylesheet" href="./assets/font/themify-icons/themify-icons.css" />
        <link rel="stylesheet" href="./assets/css/style.css">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="./assets/css/order-detail.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="./assets/js/order-detail.js"></script>

        <script>
            // Hàm cập nhật số lượng và giá trị
            document.addEventListener('DOMContentLoaded', function () {
                var quantityInputs = document.querySelectorAll('.quantity');
                quantityInputs.forEach(function (input) {
                    updateAmount(input);
                });
            });
        </script>
    </head>
    <body>
        <%@ include file="/include/shop-header.jsp" %>
        <div>
            <br><br>
            <div class="center container order">
                <h4>${order.getDeliveryStatus()}</h4>
                <div class="order-products">
                    <div class="order-thead">
                        <div style="width: 6%"></div>
                        <div style="width: 21%;text-align: left;">Product</div>
                        <div style="width: 21%;text-align: left;padding-left: 5px;"></div>
                        <div id="head2" style="width: 21%"><span>Price</span></div>
                        <div style="width: 16%">Quantity</div>
                        <div id="head3" style="width: 13%;text-align: right!important;">Amount</div>
                    </div>
                    <div class="order-tbody">
                        <c:forEach var="item" items="${orderList}">
                            <div class="order-item">
                                <tr>
                                <div style="width: 6%">

                                </div>
                                <div style="width: 6%;text-align: left;">
                                    <img width="120" height="auto" alt="Salmon Rice Bowls" src="${item.getImgURL()}" />
                                </div>
                                <div style="width: 6%"></div>
                                <div id="col1" style="width: 30%;" class="a-center order2">
                                    <h2 class="product-name" title="${item.getProduct().getName()}">
                                        <a href="./food-detail?productId=${item.getProduct().getProductId()}">${item.getProduct().getName()}</a>
                                        <span class="variant-title">Food/ Drink</span>
                                    </h2>
                                </div>
                                <div id="col2" style="width: 21%;">
                                    <span class="price">${item.getProduct().getPrice()}</span>
                                </div>
                                <div style="width: 16%">
                                    <div class="number-input">

                                        <input type="number" id="${item.getProduct().getProductId()}" name="quantity_${item.getProduct().getProductId()}" class="quantity" value="${item.getQuantity()}" min="1" max="10" onchange="updateAmount(this)">

                                    </div>
                                </div>
                                <div id="col3" style="width: 13%; text-align: right!important;align-items: flex-end; padding-right: 0;">
                                    <span class="amount"></span>
                                </div>
                                </tr>
                            </div>
                        </c:forEach>

                    </div>
                    <table class="total-table">
                        <tr>
                            <td class="a-right"><span class="total_tt">Total:</span>
                                <span class="totals_price"></span>
                            </td>
                        </tr>
                    </table>
                    <div class = "button-group mt3">
                        
                        <c:choose>
                            <c:when test="${order.getDeliveryStatus() == 'PENDING'}">
                                <form method="post" action="order-detail">
                                    <input type="hidden" name="orderId" value="${order.orderId}" />
                                    <input type="hidden" name="action" value="prepare" />
                                    <button type="submit">Confirm Order</button>
                                </form>
                            </c:when>
                            <c:when test="${order.getDeliveryStatus() == 'PREPARING'}">
                                <form method="post" action="order-detail">
                                    <input type="hidden" name="orderId" value="${order.orderId}" />
                                    <input type="hidden" name="action" value="ready" />
                                    <button type="submit">Done Preparing</button>
                                </form>
                            </c:when>
                            <c:when test="${order.getDeliveryStatus() == 'READY' || order.deliveryStatus == 'SHIPPING'}">
                                <form method="post" action="order-detail">
                                    <input type="hidden" name="orderId" value="${order.orderId}" />
                                    <input type="hidden" name="action" value="complete" />
                                    <button type="submit">Complete Order</button>
                                </form>
                            </c:when>
                            <c:when test="${order.getDeliveryStatus() == 'COMPLETED'}">
                                <p>This order is already Completed!</p>
                            </c:when>
                            <c:otherwise>
                                <p>This order is already Canceled!</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

            </div>
        </div>
        <%@ include file="/include/footer.jsp" %>
        <script src="js/Jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
