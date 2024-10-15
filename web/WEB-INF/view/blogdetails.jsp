<%-- 
    Document   : blogdetails
    Created on : Oct 2, 2024, 1:55:11 PM
    Author     : phuct
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%@ page import="model.Comment" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Account user = (Account) session.getAttribute("loggedUser");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Foodie Blog</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="./assets/font/themify-icons/themify-icons.css">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/css/blog.css">
        <link rel="stylesheet" href="./assets/css/modal.css">
        <link rel="stylesheet" href="./assets/css/blogdetails.css">
        <script src="./js/blog.js"></script>
    </head>
    <body>
        <!-- HEADER -->
        <div id="header">
            <nav class="navbar navbar-expand-lg bg-body-tertiary">
                <div class="container-fluid">
                    <a class="navbar-brand" href="/OrderingSystem/">FOODIE</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                        <div class="navbar-nav">
                            <a class="nav-link" href="/OrderingSystem/">Home</a>
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
                                                            <img id="user-avatar" class="img-responsive img-circle" src="${user.getAvtImg()}" onerror="this.onerror=null;this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';" alt="Profile Picture">
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
                            <a style="text-decoration: none" href="./cart">
                                <div class="icon"><i class="ti-shopping-cart"></i></div>
                            </a>
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
                    <c:if test="${user.userID == post.userID}">
                        <div class="dropdown-container">
                            <i class="ti-more-alt" id="more-options"></i>
                            <div class="dropdown-menu" id="dropdown-menu" style="display:none;">
                                <a id="openModalBtn"><i class="ti-pencil"> Edit</i></a>
                                <div id="myModal" class="modal">
                                    <div class="modal-content">
                                        <span class="close">&times;</span>
                                        <form action="editPost" method="POST" enctype="multipart/form-data">
                                            <h2>Edit Blog Post Id: ${postId}</h2>
                                            <label for="imgPost">Picture Cover:</label>
                                            <input name="imgPost" type="file" class="input-field" required><br>
                                            <label for="title">Heading:</label>
                                            <input name="title" type="text" required class="input-field" value="${post.heading}"><br>
                                            <label for="description">Description:</label>
                                            <textarea id="default" name="description">${post.content}</textarea><br>
                                            <input type="hidden" name="postId" value="${post.postID}">
                                            <input type="hidden" name="userID" value="${user.getUserID()}">
                                            <button type="submit" class="submit-button">Submit</button>
                                        </form>
                                    </div>
                                </div>
                                <a href="#" id="delete" onclick="confirmDelete(${postId})"><i class="ti-trash"> Delete</i></a>
                            </div>
                        </div>
                    </c:if>
                    <!-- Hidden form for deletion -->
                    <form id="deleteFormPost" action="deletePost" method="post" style="display:none;">
                        <input type="hidden" name="postID" id="postID">
                    </form>

                </div>
                <div class="blog-details-author">
                    <img src="${avtURL}" alt="Author Avatar" onerror="this.onerror=null;this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';" class="details-avatar">
                    <p class="details-name">${fullName}</p>
                    <div class="blog-details-date">${post.createdDate}</div>
                </div>
                <div class="blog-content">
                    <img src="${post.imgURL}" alt="Post Image" class="blog-content-img">
                    <p>${post.content}</p>
                </div>
            </c:if>
            <c:if test="${empty post}">
                <p>This post is not available.</p>
            </c:if>
        </div>

        <!-- COMMENT SECTION -->
        <div class="comment-container">
            <h2>Comment Section</h2>
            <form action="blogdetails" method="post">
                <div class="comment-box">
                    <textarea name="commentInput" rows="4" placeholder="Write a comment..." required></textarea><br><br>
                    <input type="hidden" name="postID" value="${postId}">
                    <input type="hidden" name="userID" value="${user.userID}">
                    <input type="submit" value="Post Comment" class="btn btn-primary">
                </div>
            </form>

            <!-- Display Comments Section -->
            <div class="comments-display" id="commentsDisplay">
                <c:if test="${not empty comments}">
                    <c:forEach var="comment" items="${comments}">
                        <div class="comment-section">
                            <img src="${comment.userAvtURL}" alt="Avatar" class="comment-avt" onerror="this.onerror=null;this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';">
                            <div class="comment-content">
                                <div>
                                    <div class="comment-author">${comment.userFullName}</div>
                                    <div class="comment-time">${comment.createdDate}</div>
                                </div>
                                <div class="comment">${comment.content}</div>
                            </div>
                            <c:if test="${user.userID == comment.userID}">
                                <div class="comment-options-dropdown">
                                    <i class="ti-more-alt comment-options-icon" id="comment-options" style="cursor: pointer;"></i>
                                    <div class="comment-options-menu dropdown-menu" id="comment-options-menu" style="display:none;">
                                        <a class="edit-comment-option"><i class="ti-pencil"> Edit</i></a>
                                        <a class="delete-comment-option" onclick="confirmDeleteComment()"><i class="ti-trash"> Delete</i></a>
                                    </div>

                                    <!-- Edit Comment Modal -->
                                    <div id="edit-comment-modal" class="edit-comment-modal modal">
                                        <div class="modal-content">
                                            <span class="close-modal">&times;</span>
                                            <form class="edit-comment-form" action="editComment" method="POST">
                                                <h2>Edit Comment</h2>
                                                <textarea id="edit-comment-content" name="commentContent" rows="4" required>${comment.content}</textarea><br><br>
                                                <!--<input type="hidden" id="edit-comment-id" name="commentID">-->
                                                <input type="hidden" name="postId" value="${post.postID}">
                                                <input type="hidden" name="userID" value="${user.userID}">
                                                <input type="hidden" name="commentID" value="${comment.commentID}">
                                                <button type="submit" class="btn btn-primary">Submit</button>
                                            </form>
                                        </div>
                                    </div>

                                    <!-- Delete Comment Form -->
                                    <form id="delete-comment-form" action="deleteComment" method="post" style="display:none;">
                                        <input type="hidden" name="postId" value="${post.postID}">
                                        <input type="hidden" name="commentID" value="${comment.commentID}">
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty comments}">
                    <div class="no-comments">No comments yet. Be the first to comment!</div>
                </c:if>
            </div>
        </div>

        <!-- AD BLOG -->
        <img src="./assets/img/adblog.svg" alt="" class="blog-ad">

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
            // Modal functionality
            const modal = document.getElementById("myModal");
            const btn = document.getElementById("openModalBtn");
            const span = document.getElementsByClassName("close")[0];

            btn.onclick = function () {
                modal.style.display = "block"; // Show modal
            }

            span.onclick = function () {
                modal.style.display = "none"; // Close modal
            }

            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = "none"; // Close modal if clicked outside
                }
            }

            // Confirm deletion
            function confirmDelete(postID) {
                const confirmAction = confirm("Are you sure you want to delete this post?");
                if (confirmAction) {
                    document.getElementById('postID').value = postID;
                    document.getElementById('deleteFormPost').submit();
                }
            }

            // Toggle dropdown visibility
            document.getElementById("more-options").addEventListener("click", function (event) {
                event.stopPropagation(); // Prevent window click from firing
                const dropdown = document.getElementById("dropdown-menu");
                dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
            });

            // Hide dropdown when clicking outside
            window.addEventListener("click", function (event) {
                const dropdown = document.getElementById("dropdown-menu");
                if (dropdown.style.display === "block" && !event.target.closest('.dropdown-container')) {
                    dropdown.style.display = "none";
                }
            });


            //Comment section


            // Show/Hide Dropdown Menu
            document.getElementById('comment-options').addEventListener('click', function () {
                const dropdownMenu = document.getElementById('comment-options-menu');
                dropdownMenu.style.display = dropdownMenu.style.display === 'none' ? 'block' : 'none';
            });

            // Close Modal
            document.querySelector('.close-modal').addEventListener('click', function () {
                document.getElementById('edit-comment-modal').style.display = 'none';
            });

            // Edit Comment Action
            document.querySelector('.edit-comment-option').addEventListener('click', function () {
                document.getElementById('edit-comment-modal').style.display = 'block';
            });

            // Confirm Delete Action
            function confirmDeleteComment() {
                if (confirm("Are you sure you want to delete this comment?")) {
                    document.getElementById('delete-comment-form').submit();
                }
            }

            // Close modal when clicking outside the modal content
            window.addEventListener('click', function (event) {
                const modal = document.getElementById('edit-comment-modal');
                if (event.target === modal) {
                    modal.style.display = 'none';
                }
            });

        </script>

        <script src="./tinymce/tinymce.min.js"></script>
        <script src="./assets/js/tinymceConfig.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    </body>
</html>