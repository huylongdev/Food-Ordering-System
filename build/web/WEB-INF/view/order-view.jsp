<%-- 
    Document   : food-detail
    Created on : Oct 3, 2024, 1:29:13 AM
    Author     : LENOVO
--%>
<%@page import="model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Account user = (Account) request.getSession().getAttribute("user");
    if(user != null){
    request.setAttribute("user", user);
    }
%>
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
        <link
            rel="stylesheet"
            href="./assets/font/themify-icons/themify-icons.css"
            />
        <link rel="stylesheet" href="./assets/css/style.css">
        <!--<link rel="stylesheet" href="./assets/css/blog.css" />-->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="./assets/css/cart.css" rel="stylesheet">
        <!--<link href="./assets/css/hs-test.css" rel="stylesheet">-->
        <script src="./assets/js/order.js"></script>
        <script>document.addEventListener('DOMContentLoaded', function () {

                var quantityInputs = document.querySelectorAll('.quantity');
                quantityInputs.forEach(function (input) {
                    updateAmount(input);
                });
            });</script>
    </head>
    <body>

        <%@ include file="/include/header.jsp" %>





        <div>

            <br><br>
            <div  class ="center  container cart">
                <h1>Your Order</h1>

                <!--                tạo 1 địa chỉ hidden để nộp form hoặc getSession như ban đầu-->
                <h5 style="color: #FF6B6B;">Address: ${user.address}</h3>
                    <form id ="myForm" name= 'process' action='order' method = 'GET'>

                        <div class = "cart-products">

                            <div class="cart-thead"><div style="width: 6%" ></div><div style="width: 21%;text-align: left; ">Product</div>
                                
                                <div style="width: 21%;text-align: left;padding-left: 5px;"></div><div id ="head2" style="width: 21%"><span>Price</span></div>
                                <div style="width: 16%">Quantity</div><div id ="head3" style="width: 13%;text-align: right!important;" >Amount</div></div>




                            <div class="cart-tbody">

                                <c:forEach var ="item" items='${orderItems}'>
                                    <div class="cart-item">
                                        <tr>
                                        <div style="width: 6%" ><input name="isSelected" type="hidden" value ="${item.getProduct().getProductId()}" ></div>
                                        
                                        <div style="width: 6%" ></div>
                                        <div id ="col1" style="width: 17%;align-items: flex-start;" class="a-center cart2"><h2 class="product-name" title="${item.getProduct().getName()}"> <a href="./detail?bookId=ID">Salmon Rice Bowls</a><span class="variant-title">Good / Paperback</span> </h2></div>
                                        
                                        <div id ="col2" style="width: 21%;" ><span class ="price">${item.getProduct().getPrice()}</span></div>
                                        <div style="width: 16%">
                                            <div class="number-input">
                                                <!--                                            <button type="button" onclick="decrement(this)">-</button>-->
                                                <input type='number' id='${item.getProduct().getProductId()}' name ='quantity_${item.getProduct().getProductId()}' class ='quantity' value='${item.getQuantity()}' min='1' max='10'onchange='updateAmount(this)'>
                                                <!--                                            <button type="button" onclick="increment(this)">+</button>-->
                                            </div></div>
                                        <div id = "col3"style="width: 13%;text-align: right!important;align-items: flex-end;padding-right: 0" >
                                            <span class="amount">${item.getTotalPrice()}</span>
                                        </div>

                                        </tr>
                                    </div>
                                </c:forEach>

                                <input  type="hidden" id="mt" name="mt" value ="display" >
                                <input  type="hidden" id="userID" name="userID" value ="${user.getUserID()}" >

                            </div>

                            <table class = "total-table">
                                <tr>
                                    <td class = "a-right"><span class="total_tt">Total:</span> 
                                        <span class="totals_price"></span></td></tr>
                            </table>
                        </div>


                        <div class="container text-center mt-5">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        <input type="text" name="voucher"style="display:inline-block ;width: 30%;"/>
                                        <span class="btn btn-primary " style="margin-left: 10px;width: 30%;" id="voucher-btn" type='submit' onclick="submitForm('method1')">Apply voucher</a>
                                    </div>
                                    <div class="row">

                                    </div>

                                </div>
                                <div class="col">
                                    <select class="form-select" aria-label="Default select example">
                                        <option selected>Delivery Option</option>
                                        <option value="1">Pick up</option>
                                        <option value="2">Shipping</option>

                                    </select>
                                </div>
                            </div>

                            <div class="row mt-5">
                                <div class="col">Chọn ngày giờ nếu bạn pickup</div>
                                <div class="col" style="background-color: #F7F6F2">
                                    <p>Please select Payment Method</h5>
                                    
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1" value="option1" checked>
                                            <label class="form-check-label" for="byCash">
                                                By Cash
                                            </label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2" value="option2">
                                            <label class="form-check-label" for="VNPay">
                                                VN Pay
                                            </label>
                                        </div>
                                    
                                </div>
                            </div>
                        </div>       
                                
                        <tr><button id="delete-btn" type='submit' onclick="submitForm('method2')">Back to Cart</button>
                        <button type="submit" onclick="submitForm('method3')">Place an order</button></tr>
                    </form>
                    <p>${cartStatus}</p>
            </div>



        </div>

        <%@ include file="/include/footer.jsp" %>


        <script src="js/Jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>


    </body>
</html>