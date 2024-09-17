<%-- 
    Document   : register
    Created on : Sep 17, 2024, 6:22:14 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
        
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <link href="style.css" rel="stylesheet">
    </head>
    <body>
        <section class="h-100 h-custom" style="background-color: #8fc4b7;">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-lg-8 col-xl-6">
        <div class="card rounded-3">
          <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/img3.webp"
            class="w-100" style="border-top-left-radius: .3rem; border-top-right-radius: .3rem;"
            alt="Sample photo">
          <div class="card-body p-4 p-md-5">
            <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2">Registration Info</h3>

            <form class="px-md-2" action="register" method="post">

              <div data-mdb-input-init class="form-outline mb-4">
                  <input type="text" id="form3Example1q" name="fullname" class="form-control" placeholder="Full Name" required />
              </div>

              <div class="row">
                <div class="col-md-6 mb-4">

                  <div data-mdb-input-init class="form-outline datepicker">
                      <input type="text" class="form-control" name="user" id="exampleDatepicker1" placeholder="Username" required />
                  </div>

                </div>
                <div class="col-md-6 mb-4">

                  <div data-mdb-input-init class="form-outline datepicker">
                      <input type="text" class="form-control" name="pass" id="exampleDatepicker1" placeholder="Password" required />
                  </div>

                </div>
              </div>

              <div data-mdb-input-init class="form-outline mb-4">
                  <input type="number" id="form3Example1q"  name="phonenumber" class="form-control" placeholder="Phone Number" required />
              </div>

              <div data-mdb-input-init class="form-outline mb-4">
                  <input type="email" id="form3Example1q" name="email" class="form-control" placeholder="Email" required/>
              </div>
                
                <div data-mdb-input-init class="form-outline mb-4">
                  <input type="text" id="form3Example1q" name="address" class="form-control" placeholder="Address" required/>
              </div>

              <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-success btn-lg mb-1">Submit</button>

            </form>

          </div>
        </div>
      </div>
    </div>
  </div>
</section>
    </body>
</html>
