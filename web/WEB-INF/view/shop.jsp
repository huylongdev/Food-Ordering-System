<%-- 
    Document   : shop
    Created on : Oct 2, 2024, 1:53:59 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store Management</title>
    </head>
   
    <body>
        <button onclick = "showAddProductOverlay()" >Add product</button>
        <div id ="add-product-overlay" class ="overlay center">
           <div class="overlay-content">
                    <span class="close-btn" onclick="hideUpdateAvatarOverlay()">&times;</span></br>
                    <form name = "changeAvatar" action = "account" method = "post" enctype="multipart/form-data">
                        <label for="imgURL">Upload Avatar:</label>
                       
                        <input type = "hidden" name = "userID" value =${user.getUserID()}>
                        <input type = "hidden" name = "mt" value ="changeAvatar">
                        </br></br></br>
                        <button type ="submit" style="background-color: #b0c4de" class="btn">Save</button>
                    </form>
                </div> 
        </div>
    </body>
</html>
