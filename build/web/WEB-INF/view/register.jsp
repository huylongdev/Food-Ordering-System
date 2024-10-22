<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign up</title>
        <link rel="stylesheet" href="./index.css">
        <link rel="stylesheet" href="themify-icons-font\themify-icons\themify-icons.css">
    </head>
    <body>
        <div class="form-structor">
            <div class="signup">
                <h2 class="form-title" id="signup">Create an account</h2>
                <div class="form-holder">
                    <form action="register" method="post">

                        <input type="hidden" name="action" value="register">

                        <input type="text" class="input" placeholder="Full Name" id="fullname" name="fullname" required>


                        <input type="text" class="input" placeholder="UserName" id="username" name="username" required>

                        <input type="password" class="input" placeholder="Password"id="password" name="password" required>


                        <input type="text" class="input" placeholder="Phone Number" id="phonenumber" name="phonenumber" required>


                        <!--<input type="text" class="input" id="address" name="address" >-->


                        <input type="email" class="input" placeholder="Email" id="email" name="email" required>
                        </div>

                        <button type="submit" class="submit-btn">Register</button>
                    </form>
                    <p class="text-danger">${message}</p>

                </div>



            </div>
            <script src="index.js"></script>
    </body>
</html>
