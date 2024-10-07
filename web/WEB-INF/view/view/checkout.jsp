<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="model.Account" %>
<%
    Account user = (Account) session.getAttribute("loggedUser");
%>
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
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
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
                                    <input type="radio" name="payment_method" id="momo" value="momo" checked>
                                    <label for="momo">Momo</label>
                                </div>
                                <div>
                                    <input type="radio" name="payment_method" id="vnpay" value="vnpay">
                                    <label for="vnpay">VNPAY</label>
                                </div>
                                <div>
                                    <input type="radio" name="payment_method" id="cod" value="cod">
                                    <label for="cod">Thanh toán khi nhận hàng</label>
                                </div>
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
                                    <c:forEach var="item" items="${sessionScope.cart.items}">
                                        <tr>
                                            <td>${item.product.product_name}</td>
                                            <td>${item.product.price}</td>
                                            <td>${item.quantity}</td>
                                            <td>${item.product.price * item.quantity}</td>
                                        </tr>
                                    </c:forEach>
                                    <tr>
                                        <td colspan="3">Tổng cộng</td>
                                        <td>${sessionScope.cart.total}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="assets/js/vendor/jquery-3.6.0.min.js"></script>
    <script src="assets/js/plugins.js"></script>
    <script src="assets/js/main.js"></script>
</body>

</html>
