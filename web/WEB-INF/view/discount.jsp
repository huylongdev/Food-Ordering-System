<%-- 
    Document   : restaurant
    Created on : Oct 2, 2024, 2:16:53 PM
    Author     : phuct
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Discount</title>
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

        <link rel="stylesheet" href="./assets/css/style.css" />
        <link rel="stylesheet" href="./assets/css/blog.css" />
        <link rel="stylesheet" href="./assets/css/blogdetails.css" />
        <link rel="stylesheet" href="./assets/css/header-footer.css" />
        <link rel="stylesheet" href="./assets/css/discount.css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    </head>
    <body>
        <!-- HEADER -->
        <div id="overlay"></div>
        <%@ include file="/include/header.jsp" %>

        <!-- PAGE INFO -->
        <div id="page-info">
            <div class="page-title">Discount</div>
            <div class="page-info-more">
                <a href="/OrderingSystem/">Home</a>

                <a style="border-left: 1px solid #e8e8ea" href="#" id="createDiscountAnchor">Create Discount</a>

            </div>
        </div>

        <div class="saleBanner">
            <img src="./assets/img/sale.png" alt="" class="sliderback" />
        </div>

        <div class="container">

            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success" role="alert">
                    ${sessionScope.successMessage}
                </div>
                <c:remove var="successMessage" scope="session" /> 
            </c:if>
            <div id="discountForm">
                <form method="post" action="discount">
                    <input type="hidden" name="action" value="create">
                    <div class="form-group">
                        <label>User ID</label>
                        <input type="number" class="form-control" name="userID" required>
                    </div>
                    <div class="form-group">
                        <label>Discount Name</label>
                        <input type="text" class="form-control" name="discountName" required>
                    </div>
                    <div class="form-group">
                        <label>Start Date</label>
                        <input type="date" class="form-control" name="startDate" required>
                    </div>
                    <div class="form-group">
                        <label>End Date</label>
                        <input type="date" class="form-control" name="endDate" required>
                    </div>
                    <div class="form-group">
                        <label>Discount Percentage</label>
                        <input type="number" step="0.01" class="form-control" name="discountPercentage" required>
                    </div>
                    <div class="form-group">
                        <label>Type</label>
                        <input type="text" class="form-control" name="type" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-add-dis">Add Discount</button>
                </form>

            </div>
            <h3 class="mt-4">Current Discounts</h3>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>User ID</th>
                        <th>Discount Name</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Percentage</th>
                        <th>Type</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="discount" items="${discounts}">
                        <tr>
                            <td>${discount.discountID}</td>
                            <td>${discount.userID}</td>
                            <td>${discount.discountName}</td>
                            <td>${discount.startDate}</td>
                            <td>${discount.endDate}</td>
                            <td>${discount.discountPercentage}</td>
                            <td>${discount.type}</td>
                            <td>
                                <form method="post" action="discount" style="display:inline-block;" onsubmit="return confirmDelete();">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="discountID" value="${discount.discountID}">
                                    <button type="submit" class="btn btn-danger btn-delete">Delete</button>
                                </form>
                                <button class="btn btn-warning btn-edit" data-toggle="modal" data-target="#editModal${discount.discountID}">Edit</button>
                            </td>
                        </tr>
                        <!-- Edit Modal -->
                    <div class="modal fade" id="editModal${discount.discountID}" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editModalLabel">Edit Discount</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body edit-modal">
                                    <form method="post" action="discount">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="discountID" value="${discount.discountID}">
                                        <div class="form-group">
                                            <label>User ID</label>
                                            <input type="number" class="form-control" name="userID" value="${discount.userID}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Discount Name</label>
                                            <input type="text" class="form-control" name="discountName" value="${discount.discountName}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Start Date</label>
                                            <input type="date" class="form-control" name="startDate" value="${discount.startDate}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>End Date</label>
                                            <input type="date" class="form-control" name="endDate" value="${discount.endDate}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Discount Percentage</label>
                                            <input type="number" step="0.01" class="form-control" name="discountPercentage" value="${discount.discountPercentage}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Type</label>
                                            <input type="text" class="form-control" name="type" value="${discount.type}" required>
                                        </div>
                                        <button type="submit" class="btn btn-primary btn-up-dis">Update Discount</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                </tbody>
            </table>


        </div>

        <img src="./assets/img/adblog.svg" alt="" class="blog-ad" />

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
            document.querySelectorAll(".pagination button").forEach((button) => {
                button.addEventListener("click", function () {
                    document
                            .querySelector(".pagination button.active")
                            .classList.remove("active");
                    this.classList.add("active");
                });
            });
        </script>
        <script>
            document.getElementById('createDiscountAnchor').addEventListener('click', function (event) {
                event.preventDefault(); 
                document.getElementById('discountForm').style.display = 'block'; 
                document.getElementById('overlay').style.display = 'block'; 
            });

            document.getElementById('overlay').addEventListener('click', function () {
                document.getElementById('discountForm').style.display = 'none'; 
                document.getElementById('overlay').style.display = 'none'; 
            });
            function confirmDelete() {
                const confirmed = confirm("Are you sure you want to delete this discount?");
                if (confirmed) {
                    alert("Discount deleted successfully."); 
                }
                return confirmed; 
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
