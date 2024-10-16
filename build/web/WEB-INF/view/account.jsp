<%-- 
    Document   : account
    Created on : Sep 21, 2024, 12:38:14 AM
    Author     : phuct
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="model.*,java.util.*,util.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Account</title>

        <link rel="stylesheet" href="./index.css">
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link
            href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
            rel="stylesheet"
            />

        <!-- Google Fonts -->
        <link
            href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap"
            rel="stylesheet"
            />

        <!-- Font Awesome for icons -->
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
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
            <link rel="stylesheet" href="./assets/css/account.css">

    </head>

    <body>
        <div class="container bootstrap snippets bootdey">
            <div class="row">
                <!-- Profile Sidebar -->
                <div class="col-md-3 profile-nav">
                    <div class="panel">
                        <div class="user-heading round">

                            <div class = img-wrapper>
                                <a href="#">
                                    <img
                                        src="${user.getAvtImg()}"
                                        onerror="this.onerror=null;this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';"
                                        alt="Profile Picture"
                                        class="img-responsive img-circle"
                                        />
                                </a>
                                <div class="icon-wrapper">
                                    <i id ="user-icon" class="fa fa-edit" onclick="showUpdateAvatarOverlay()"></i>
                                </div>
                            </div>
                            <h1>${user.getFullName()}</h1>
                            <p>${user.getEmail()}</p>
                        </div>

                        <ul class="nav nav-pills nav-stacked">
                            <li class="active">
                                <a href="#"> <i class="fa fa-user"></i> Profile</a>
                            </li>
                            <li>
                                <a href="#">
                                    <i class="fa fa-calendar"></i> Recent Activity
                                    <span class="label label-warning pull-right r-activity"
                                          >9</span
                                    ></a
                                >
                            </li>
                            <li>
                                <a href="editUser?userId=${user.getUserID()}">
                                    <i class="fa fa-edit"></i> Edit profile</a
                                >
                            </li>
                            <li>
                                <a href="./changePassword">
                                    <i class="fa fa-edit"></i> Change password</a
                                >
                            </li>
                            <li>
                                <a href="./order-history">
                                    <i class="fa fa-calendar"></i> View Orders</a
                                >
                            </li>
                            <li>
                                <a href="/OrderingSystem">
                                    <i class="fa fa-home"></i> Back to hompage</a
                                >
                            </li>
                            <div class="text-center btn-dash" style="margin-top:2em; line-height:200px; ">
                        <a href="./dashboard"  class="btn btn-primary">View Admin Dashboard</a>
                    </div>
                        </ul>
                    </div>
                </div>

                <!-- Profile Info -->
                <div class="col-md-9 profile-info">
                    <div class="panel">
                        <form>
                            <textarea
                                placeholder="What's in your mind today?"
                                rows="2"
                                class="form-control input-lg p-text-area"
                                ></textarea>
                        </form>
                        <footer class="panel-footer">
                            <button style="background-color: #b0c4de" class="btn pull-right">
                                Post
                            </button>
                            <ul class="nav nav-pills">
                                <li>
                                    <a href="#"><i class="fa fa-map-marker"></i></a>
                                </li>
                                <li>
                                    <a href="#"><i class="fa fa-camera"></i></a>
                                </li>
                                <li>
                                    <a href="#"><i class="fa fa-film"></i></a>
                                </li>
                                <li>
                                    <a href="#"><i class="fa fa-microphone"></i></a>
                                </li>
                            </ul>
                        </footer>
                    </div>

                    <div class="panel">
                        <div class="panel-body bio-graph-info">
                            <h1 style="font-weight: 600; padding: 10px 10px 0 10px">
                                Bio Graph
                            </h1>
                            <div class="row">
                                <div class="bio-row">
                                    <p><span>Full Name</span>: ${user.getFullName()}</p>
                                </div>
                                <div class="bio-row">
                                    <p><span>Username</span>: ${user.getUserName()}</p>
                                </div>
                                <div class="bio-row">
                                    <p><span>Email</span>: ${user.getEmail()}</p>
                                </div>
                                <div class="bio-row">
                                    <p><span>Phone Number</span>: ${user.getPhoneNumber()}</p>
                                </div>
                                <div class="bio-row">
                                    <p><span>Address</span>: ${user.getAddress()}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                          
                                
                        
                                
                </div>

            </div>





            <div id="update-avatar-overlay" class="overlay center">
                <div class="overlay-content">
                    <span class="close-btn" onclick="hideUpdateAvatarOverlay()">&times;</span></br>
                    <form name = "changeAvatar" action = "account" method = "post" enctype="multipart/form-data">
                        <label for="imgURL">Upload Avatar:</label>
                        <input type="file" id="img" name="img" accept=".jpg,.jpeg,.png" required>
                        <input type = "hidden" name = "userID" value =${user.getUserID()} >
                        <input type = "hidden" name = "mt" value ="changeAvatar">
                        </br></br></br>
                        <button type ="submit" style="background-color: #b0c4de" class="btn">Save</button>
                    </form>
                </div>
            </div>   



 


        </div>
        <script src="index.js"></script>
    </body>
</html>
