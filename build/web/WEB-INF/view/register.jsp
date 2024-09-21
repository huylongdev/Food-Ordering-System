<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign up</title>
        <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
    <script type="text/javascript" src="https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/js/dist/mdb5/standard/core.min.js"></script>
    <script type="text/javascript" src="https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/js/dist/search/search.min.js"></script>
    <script src="https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/js/dist/main.min.js"></script>
    <link rel="stylesheet" href="https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/css/dist/mdb5/standard/core.min.css">
    <link rel="stylesheet" id="roboto-subset.css-css" href="https://mdbcdn.b-cdn.net/wp-content/themes/mdbootstrap4/docs-app/css/mdb5/fonts/roboto-subset.css?ver=3.9.0-update.5" type="text/css" media="all">
    <link rel="stylesheet" href="./assets/css/register.css">
</head>
<body>
    <section class="vh-100 bg-image"
      style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
      <div class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
          <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-9 col-lg-7 col-xl-6">
              <div class="card" style="border-radius: 15px;">
                <div class="card-body p-5">
                  <h2 class="text-uppercase text-center mb-5">Create an account</h2>

                  <form action="register" method="post">
    <div data-mdb-input-init class="form-outline mb-4">
        <input type="text" id="fullname" name="fullname" class="form-control form-control-lg" placeholder="Full Name" required />
        <label class="form-label" for="fullname">Full Name</label>
    </div>
      
    <div data-mdb-input-init class="form-outline mb-4">
        <input type="text" id="username" name="user" class="form-control form-control-lg" placeholder="User Name" required />
        <label class="form-label" for="username">User Name</label> <!-- Sửa id cho label -->
    </div>
      
    <div data-mdb-input-init class="form-outline mb-4">
        <input type="password" id="password" name="pass" class="form-control form-control-lg" placeholder="Password" required />
        <label class="form-label" for="password">Password</label>
    </div>
    
    <div data-mdb-input-init class="form-outline mb-4">
        <input type="number" id="phonenumber" name="phonenumber" class="form-control form-control-lg" placeholder="Phone Number" required />
        <label class="form-label" for="phonenumber">Phone Number</label> <!-- Sửa id cho label -->
    </div>  

    <div data-mdb-input-init class="form-outline mb-4">
        <input type="email" id="email" name="email" class="form-control form-control-lg" placeholder="Your Email" required />
        <label class="form-label" for="email">Your Email</label>
    </div>

    <div data-mdb-input-init class="form-outline mb-4">
        <input type="text" id="address" name="address" class="form-control form-control-lg" placeholder="Address" required />
        <label class="form-label" for="address">Address</label> <!-- Sửa id cho label -->
    </div>  

    <div class="d-flex justify-content-center">
        <button type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Register</button>
    </div>

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
