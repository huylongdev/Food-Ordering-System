<%-- 
    Document   : account
    Created on : Sep 17, 2024, 7:51:01 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <link href="style.css" rel="stylesheet">
        
    <link href="/OrderingSystem/WEB-INF/style/account.css" rel="stylesheet" />
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
  </head>
  <style>
    body {
      color: #333;
      background: #f7f8fa;
      font-family: "Poppins", sans-serif;
      padding: 0 !important;
      margin: 0 !important;
      font-size: 14px;
      text-rendering: optimizeLegibility;
      -webkit-font-smoothing: antialiased;
      -moz-font-smoothing: antialiased;
    }

    .profile-nav,
    .profile-info {
      margin-top: 20px;
    }

    .profile-nav .user-heading {
      background: #b0c4de; /* Updated to pastel blue */
      color: #fff;
      border-radius: 4px 4px 0 0;
      padding: 30px;
      text-align: center;
    }

    .profile-nav .user-heading.round a {
      border-radius: 50%;
      border: 5px solid rgba(255, 255, 255, 0.3);
      display: inline-block;
    }

    .profile-nav .user-heading a img {
      width: 100px;
      height: 100px;
      border-radius: 50%;
    }

    .profile-nav .user-heading h1 {
      font-size: 20px;
      font-weight: 500;
      margin-bottom: 5px;
    }

    .profile-nav .user-heading p {
      font-size: 12px;
      color: #ddd;
    }

    .profile-nav ul {
      margin-top: 10px;
    }

    .profile-nav ul > li {
      border-bottom: 1px solid #f1f1f1;
      margin-top: 0;
      line-height: 30px;
    }

    .profile-nav ul > li > a {
      color: #666;
      border-left: 5px solid #fff;
      padding: 10px 15px;
    }

    .profile-nav ul > li > a:hover,
    .profile-nav ul > li.active a {
      background: #f9f9f9;
      border-left: 5px solid #b0c4de; /* Updated to pastel blue */
      color: #333;
    }

    .profile-info .panel-footer {
      background-color: #f9f9f9;
      margin: 10px 0;
    }

    .profile-info .panel-footer ul li a {
      color: #666;
    }

    .bio-graph-heading {
      background: #b0c4de; /* Updated to pastel blue */
      color: #fff;
      text-align: center;
      font-style: italic;
      padding: 30px 100px;
      border-radius: 4px 4px 0 0;
      font-size: 18px;
      font-weight: 400;
    }

    .bio-graph-info {
      color: #333;
      background: #f0f0f0;
      margin: 50px 0px;
    }

    .bio-graph-info h1 {
      font-size: 24px;
      font-weight: 500;
      margin-bottom: 20px;
    }

    .bio-row {
      width: 50%;
      float: left;
      margin-bottom: 10px;
      padding: 0 15px;
    }

    .bio-row p span {
      width: 120px;
      display: inline-block;
      font-weight: 500;
      color: #666;
    }

    .bio-desk h4 {
      font-size: 16px;
      font-weight: 500;
    }

    .bio-desk h4.terques {
      color: #4cc5cd;
    }

    .bio-desk h4.red {
      color: #e74c3c;
    }

    .bio-desk h4.green {
      color: #2ecc71;
    }

    .bio-desk h4.purple {
      color: #9b59b6;
    }

    .activity {
      width: 100%;
      float: left;
      margin-bottom: 10px;
    }

    .activity span {
      float: left;
      width: 50px;
      height: 50px;
      line-height: 50px;
      border-radius: 50%;
      background: #eee;
      text-align: center;
      color: #fff;
      font-size: 18px;
    }

    .activity h4 {
      margin-top: 0;
      font-size: 16px;
      font-weight: 500;
    }

    .activity p {
      font-size: 14px;
    }

    .activity .activity-desk {
      margin-left: 70px;
      position: relative;
    }

    .activity-desk .panel {
      background: #fff;
      border: 1px solid #ddd;
      padding: 15px;
      border-radius: 4px;
    }

    .activity-desk .arrow {
      border-right: 8px solid #fff;
    }

    .activity-desk .arrow-alt {
      border-left: 8px solid #fff;
    }

    .bio-graph-info .bio-row {
      width: 50%;
      padding: 10px 0px 0px 40px;
      font-size: 1.1em;
    }

    .bio-graph-info .bio-row span {
      font-weight: bold;
      color: #333;
    }

    .panel-body .bio-chart {
      text-align: center;
    }

    .panel-body .bio-desk {
      margin-top: 10px;
      text-align: center;
    }

    .panel-body .bio-desk h4 {
      font-size: 1.3em;
      font-weight: bold;
      margin-bottom: 10px;
    }

    .img-circle {
      border-radius: 50%;
      width: 100px;
      height: 100px;
    }
    li {
      padding: 0 6px 0 0;
    }
  </style>
  <body>
    <div class="container bootstrap snippets bootdey">
      <div class="row">
        <!-- Profile Sidebar -->
        <div class="col-md-3 profile-nav">
          <div class="panel">
            <div class="user-heading round">
              <a href="#">
                <img
                  src="${user.getAvtImg()}"
                  onerror="this.onerror=null;this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';"
                  alt="Profile Picture"
                  class="img-responsive img-circle"
                />
              </a>
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
                <a href="/OrderingSystem">
                  <i class="fa fa-home"></i> Back to hompage</a
                >
              </li>
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
              <button style="background-color: #b0c4de; height: 100%" class="btn pull-right">
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
    </div>
<!--        <h1>Account's information here :VV </h1>
        <div class="container mt-4 mb-4 p-3 d-flex justify-content-center"> 
            <div class="card p-4"> <div class=" image d-flex flex-column justify-content-center align-items-center">
                    <button class=""> <img src=${user.getAvtImg()} onerror="this.onerror=null;this.src='https://i.pinimg.com/564x/09/a9/2c/09a92c1cbe440f31d1818e4fe0bcf23a.jpg';" height="100" width="100" />
                    </button> <span class="name mt-3">${user.getFullName()}</span> <span class="idd">${user.getUserName()}</span>
                    <div class="d-flex flex-row justify-content-center align-items-center gap-2"> 
                        <span class="idd1">${user.getEmail()}</span> <span><i class="fa fa-copy"></i></span> </div> 
                    <div class="d-flex flex-row justify-content-center align-items-center mt-3"> 
                        <span class="number"> <span class="follow">${user.getPhoneNumber()}</span></span> </div> 
                    <div class=" d-flex mt-2"> <button class="btn1 btn-dark">Edit Profile</button> </div> 
                    <div class="text mt-3"> <span>Không biết để chữ chi nữa thì thôi ghi bậy bạ v.<br><br> Artist/ Creative Director by Day #NFT minting@ with FND night. </span> 
                    </div> <div class="gap-3 mt-3 icons d-flex flex-row justify-content-center align-items-center"> 
                        <span><i class="fa fa-twitter"></i></span> <span><i class="fa fa-facebook-f"></i></span>
                        <span><i class="fa fa-instagram"></i></span> <span><i class="fa fa-linkedin"></i></span> </div>
                    <div class=" px-2 rounded mt-4 date "> <span class="join">Joined May,2021</span> </div> </div> </div>
</div>-->
    </body>
</html>
