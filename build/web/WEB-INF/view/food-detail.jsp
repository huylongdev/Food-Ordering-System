<%-- 
    Document   : food-detail
    Created on : Oct 3, 2024, 1:29:13 AM
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
        <link
            rel="stylesheet"
            href="./assets/font/themify-icons/themify-icons.css"
            />
        <script src="/js/blog.js"></script>
        <link rel="stylesheet" href="./assets/css/style.css">
        <!--<link rel="stylesheet" href="./assets/css/blog.css" />-->
        <link href="product.css" rel="stylesheet">
        <script src="index.js"></script>
    </head>
    <body>

        <%@ include file="/include/header.jsp" %>

        
        
            <div class ="details-container row">
                <div class = "book-img col-12 col-sm-5 col-md-5 col-lg-4" >
                    <img src="https://i.pinimg.com/564x/76/19/ef/7619ef4dfcf7382aab410d57e796ffbf.jpg" alt=image" />

                </div>

                <div id ="details"class ="col-12 col-sm-7 col-md-7 col-lg-8">
                    <div class="product-details ">
                        <h1 class="title-product">Salmon Rice Bowls</h1>
                        <div class="group-status">
                            <p><b>Purchase: </b>2500</p>
                            <p><b>Category: </b><a href="./all-book?category=rice" title="Rice">Rice</a></p>
                        </div>
                        <h4 class = "price">
                            <c:out value="79.000 VND" />
                        </h4>
                        <form name = "addCart" action = "cart" method ="post">
                            <div>
                                <div class = "quantity-label">Quantity</div>
                                <div class="number-input">
                                    <button type="button"  onclick="decrement(this)">-</button>
                                    <input type="number" id ="${book.getId()}" name ="quantity" class ="quantity" value="1" min="1" max="10"onchange="updateAmount(this)">
                                    <button  type="button" onclick="increment(this)">+</button>
                                    <input type = "hidden" name ="isAdd" value = "true">
                                    <input type = "hidden" name ="bookID" value = "bID">
                                    <input type = "hidden" name ="userID" value = "uID">
                                </div>
                            </div>
                            <div class ="add-cart-btn">
                                <h4 class="status">New / Hardback: ${FormatString.formatCurrency(book.getPrice())}</h4>
                                <c:if test="${sessionScope.role != 'admin'}">
                                    <button id ="cart-btn">Add to cart</button>
                                </c:if>
                            </div>
                        </form>

                        <div class="rating " data-rating="3"></div>
                        <!--<p id = "ratingStatus"></p>-->
                        <div class ="rating-border"></div>
                    </div>
                </div>



            </div> 
            <div class ="product-descript" >
                <div class ="product-tab">
                    <h4>DESCRIPTION</h4>
                </div>
                <div>
                    <p>These easy Salmon Rice Bowls have become a healthy and delicious staple for both lunch and dinner! They're quick and easy and truly packed with fresh flavors. If you saw those viral TikTok salmon bowls, I can confirm that this recipe is even better!</p>
                </div>
            </div>
            <div class ="product-descript" >
                <div class ="product-tab">
                    <h4>REVIEW</h4>
                </div>
                <form action="detail" method="POST">
                    <div class ="rate-product">
                        <label for="rating">Rate:</label>
                        <select name="rating" id="rating">
                            <option value="1">⭐</option>
                            <option value="2">⭐ ⭐</option>
                            <option value="3">⭐ ⭐ ⭐</option>
                            <option value="4">⭐ ⭐ ⭐ ⭐</option>
                            <option value="5">⭐ ⭐ ⭐ ⭐ ⭐</option>
                        </select>
                    </div>
                    <div class ="review-content">
                        <input type = "text" name = "comment" placeholder="Enter content...">
                    </div>

                    <c:if test="${sessionScope.role != null}">
                        <input type = "hidden" name ="bookID" value = "bookID">
                        <input type = "hidden" name ="userID" value = "userID">
                        <div class ="center">
                            <!--<button type="submit">Send</button></div>-->
                        </c:if>
                </form>
            </div>
        
        
        <%@ include file="/include/footer.jsp" %>


        <script src="js/Jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
        

    </body>
</html>
