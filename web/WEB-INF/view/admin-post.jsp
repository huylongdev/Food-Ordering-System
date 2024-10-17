<%-- 
    Document   : admin-post-item
    Created on : Oct 15, 2024, 8:58:01 AM
    Author     : Lenovo
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
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard Demo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <link rel="stylesheet" href="assets_01/css/style.css">

    </head>
    <%@include file="../../assets_01/includes/navbar.jsp"%>
    <body>


        <div class="main">

            <center> <h1 class="mb-5">Posts Management</h1>
                <!-- Hiển thị thông báo từ session -->
                <c:if test="${not empty sessionScope.msg}">
                    <p style="color:red">${sessionScope.msg}</p>
                    <c:remove var="msg" scope="session"/>
                </c:if>
                <form action="admin-post" method="GET">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">User ID</th>
                                <th scope="col">Heading</th>
                                <th scope="col">Image</th>
                                <th scope="col">Content</th>
                                <th scope="col">Created Date</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${posts}" var="post">
                                <tr>
                                    <td>${post.postID}</td>
                                    <td>${post.userID}</td>
                                    <td><img src="postImg/${post.imgURL}" width="50%" height="50%"/>
                                    </td>
                                    <td>${post.heading}</td>
                                    <td>${post.content}</td>
                                    <td>${post.createdDate}</td>

                                    <td>
                                        <div class="d-flex justify-content-center align-items-center">
                                            <a class="btn btn-primary btn-sm me-2 text-nowrap" href="blogdetails?postId=${post.postID}">View Detail</a> <!-- Thay # bằng liên kết chi tiết nếu cần -->
                                            <c:choose>
                                                <c:when test="${post.status}">
                                                    <a class="btn btn-danger btn-sm" href="admin-post?action=deleteIllegalPost&id=${post.postID}">Delete</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-warning btn-sm" disabled>Deleted</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </td>


                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                </form></center>
        </div>
        <!-- =========== Scripts =========  -->
        <script src="assets_01/js/main.js"></script>

        <!-- ======= Charts JS ====== -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>
        <script src="assets_01/js/chartsJS.js"></script>

        <!-- ====== ionicons ======= -->
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    </body>
</html>
