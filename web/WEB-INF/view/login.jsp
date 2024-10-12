<%-- Document : login Created on : Sep  14, 2024, 12:41:24 AM Author : HUYLONG --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
                                                                  uri="http://java.sun.com/jsp/jstl/core"%> <%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Login Page</title>
        
        <link rel="stylesheet" href="./index.css">
        <link rel="stylesheet" href="themify-icons-font\themify-icons\themify-icons.css">
    </head>
    
    <body>
        
        <div class="form-structor">
            <div class="signup">
                <div class="center">
                        <h2 class="form-title" id="login">Log in</h2>
                        <div class="social-container">
                            <a href="https://accounts.google.com/o/oauth2/auth?scope=profile%20email&redirect_uri=http://localhost:8080/OrderingSystem/login-google&response_type=code
                               &client_id=953119864721-91spj7rint3v7cqejchkgqm3m63hgb1i.apps.googleusercontent.com&approval_prompt=force" class="social"><svg xmlns="http://www.w3.org/2000/svg"colour ="white" width="16" height="16" fill="white" class="bi bi-google" viewBox="0 0 16 16">
                                <path d="M15.545 6.558a9.4 9.4 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.7 7.7 0 0 1 5.352 2.082l-2.284 2.284A4.35 4.35 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.8 4.8 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.7 3.7 0 0 0 1.599-2.431H8v-3.08z"/>
                                </svg></i></a>
                        </div>
                        <form action="login" method="post">
                            <div class="form-holder">
                                <input type="text" class="input" placeholder="Username" name ="user" />
                                <input type="password" class="input" placeholder="Password" name="pass"/>
                                
                            </div>
                            <div id="forgot-password">
                                    <a class="forgot-pass" href="./forgot-password">Forgot password?</a>  
                                </div>
                            <button type ="submit" class="submit-btn">Log in</button>
                            <p class="text-danger text-center mt-3">${message}</p>
                        </form>
                        <a href ="./register"><button class="signup-btn">Create an account</button></a>
                    </div>

                

            </div>
            </div>

        
        
        
        
        
        
            <script src="index.js"></script>
        
        
        
        
<!--                <form action="login" method="post">
                    <h1>Sign in</h1>
                    <div class="social-container">
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=profile%20email&redirect_uri=http://localhost:8080/OrderingSystem/login-google&response_type=code
    &client_id=953119864721-91spj7rint3v7cqejchkgqm3m63hgb1i.apps.googleusercontent.com&approval_prompt=force" class="social"><i class="fab fa-google-plus-g"></i></a>
                    </div>
                    <span>or use your account</span>
                    <input type="text" placeholder="User Name" name="user" />
                    <input type="password" placeholder="Password" name="pass"/>
                    <button type = 'submit'>Sign In</button><br>
                    <span><a href = "./forgot-password">Forgot Password?</a></span>
        <p class="text-danger text-center mt-3">${message}</p>
                </form>-->
            
    </body>
    <!-- <br><br>
    <c:if test="${not empty failedMsg}">
        <h5 class="text-center text-danger">${failedMsg}</h5>
        <c:remove var="failedMsg" scope="session"/>
    </c:if>
    <c:if test="${not empty succMsg}">
        <h5 class="text-center text-success">${succMsg}</h5>
        <c:remove var="failedMsg" scope="session"/>
    </c:if>
   
    
</html>
