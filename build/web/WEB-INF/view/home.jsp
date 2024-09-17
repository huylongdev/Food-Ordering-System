<%-- 
    Document   : home
    Created on : Sep 17, 2024, 2:49:16 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Foodie</title>
    </head>
    <body>
        <h4><a href ="./register">Register</a></h4>
        <h4><a href ="./login">Login</a></h4>
        <h4><a href ="./account">Profile</a></h4>


    <c:if test="${not empty sessionScope.username }">
        <h4>Content only seen when you'd login</h4>
        <h4><a href ="./logout">Logout</a></h4>
    </c:if>
    </body>
</html>
