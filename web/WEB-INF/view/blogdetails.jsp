<%-- 
    Document   : blogdetails
    Created on : Oct 2, 2024, 1:55:11 PM
    Author     : phuct
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
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
                        </div>
                    </div>
                </div>
            </nav>
        </div>

        <!-- PAGE INFO -->
        <div id="page-info">
            <div class="page-title">Foodie Community Blog</div>
            <div class="page-info-more">
                <a href="/OrderingSystem/">Home</a>
                <a style="border-left: 1px solid #e8e8ea" href="#">Post Management</a>
            </div>
        </div>

        <!-- BLOG DETAILS -->
        <div id="blog-details">
            <c:if test="${not empty post}">
                <div class="blog-details-title">
                    <h2>${post.heading}</h2>
                    <div class="dropdown-container">
                        <i class="ti-more-alt" id="more-options"></i>
                        <div class="dropdown-menu" id="dropdown-menu" style="display:none;">
                            <a href="#" id="edit"><i class="ti-pencil"> Edit</i></a>
                            <a href="#" id="delete" onclick="confirmDelete(${postId})"><i class="ti-trash"> Delete</i></a>
                        </div>
                    </div>

                    <!-- Hidden form for deletion -->
                    <form id="deleteForm" action="blogdetails" method="post" style="display:none;">
                        <input type="hidden" name="postID" id="postID">
                    </form>
                </div>
                <div class="blog-details-author">
                    <img src="${avtURL}" alt="Author Avatar" class="details-avatar" />
                    <p class="details-name">${fullName}</p>
                    <div class="blog-details-date">${post.createdDate}</div>
                </div>
                <div class="blog-content">
                    <img src="${post.imgURL}" alt="Post Image" class="blog-content-img" />
                    <p>${post.content}</p>
                </div>
            </c:if>
            <c:if test="${empty post}">
                <p>Bài viết không tồn tại.</p>
            </c:if>
        </div>

        <!-- COOMMENT -->
        <div class="comment-container">
            <h2>Comment Section</h2>
            <div class="comment-box">
                <textarea id="commentInput" rows="4" placeholder="Write a comment..."></textarea><br><br>
                <button onclick="addComment()">Post Comment</button>
            </div>
            <div class="comments-display" id="commentsDisplay"></div>
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
        <script>
            function addComment() {
                // Get the comment input value
                var comment = document.getElementById("commentInput").value;

                // Check if the input is not empty
                if (comment.trim() !== "") {
                    // Create a new div for the comment
                    var newComment = document.createElement("div");
                    newComment.classList.add("comment");
                    newComment.innerText = comment;

                    // Append the new comment to the display area
                    document.getElementById("commentsDisplay").appendChild(newComment);

                    // Clear the input area
                    document.getElementById("commentInput").value = "";
                } else {
                    alert("Please write a comment before posting!");
                }
            }

            // Toggle dropdown visibility
            document.getElementById("more-options").addEventListener("click", function (event) {
                event.stopPropagation(); // Prevent window click from firing
                var dropdown = document.getElementById("dropdown-menu");
                dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
            });

            // Hide dropdown when clicking outside
            window.addEventListener("click", function (event) {
                var dropdown = document.getElementById("dropdown-menu");
                if (dropdown.style.display === "block" && !event.target.closest('.dropdown-container')) {
                    dropdown.style.display = "none";
                }
            });

            // Function to confirm deletion
            function confirmDelete(postID) {
                const confirmAction = confirm("Are you sure you want to delete this post?");
                if (confirmAction) {
                    // If confirmed, submit the form
                    document.getElementById('postID').value = postID;
                    document.getElementById('deleteForm').submit();
                }
            }
        </script>
    </body>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>
