<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="model.Account" %>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Reid - Checkout Page</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <link rel="stylesheet" href="assets/css/plugins.css">
        <link rel="stylesheet" href="./assets/css/style.css" />
        <link rel="stylesheet" href="./assets/css/blog.css" />
        <link rel="stylesheet" href="./assets/css/cart.css">
        <link rel="stylesheet" href="./assets/css/header-footer.css">
        <link rel="stylesheet" href="./assets/css/checkout.css" />
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
        <link
            rel="stylesheet"
            href="./assets/font/themify-icons/themify-icons.css"
            />
    </head>

    <body>

        <%@ include file="/include/header.jsp" %>

        <div class="off_canvars_overlay"></div>
        <div class="breadcrumbs_area other_bread">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="breadcrumb_content">
                            <ul>
                                <li><a href="/OrderingSystem/">Home</a></li>
                                <li>/</li>
                                <li>Checkout</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="Checkout_section" id="accordion">
            <div class="container">
                <div class="checkout_form">
                    <form action="checkout" method="POST">
                        <div class="row">
                            <div class="col-lg-5 col-md-5">
                                <h3>Chi tiết đơn hàng</h3>
                                <div class="row">
                                    <div class="col-lg-12 mb-20">
                                        <label>Tên khách hàng<span>*</span></label>
                                        <c:if test="${not empty user}">
                                            <input readonly value="${user.getFullName()}" type="text">
                                        </c:if>
                                        <c:if test="${empty user}">
                                            <input readonly value="Người dùng không xác định" type="text">
                                        </c:if>
                                    </div>
                                    <div class="col-lg-12 mb-20">
                                        <label>Email <span>*</span></label>
                                        <c:if test="${not empty user}">
                                            <input readonly value="${user.getEmail()}" type="text">
                                        </c:if>
                                        <c:if test="${empty user}">
                                            <input readonly value="Người dùng không xác định" type="text">
                                        </c:if>
                                    </div>
                                    <div class="col-lg-12 mb-20">
                                        <label>Địa chỉ<span>*</span></label>
                                        <input required name="address" type="text">
                                    </div>
                                    <div class="col-lg-12 mb-20">
                                        <label>Số điện thoại<span>*</span></label>
                                        <input required name="phone" type="tel" pattern="[0-9]{10}" title="Nhập số điện thoại 10 số" maxlength="10">
                                    </div>
                                </div>
                                <h4>Phương thức thanh toán</h4>
                                <div class="payment_method">
                                    <div>
                                        <input type="radio" name="payment_method" id="vnpay" value="vnpay">
                                        <label for="vnpay">VNPAY</label>
                                    </div>
                                    <div>
                                        <input type="radio" name="payment_method" id="cod" value="cod">
                                        <label for="cod">Thanh toán khi nhận hàng</label>
                                    </div>
                                </div>
                                <div class="col-lg-12 mb-20">
                                    <h4>Phương thức nhận hàng</h4>
                                    <div class="shipping_method">
                                        <div>
                                            <input type="radio" name="shipping_method" id="home_delivery" value="home_delivery" required>
                                            <label for="home_delivery">Giao hàng tận nơi</label>
                                        </div>
                                        <div>
                                            <input type="radio" name="shipping_method" id="pickup" value="pickup" required>
                                            <label for="pickup">Pick up tại cửa hàng</label>
                                        </div>
                                    </div>
                                </div>

                                <!-- Time Pickup Field -->
                                <div class="col-lg-12 mb-20" id="pickup_time_field" style="display:none;">
                                    <label>Thời gian pick up<span>*</span></label>
                                    <input name="pickup_time" type="datetime-local" id="pickup_time">
                                </div>

                                <button class="btn btn-primary" type="submit">Hoàn tất đơn hàng</button>

                            </div>

                            <div class="col-lg-7 col-md-7">
                                <h3>Giỏ hàng</h3>
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Sản phẩm</th>
                                            <th>Giá</th>
                                            <th>Số lượng</th>
                                            <th>Tổng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${cart}">
                                            <tr>
                                                <!-- Assuming CartItemDTO has a 'product' with a 'name' and 'price' property -->
                                                <td>${item.product.name}</td>
                                                <td>${item.product.price}</td>
                                                <td>${item.quantity}</td>
                                                <td>${item.product.price * item.quantity}</td>
                                            </tr>
                                        </c:forEach>
                                        <tr>
                                            <td colspan="3">Tổng cộng</td>
                                            <td>
                                                <c:set var="total" value="0" />
                                                <c:forEach var="item" items="${cart}">
                                                    <c:set var="total" value="${total + (item.product.price * item.quantity)}" />
                                                </c:forEach>
                                                ${total}
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%@ include file="/include/footer.jsp" %>  
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const pickupRadio = document.getElementById('pickup');
                const homeDeliveryRadio = document.getElementById('home_delivery');
                const pickupTimeField = document.getElementById('pickup_time_field');

                // Lắng nghe sự thay đổi của radio buttons
                pickupRadio.addEventListener('change', function () {
                    if (this.checked) {
                        pickupTimeField.style.display = 'block'; // Hiển thị trường Time pickup
                        document.getElementById('pickup_time').setAttribute('required', 'required');
                    }
                });

                homeDeliveryRadio.addEventListener('change', function () {
                    if (this.checked) {
                        pickupTimeField.style.display = 'none'; // Ẩn trường Time pickup
                        document.getElementById('pickup_time').removeAttribute('required');
                    }
                });
            });
        </script>

    </body>

</html>
