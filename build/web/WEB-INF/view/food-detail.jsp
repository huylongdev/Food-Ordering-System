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
        <link rel="stylesheet" href="./assets/css/header-footer.css">
        <!--<link rel="stylesheet" href="./assets/css/blog.css" />-->
        <link href="./assets/css/product.css" rel="stylesheet">
        <script src="index.js"></script>
    </head>
    <body>

        <%@ include file="/include/header.jsp" %>



        <div class ="details-container row">
            <div class = "book-img col-12 col-sm-6 col-md-6 col-lg-5" >
                <!--<img src="https://i.pinimg.com/564x/76/19/ef/7619ef4dfcf7382aab410d57e796ffbf.jpg" alt=image" />-->
                <div id="carouselExampleIndicators" class="carousel slide">
                    <div class="carousel-indicators">
                        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                    </div>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="https://i.pinimg.com/564x/76/19/ef/7619ef4dfcf7382aab410d57e796ffbf.jpg" class="d-block w-100" alt="...">
                        </div>
                        <div class="carousel-item">
                            <img src="https://i.pinimg.com/474x/78/c4/33/78c433eb22a7fb53e31df6150ca867b2.jpg" class="d-block w-100" alt="...">
                        </div>
                        <div class="carousel-item">
                            <img src="https://i.pinimg.com/564x/c6/5b/37/c65b37a8d0f002c9ff17f6d13b58de67.jpg" class="d-block w-100" alt="...">
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>

            </div>

            <div id ="details"class ="col-12 col-sm-6 col-md-6 col-lg-7">
                <div class="product-details ">
                    <h1 class="title-product">Salmon Rice Bowls</h1>
                    <div class="group-status">
                        <p><b>2500 </b>orders</p>
                        <p><b>Category: </b><a href="./all-book?category=rice" title="Rice">Rice</a></p>
                    </div>
                    <h4 class = "price">
                        <c:out value="79.000 VND" />
                    </h4>
                    <p>Delicately grilled salmon fillet served with a variety of roasted vegetables, drizzled with a tangy lemon butter sauce for a flavorful experience.</p>
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
                            <h4 class="status">Foodie </h4>
                            <c:if test="${sessionScope.role != 'admin'}">
                                <button type ="submit" id ="cart-btn">Add to cart</button>
                                <button id ="fav"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314"/>
</svg></button>
                            </c:if>
                        </div>

                    </form>

                    <!--<div class="rating " data-rating="3"></div>-->
                    <!--<p id = "ratingStatus"></p>-->
                    <div class="rating " data-rating="3"></div>
                    <div class ="rating-border"></div>
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

        </div> 

        <%@ include file="/include/footer.jsp" %>


        <script src="js/Jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"
        ></script>
    </body>
</html>