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
    </head>
    <body>
        <h1>Account's information here :VV </h1>
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
</div>
    </body>
</html>
