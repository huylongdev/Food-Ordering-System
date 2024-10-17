<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@ page import="model.Account" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Account user = (Account) session.getAttribute("loggedUser");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Admin Dashboard</title>

        <link rel="stylesheet" href="./index.css">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/css/dashboard.css">
        <script src="./assets/js/plugin/webfont/webfont.min.js"></script>
        <script>
            WebFont.load({
                google: {families: ["Public Sans:300,400,500,600,700"]},
                custom: {
                    families: [
                        "Font Awesome 5 Solid",
                        "Font Awesome 5 Regular",
                        "Font Awesome 5 Brands",
                        "simple-line-icons",
                    ],
                    urls: ["./assets/css/fonts.min.css"],
                },
                active: function () {
                    sessionStorage.fonts = true;
                },
            });
        </script>

        <!-- CSS Files -->
        <link rel="stylesheet" href="./assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="./assets/css/plugins.min.css" />
        <link rel="stylesheet" href="./assets/css/kaiadmin.min.css" />
        <link rel="stylesheet" href="./assets/font/themify-icons/ie7/ie7.css" />

        <style>

        </style>
    </head>

    <body>



        <div id="dashboard"> 
            <div id="header">
                <nav class="navbar navbar-expand-lg bg-body-tertiary">
                    <div class="container-fluid">

                        <a class="navbar-brand" href="./">FOODIE</a>

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
                        <div class="collapse navbar-collapse navlogo" id="navbarNavAltMarkup">


                            <div class="navbar__item">


                                <nav id="nav-bar">
                                    <ul class="nav-list">
                                        <li class="nav-item">
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
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
            <div class="dashContainer">                               
                <h1 class="title-dash">DASHBOARD</h1>
                <div class="row">
                    <div class="col-sm-6 col-md-3">
                        <div class="card card-stats card-round">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col-icon">
                                        <div
                                            class="icon-big text-center icon-primary bubble-shadow-small"
                                            >
                                            <i class="ti-home"></i>
                                        </div>
                                    </div>
                                    <div class="col col-stats ms-3 ms-sm-0">
                                        <div class="numbers">
                                            <p class="card-category">Restaurant</p>
                                            <h4 class="card-title">
                                                ${restaurantCount} 
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="card card-stats card-round">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col-icon">
                                        <div
                                            class="icon-big text-center icon-info bubble-shadow-small"
                                            >
                                            <i class="ti-apple "></i>
                                        </div>
                                    </div>
                                    <div class="col col-stats ms-3 ms-sm-0">
                                        <div class="numbers">
                                            <p class="card-category">Food</p>
                                            <h4 class="card-title">
                                                ${foodCount}
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="card card-stats card-round">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col-icon">
                                        <div
                                            class="icon-big text-center icon-success bubble-shadow-small"
                                            >
                                            <i class="ti-clipboard"></i>
                                        </div>
                                    </div>
                                    <div class="col col-stats ms-3 ms-sm-0">
                                        <div class="numbers">
                                            <p class="card-category">Post</p>
                                            <h4 class="card-title">
                                                ${postCount}
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="card card-stats card-round">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col-icon">
                                        <div
                                            class="icon-big text-center icon-secondary bubble-shadow-small"
                                            >
                                            <i class="ti-money"></i>
                                        </div>
                                    </div>
                                    <div class="col col-stats ms-3 ms-sm-0">
                                        <div class="numbers">
                                            <p class="card-category">Order</p>
                                            <h4 class="card-title">
                                                ${billCount} </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container bootstrap snippets bootdey">
                    <div class="row">






                        <div class="user-list" style="max-height: 300px; overflow-y: scroll;">
                            <h2>List of Users</h2>
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>User's ID</th>
                                        <th>Avatar</th>
                                        <th>Name</th>
                                        <th>Phone</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="user" items="${userList}">
                                        <tr>
                                            <td>
                                                <h5>${user.userID}</h5>
                                            </td>
                                            <td>
                                                <img src="${user.avtImg}" onerror="this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';" alt="Avatar of ${user.fullName}" class="img-responsive img-circle" style="width: 40px; height: 40px;">
                                            </td>
                                            <td>${user.fullName}</td>
                                            <td>${user.phoneNumber}</td>
                                            <td>
                                                <button class="btn btn-danger" onclick="openBanUserModal('${user.userID}')">Ban</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </div> 
        </div>  


        <script src="index.js">

        </script>
        <div id="overlay"></div>

        <!-- Ban User Modal -->
        <div id="banUserModal">
            <h3>Ban User</h3>
            <p id="banUserId"></p>
            <form id="banUserForm">
                <div class="form-group">
                    <input type="checkbox" id="abusiveLanguage" name="banReasons" value="abusiveLanguage">
                    <label for="abusiveLanguage">The user has used abusive language.</label>
                </div>
                <div class="form-group">
                    <input type="checkbox" id="spammedOrders" name="banReasons" value="spammedOrders">
                    <label for="spammedOrders">The user has spammed orders too many times.</label>
                </div>
                <div class="form-group">
                    <input type="checkbox" id="violatedStandards" name="banReasons" value="violatedStandards">
                    <label for="violatedStandards">The user has violated community standards.</label>
                </div>
                <div class="form-group">
                    <input type="checkbox" id="spreadMalware" name="banReasons" value="spreadMalware">
                    <label for="spreadMalware">The user has spread viruses or malware.</label>
                </div>
                <div class="form-group">
                    <input type="checkbox" id="others" name="banReasons" value="others">
                    <label for="others">Others:</label>
                    <input type="text" id="othersInput" name="othersInput" placeholder="Please specify">
                </div>
                <div class="button-group">
                    <button type="button" class="btn-confirm" onclick="confirmBan()">Confirm</button>
                    <button type="button" class="btn-cancel" onclick="closeBanUserModal()">Cancel</button>
                </div>
            </form>
        </div>

        <script>
            // Hiển thị modal
            function openBanUserModal(userId) {
                document.getElementById('banUserId').innerText = `Ban User ID: ${userId}`;
                document.getElementById('banUserModal').style.display = 'flex'; // Hiển thị modal
            }

    // Đóng modal
            function closeBanUserModal() {
                document.getElementById('banUserModal').style.display = 'none'; // Ẩn modal
            }

    // Xác nhận việc ban user
            function confirmBan() {
                // Logic xử lý khi xác nhận ban user
                alert("User has been banned!");
                closeBanUserModal(); // Đóng modal sau khi xác nhận
            }
        </script>
    </body>
</html>
