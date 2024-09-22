<%-- 
    Document   : forgot-password
    Created on : Sep 22, 2024, 9:54:31 PM
    Author     : LENOVO
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="./assets/css/verifyOtp.css">
</head>
<body>
    <section class="vh-100 bg-image" style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
        <div class="mask d-flex align-items-center h-100 gradient-custom-3">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card" style="border-radius: 15px;">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5">Reset Password</h2>
                                <form action="forgot-password" method="post">
                                    <input type="hidden" name="action" value="verifyOTP">
                                    <div class="form-group">
                                        <label for="otp" class="form-label">Enter email:</label>
                                        <input type="email" class="form-control" id="email" name="email" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary mt-4">Next</button>
                                </form>
                                <p class="text-danger text-center mt-3">${message}</p> <!-- Hiển thị thông báo lỗi nếu có -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>
</html>
