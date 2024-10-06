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
<<<<<<< HEAD
        

        <div id="update-product-overlay" class="overlay center">
            <div class="overlay-content">
                <span class="close-btn" onclick="hideUpdateProductOverlay()">&times;</span><br>
                <form name="update-product" action="restaurant-detail" method="post" enctype="multipart/form-data">
                    <div class="add-product-form">
                        <h1>Update product</h1>
                        <input type="hidden" name="mt" value="update">
                        <input id="shopID" type="hidden" name="shopID" value=""required>
                        <input id="productID" type="hidden" name="productID" value=""required>

                        <label for="title">Name</label>
                        <input type="text" id="name" name="name" value=""required>

                        <label for="price">Price:</label>
                        <input type="number" id="price" name="price" value=""required>

                        <label for="category">Category:</label>
                        <select id="category" name="category">
                            <option value="1">Food</option>
                            <option value="2">Drink</option>
                            <option value="3">Dessert</option>
                            <option value="4">Snacks</option>
                            <option value="5">Beverages</option>
                        </select><br>

                        <label for="description">Description:</label>
                        <input type="text" id="description" name="description" value=""required>

                        <label for="imgs">Images</label>
                        <input type="file" id="image" name="img" accept="image/*" multiple required><br>

                        <button type="submit" style="background-color: #b0c4de" class="btn">Save</button>
                    </div>
                </form>
            </div>
        </div>


        <div class="pagination">
            <button>&laquo;</button>
            <button class="active">1</button>
            <button>2</button>
            <button>3</button>
            <button>4</button>
            <button>&raquo;</button>
        </div>

        <%@ include file="/include/footer.jsp" %>


        <script src="js/Jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"
        ></script>
=======
>>>>>>> cbf9322c3b242371e94b9d0461585a9c57bfd713
    </body>
</html>
