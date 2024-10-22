<%@page contentType="text/html" pageEncoding="UTF-8" import="model.*,java.util.*,util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Account</title>

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet" />

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />

        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" />
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
        <link rel="stylesheet" href="./assets/css/account.css">

    </head>

    <body>
        <%@ include file="/include/header.jsp" %>

        <div class="container">
            <div class="header">
                <div class="user-info">
                    <img src="${user.getAvtImg()}" onerror="this.onerror=null;this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';" alt="Profile Picture">
                    <div class="icon-wrapper">
                        <i id ="user-icon" class="fa fa-edit" data-bs-toggle="modal" data-bs-target="#avatarModal"></i>
                    </div>
                    <div>
                        <h2>${user.getFullName()}</h2>
                        <p>${user.getEmail()}</p>
                    </div>
                </div>

                <div class="modal fade" id="avatarModal" tabindex="-1" aria-labelledby="avatarModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="avatarModalLabel">Update Avatar</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form name="changeAvatar" action="account" method="post" enctype="multipart/form-data">
                                    <label for="imgURL">Upload Avatar:</label>
                                    <input type="file" id="img" name="img" accept=".jpg,.jpeg,.png" required>
                                    <input type="hidden" name="userID" value="${user.getUserID()}">
                                    <input type="hidden" name="mt" value="changeAvatar">
                                    <button type="submit" style="background-color: #b0c4de" class="btn mt-3">Save</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="nav-bar">
                <!--<a href="editUser?userId=${user.getUserID()}"><i class="fa fa-edit"></i> Edit Profile</a>-->
                <a href="./changePassword"><i class="fa fa-edit"></i> Change Password</a>
                <a href="./order-history"><i class="fa fa-calendar"></i> View Orders</a>
                <a href="/OrderingSystem"><i class="fa fa-home"></i> Back to Homepage</a>
            </div>

            <form action="editUser" method="POST">
                <div class="form-section">
                    <input type="hidden" name="userId" value="${user.userID}" />
                    <div class="form-group">
                        <label for="fullName">Full Name</label>
                        <input type="text" id="fullName" name="fullName" value="${user.getFullName()}">
                    </div>
                    <div class="form-group">
                        <label for="userName">Username</label>
                        <input type="text" id="username" name="username" value="${user.getUserName()}">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input readonly type="email" id="email" name="email" value="${user.getEmail()}">
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Phone Number</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" value="${user.getPhoneNumber()}">
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" value="${user.getAddress()}">
                    </div>
                </div>

                <button type="submit" class="edit-btn">Save Changes</button>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <%@ include file="/include/footer.jsp" %>

    </body>
</html>
