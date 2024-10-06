<%-- 
    Document   : shop
    Created on : Oct 2, 2024, 1:53:59 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="model.*,java.util.*,util.*" %>

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
        <script src="./assets/js/shop.js"></script>
        <link rel="stylesheet" href="./assets/css/header-footer.css">
        <!--<link rel="stylesheet" href="./assets/css/blog.css" />-->
        <link href="./assets/css/shop.css" rel="stylesheet">
        <link href="./assets/css/restaurant.css" rel="stylesheet">
    </head>
    <body>

        <%@ include file="/include/header.jsp" %>





        <!-- PAGE INFO -->

        <section class="banner jumbotron text-center" style="background-image: url('${shop.getShopImage()}');">
            <div class="page-title">${shop.getName()}</div>
            <p>${shop.getDescription()}</p>
        </section>

        <div id="page-info">

            <div class="page-info-more">
                <a href="./">Home</a>
                <p>Time Open: ${shop.getTimeOpen()}</p>
                <p>Time Close: ${shop.getTimeClose()}</p>

            </div>
        </div>

        <!-- FOOD CONTENT -->
        <div class="container">
            <aside class="filter-section">
                <h3>Categories</h3>
                <ul>
                    <li><input type="checkbox" /> Pizza (42)</li>
                    <li><input type="checkbox" /> Sushi (35)</li>
                    <li><input type="checkbox" checked /> Burgers (28)</li>
                    <li><input type="checkbox" /> Vegetarian (23)</li>
                    <li><input type="checkbox" /> Asian (15)</li>
                    <li><input type="checkbox" /> Bakery (8)</li>
                </ul>

                <h3>Rating</h3>
                <select>
                    <option>9+</option>
                    <option>8+</option>
                    <option>7+</option>
                </select>



                <button class="filter-btn">Filter</button>
            </aside>

            <main class="restaurant-section">
                <div class="header">
                    <div class="restaurant-search">
                        <input type="text" placeholder="Search for a food..." />
                        <button>Search</button>
                    </div>
                    <c:if test="${sessionScope.user != null && sessionScope.user.shopID == shop.shopID}">
                    <button class ="sticky-button" 
                            data-shopId="${user.getShopID()}"
                            onclick = "showAddProductOverlay();addButton(this)" >Add product</button>
                    </c:if>
                    <select>
                        <option>Sort by Popularity</option>
                        <option>Sort by Rating</option>
                        <option>Sort by Price</option>
                    </select>
                </div>
                <div class="restaurant-list">



                    <c:if test="${not empty productList}">
                        <c:forEach var="product" items="${productList}">
                            <div class="restaurant-card">
                                <div class = "img-wrapper">
                                    <img src="${product.getImgURL()}" alt="${product.getProduct().getName()}" />
                                    <c:if test="${sessionScope.user != null && sessionScope.user.shopID == shop.shopID}">
                                    <div class="action-buttons">
                                        <button type="button" class="btn-update"
                                                data-shopId="3" 
                                                data-productId="${product.getProduct().getProductId()}"
                                                data-name="${product.getProduct().getName()}"
                                                data-price="${product.getProduct().getPrice()}"
                                                data-category="${product.getProduct().getCategoryId()}"
                                                data-description="${product.getProduct().getDescription()}"
                                                data-image="${product.getImgURL()}"
                                                onclick="showUpdateProductOverlay(); updateButton(this)">Update</button>
                                        <form action="restaurant-detail" method="post">
                                            <input type="hidden" name="mt" value="delete">
                                            <input type="hidden" name="shopID" value="${user.getShopID()}">
                                            <input type="hidden" name="productId" value="${product.getProduct().getProductId()}">
                                            <button type="submit" class="btn-delete">Delete</button>
                                        </form>
                                    </div>
                                    </c:if>
                                </div>

                                <a href ="./food-detail?productId=${product.getProduct().getProductId()}">
                                    <div class="restaurant-info">
                                        <span class="rating">${product.getProduct().getRating()}</span>
                                        <h4>${product.getProduct().getName()}</h4>
                                        <p>${product.getProduct().getPrice()} $</p>
                                        <div class="restaurant-more-info">
                                            <p>${product.getProduct().getDescription()}</p>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
        </div>

        <div id ="add-product-overlay" class ="overlay center">
            <div class="overlay-content">
                <span class="close-btn" onclick="hideAddProductOverlay()">&times;</span></br>
                <form name = "add-product" action = "restaurant-detail" method = "post" enctype="multipart/form-data">
                    <div class = "add-product-form">
                        <h1>Add product</h1>
                        <input type="hidden" name="mt" value="add">
                        <input id ="overlayShopID" type ="hidden" name ="shopID" value ="" >
                        <label for="title">Name</label>
                        <input type="text" id="overlayTitle" name="name" required>
                        <label for="price">Price:</label>
                        <input type="number" id="overlayPrice" name="price" required>
                        <label for="category">Category:</label>
                        <select id="category" name="category">
                            <option value="1">Food </option>
                            <option value="2">Drink</option>
                            <option value="3">Dessert</option>
                            <option value="4">Snacks</option>
                            <option value="5">Beverages</option>
                        </select><br>
                        <label for="description">Description:</label>
                        <input type="text"id="overlayDescription" name="description" required>
                        <label for="imgs">Images</label>
                        <input type="file" id="img" name="img" accept="image/*" multiple required><br>
                        <button type ="submit" style="background-color: #b0c4de" class="btn">Save</button>
                    </div>
                </form>
            </div> 
        </div>
        <!--==================================================================-->

        <div id="update-product-overlay" class="overlay center">
            <div class="overlay-content">
                <span class="close-btn" onclick="hideUpdateProductOverlay()">&times;</span><br>
                <form name="update-product" action="restaurant-detail" method="post" enctype="multipart/form-data">
                    <div class="add-product-form">
                        <h1>Update product</h1>
                        <input type="hidden" name="mt" value="update">
                        <input id="shopID" type="hidden" name="shopID" value=""required>
                        <input id="productID" type="hidden" name="productID" value=""required>

                        <label for="title">Name</label>
                        <input type="text" id="name" name="name" value=""required>

                        <label for="price">Price:</label>
                        <input type="number" id="price" name="price" value=""required>

                        <label for="category">Category:</label>
                        <select id="category" name="category">
                            <option value="1">Food</option>
                            <option value="2">Drink</option>
                            <option value="3">Dessert</option>
                            <option value="4">Snacks</option>
                            <option value="5">Beverages</option>
                        </select><br>

                        <label for="description">Description:</label>
                        <input type="text" id="description" name="description" value=""required>

                        <label for="imgs">Images</label>
                        <input type="file" id="image" name="img" accept="image/*" multiple required><br>

                        <button type="submit" style="background-color: #b0c4de" class="btn">Save</button>
                    </div>
                </form>
            </div>
        </div>


        <div class="pagination">
            <button>&laquo;</button>
            <button class="active">1</button>
            <button>2</button>
            <button>3</button>
            <button>4</button>
            <button>&raquo;</button>
        </div>

        <img src="./assets/img/adblog.svg" alt="" class="img-ad">
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