<%-- 
    Document   : food
    Created on : Oct 2, 2024, 2:21:28 PM
    Author     : phuct
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%@ page import="model.Product" %>
<%@ page import="context.ProductDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%
    Account user = (Account) session.getAttribute("loggedUser");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Foodie Blog</title>
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
        <link rel="stylesheet" href="./assets/css/style.css" />
        <link rel="stylesheet" href="./assets/css/blog.css" />
        <link rel="stylesheet" href="./assets/css/blogdetails.css" />
        <link rel="stylesheet" href="./assets/css/restaurant.css" />
        <link rel="stylesheet" href="./assets/css/food.css" />
    </head>
    <body>
        <!-- HEADER -->
        <div id="header">
            <nav class="navbar navbar-expand-lg bg-body-tertiary">
                <div class="container-fluid">
                    <a class="navbar-brand" href="/OrderingSystem/">FOODIE</a>
                    <button
                        class="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup"
                        aria-controls="navbarNavAltMarkup"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                        >
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                        <div class="navbar-nav">
                            <a class="nav-link" aria-current="page" href="/OrderingSystem/">Home</a>
                            <a class="nav-link" href="/OrderingSystem/food">Food</a>
                            <a class="nav-link" href="/OrderingSystem/restaurant">Restaurant</a>
                            <a class="nav-link" href="/OrderingSystem/blog">Blog</a>
                        </div>
                        <div class="navbar__item">
                            <nav id="nav-bar">
                                <ul class="nav-list">
                                    <li class="navbar__item--login">
                                        <a href="<%= user != null ? "account" : "login" %>" class="nav-link">
                                            <c:choose>
                                                <c:when test="${user != null}">
                                                    <div class="user-dropdown">
                                                        <a style="text-decoration: none" href="/OrderingSystem/account">
                                                            <img
                                                                id="user-avatar"
                                                                class="img-responsive img-circle"
                                                                src="${user.getAvtImg()}"
                                                                onerror="this.onerror=null;this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';"
                                                                alt="Profile Picture"
                                                                />
                                                            <span id="user-name">${user.getUserName()}</span>
                                                        </a>
                                                        <div class="dropdown-content">
                                                            <a href="/OrderingSystem/account">Account</a>
                                                            <a href="/OrderingSystem/logout">Logout</a>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <span id="login-text">Login</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </li>
                                </ul>
                            </nav>

                            <a style="text-decoration: none" href = "./cart"><div class="icon">
                                    <i class="ti-shopping-cart"></i>
                                </div></a>
                            <a href = "./favourite">
                            <div class="icon">
                                <i class="ti-heart" style="margin: 0 10px 0 10px; padding:10px; font-size: 25px; border-radius: 25px;
                                   background-color: #ff6b6b; display: inline-block;color: white; text-align: center;"></i>
                            </div>
                        </a>                

                        </div>
                    </div>
                </div>
            </nav>
        </div>

        <!-- PAGE INFO -->
        <div id="page-info">
            <div class="page-title">Food/Drink</div>
            <div class="page-info-more">
                <a href="/OrderingSystem/">Home</a>
                <a style="border-left: 1px solid #e8e8ea" href="#"
                   >Food/Drink Management</a
                >
            </div>
        </div>

        <!-- FOOD CONTENT -->
        <div class="container">
            <aside class="filter-section">
                <h3>Categories</h3>
                <form action="filterproducts" method="post">
                    <ul>
                        <c:if test="${not empty categoryList}">
                            <c:forEach var="category" items="${categoryList}">
                                <li>
                                    <input type="checkbox" name="categoryIds" value="${category.categoryID}" /> ${category.getType()}
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>

                    <h3>Rating</h3>
                    <select name="rating">
                        <option value="None">None</option>
                        <option value="4.5">4.5+</option>
                        <option value="4.0">4.0+</option>
                        <option value="3.5">3.5+</option>
                    </select>

                    <h3>Sort by</h3>
                    <select name="sortBy">
                        <option value="none">None</option>
                        <option value="popularity">Sort by Popularity</option>
                        <option value="rating">Sort by Rating</option>
                        <option value="price">Sort by Price</option>
                    </select>
                    <div class="filter-button">
                        <button class="filter-btn" type="submit">Filter</button>
                        <a href="/OrderingSystem/food" class="unfilter-btn" >Delete Filter</a>
                    </div>
                </form>
            </aside>

            <main class="restaurant-section">
                <div class="header">
                    <div class="restaurant-search">
                        <form class="food-search-form" action="food" method="post">
                            <input type="text" name="keyword" placeholder="Enter product name..." required>
                            <button style="width: 30%" type="submit">Search</button>
                        </form>
                    </div>
                </div>

                <div class="restaurant-list">
                    <c:if test="${not empty productList}">
                        <c:forEach var="product" items="${productList}">
                            <div class="restaurant-card">
                                <div class = "img-wrapper">
                                    <img src="${product.getImgURL()}" alt="${product.getProduct().getName()}" />

                                </div>

                                    <a style="text-decoration: none" href ="./food-detail?productId=${product.getProduct().getProductId()}">
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

        <div class="pagination">
            <c:set var="currentPage" value="${currentPage}" />
            <c:set var="pageSize" value="${pageSize}" />
            <c:set var="totalProducts" value="${totalProducts}" />
            <c:set var="totalPages" value="${totalPages}" />

            <c:if test="${currentPage > 1}">
                <a href="?page=${currentPage - 1}">&laquo;</a>
            </c:if>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <span class="active">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <a href="?page=${currentPage + 1}">&raquo;</a>
            </c:if>
        </div>



    </main>
</div>

<!-- AD BLOG -->
<img src="./assets/img/adblog.svg" alt="" class="blog-ad" />

<!-- FOOTER -->
<footer id="footer">
    <div class="footer-content">
        <div class="footer-logo">
            <h2>FOODIE</h2>
            <div class="footer-social">
                <a class="icon-footer" href="#"><i class="ti-facebook"></i></a>
                <a class="icon-footer" href="#"><i class="ti-instagram"></i></a>
                <a class="icon-footer" href="#"><i class="ti-location-pin"></i></a>
            </div>
        </div>
        <div class="footer-menu">
            <h4 style="display: flex; justify-content: center">MENU</h4>
            <div class="menu-item">
                <ul>
                    <li><a href="#">About</a></li>
                    <li><a href="#">Restaurants</a></li>
                </ul>
                <ul>
                    <li><a href="#">Map</a></li>
                    <li><a href="#">Submit</a></li>
                </ul>
            </div>
        </div>
    </div>
</footer>
</body>
</html>
