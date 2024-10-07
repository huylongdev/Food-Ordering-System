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
                            <div class="icon">
                                <i class="ti-shopping-cart"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
        </div>

        <!-- PAGE INFO -->
        <div id="page-info">
            <div class="page-title">Food/Drink</div>
            <div class="page-info-more">
                <a href="./index.html">Home</a>
                <a style="border-left: 1px solid #e8e8ea" href="#"
                   >Food/Drink Management</a
                >
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

                <h3>Distance, KM</h3>
                <input type="range" min="0" max="50" value="40" />

                <h3>Price, $</h3>
                <input type="range" min="20" max="150" value="50" />

                <button class="filter-btn">Filter</button>
            </aside>

            <main class="restaurant-section">
                <div class="header">
                    <div class="restaurant-search">
                        <input type="text" placeholder="Search for a food..." />
                        <button>Search</button>
                    </div>
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
                                <img src="./assets/img/nulldataimg.png" alt="${product.getName()}" />
                                <div class="restaurant-info">
                                    <span class="rating">${product.getRating()}</span>
                                    <h4>${product.getName()}</h4>
                                    <p>${product.getPrice()} $</p>
                                    <div class="restaurant-more-info">
                                        <p>${product.getDescription()}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
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
