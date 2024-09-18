<%-- 
    Document   : require-password
    Created on : Sep 18, 2024, 11:29:42 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Form Submission</title>
    <script>
        
        function validateForm() {
            var password = document.getElementById("password").value;
            if (password == "") {
                alert("Please enter your password.");
                return false; 
                
            }
            return true; // Cho phép submit nếu form đã được điền đầy đủ
        }


        window.onbeforeunload = function() {
            if (!document.getElementById("formSubmitted").value) {
                return "You have not submitted the form yet. Are you sure you want to leave?";
            }
        }


        function markFormSubmitted() {
            document.getElementById("formSubmitted").value = true;
        }
    </script>
</head>
<body>
    <h2>Set Password</h2>
    <form action="login-google" method="POST" onsubmit="return validateForm(); markFormSubmitted();">
        <input type="hidden" id="formSubmitted" value="false" /><label for="password">UserName</label>
        <input type="text" id="username" name="username" required defaut = ""${username}"" placeholder="${username}"/><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required /><br><br>
        <input type="hidden" id="1" name="name" value ="${name}" />
        <input type="hidden" id="2" name="email" value ="${email}" />
        <input type="hidden" id="3" name="picture" value ="${picture}" />
        <button type="submit">Submit</button>
    </form>
</body>
</html>

