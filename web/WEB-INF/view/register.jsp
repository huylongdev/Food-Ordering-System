<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>


    <section class="vh-100 bg-image" style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
        <div class="mask d-flex align-items-center h-100 gradient-custom-3">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card" style="border-radius: 15px;">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5">Create an account</h2>
                                <form action="register" method="post">
                                    <div class="form-outline mb-4">
                                        <input type="text" id="fullname" name="fullname" class="form-control form-control-lg" placeholder="Full Name" required />
                                        <!--<label class="form-label" for="fullname">Full Name</label>-->
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="text" id="username" name="user" class="form-control form-control-lg" placeholder="User Name" required />
                                        <!--<label class="form-label" for="username">User Name</label>-->
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="password" id="password" name="pass" class="form-control form-control-lg" placeholder="Password" required />
                                        <!--<label class="form-label" for="password">Password</label>-->
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="number" id="phonenumber" name="phonenumber" class="form-control form-control-lg" placeholder="Phone Number" required />
                                        <!--<label class="form-label" for="phonenumber">Phone Number</label>-->
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="email" id="email" name="email" class="form-control form-control-lg" placeholder="Your Email" required />
                                        <!--<label class="form-label" for="email">Your Email</label>-->
                                    </div>
                                    <div class="form-outline mb-4">
                                        <input type="text" id="address" name="address" class="form-control form-control-lg" placeholder="Address" required />
                                        <!--<label class="form-label" for="address">Address</label>-->
                                    </div>
                                    <input type="hidden" name="action" value="sendOTP" />
                                    <div class="d-flex justify-content-center">
                                        <button type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Send OTP</button>
                                    </div>
                                    <p class="text-danger">${message}</p>
                                    <p class="text-center text-muted mt-5 mb-0">Have already an account? <a href="/OrderingSystem/login" class="fw-bold text-body"><u>Login here</u></a></p>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </section>
</body>
</html>
