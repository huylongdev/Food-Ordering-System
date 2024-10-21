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
        <link href="./assets/css/reject-restaurant.css" rel="stylesheet">
        <script src="./assets/js/reject-restaurant.js"></script>

    </head>

    <style>
        /* Giảm khoảng cách giữa các cột */
        .table td, .table th {
            padding: 8px 15px;
            vertical-align: middle; /* Căn giữa theo chiều dọc */
        }

        /* Đặt chiều rộng cố định cho cột hình ảnh */
        td.image-column, th.image-column {
            width: 120px;
            text-align: left; /* Căn lề trái cho ảnh */
        }

        /* Đặt kích thước cho ảnh trong cột */
        img.shop-image {
            width: 100%;
            max-width: 80px;  /* Điều chỉnh kích thước ảnh tối đa */
            height: auto;
        }

        /* Căn lề trái cho các trường Name, Description, Address */
        td.name-column, th.name-column,
        td.description-column, th.description-column,
        td.address-column, th.address-column {
            text-align: left;
        }

        /* Căn lề trái cho thời gian mở và đóng */
        td.time-open-column, th.time-open-column,
        td.time-close-column, th.time-close-column {
            text-align: left;
        }
        
        .btn-admin{
            display: block;
            margin: 5px;
        }

    </style>



   
    <body>

         <%@include file="../../assets_01/includes/navbar.jsp"%>
        
        

        <div class="main">

            <center> <h1 class="mb-5">Register Restaurant Management</h1>
                <c:if test="${not empty message1}">
                    <p class="text-success text-center mt-3">${message1}</p>
                </c:if>
                <c:if test="${not empty message2}">
                    <p class="text-danger text-center mt-3">${message2}</p>
                </c:if>


                <form action="admin-register-restaurant" method="GET">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Image</th>
                                <th scope="col">Name</th>
                                <th scope="col">Description</th>
                                <th scope="col">Address</th>
                                <th scope="col">Time Open</th>
                                <th scope="col">Time Close</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${shops}" var="shop">
                                <tr>
                                    <td>${shop.shopID}</td>

                                    <td class="image-column">
                                        <img src="${shop.shopImage}" class="shop-image"/>
                                    </td>

                                    <td>${shop.name}</td>
                                    <td>${shop.description}</td>
                                    <td>${shop.address}</td>

                                    <td>${shop.timeOpen}</td>
                                    <td class="time-open-close">${shop.timeClose}</td>

                                    <td>
                                        <div class="action-buttons">
                                            <a class="btn btn-primary btn-sm text-nowrap btn-admin" href="admin-register-restaurant?action=view-details&shopId=${shop.shopID}">View Detail</a>
                                            <a class="btn btn-success btn-sm btn-admin" href="admin-register-restaurant?action=approve-register&shopId=${shop.shopID}">Approve</a>
                                            <a class="btn btn-danger btn-sm btn-admin"   href="admin-register-restaurant?action=show-reject-form&shopId=${shop.shopID}">Reject</a>

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
