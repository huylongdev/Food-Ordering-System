<%-- 
    Document   : addPost
    Created on : Oct 8, 2024, 3:58:47 PM
    Author     : phuct
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Blog Post</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            h1 {
                color: #333;
            }
            input, textarea {
                display: block;
                margin-bottom: 10px;
                width: 100%;
                max-width: 600px;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            button {
                padding: 10px 20px;
                background-color: #007BFF;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            button:hover {
                background-color: #0056b3;
            }
            
            /* Modal styles */
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.5);
            }
            .modal-content {
                background-color: #fff;
                margin: 10% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 700px;
                border-radius: 8px;
            }
            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }
            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <h1>Insert Blog</h1>
        
        <!-- Trigger Button to Open Modal -->
        <button id="openModalBtn">Create New Blog Post</button>
        
        <!-- Modal Structure -->
        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Add Blog Post</h2>
                <form action="addPost" method="POST" enctype="multipart/form-data"> 
                    Picture Cover: <input name="imgPost" type="file"/><br>
                    Heading: <input name="title" type="text" required /><br>
                    Description: <textarea id="default" name="description"></textarea><br>
                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>

        <!-- Include TinyMCE Scripts -->
        <script src="./tinymce/tinymce.min.js"></script>
        <script src="./assets/js/tinymceConfig.js"></script>

        <!-- JavaScript to Handle Modal -->
        <script>
            // Get modal and elements
            var modal = document.getElementById("myModal");
            var btn = document.getElementById("openModalBtn");
            var span = document.getElementsByClassName("close")[0];

            // Open modal when button is clicked
            btn.onclick = function() {
                modal.style.display = "block";
            }

            // Close modal when 'x' is clicked
            span.onclick = function() {
                modal.style.display = "none";
            }

            // Close modal when clicking outside of modal
            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
        </script>
    </body>
</html>
