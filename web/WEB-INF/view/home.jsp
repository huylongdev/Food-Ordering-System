<%-- 
    Document   : index
    Created on : Sep 21, 2024, 12:14:27 AM
    Author     : phuct
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Foodie</title>
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
    <link
      rel="stylesheet"
      href="./assets/font/themify-icons/themify-icons.css"
    />
    <link rel="stylesheet" href="./assets/css/style.css">


    </head>
    <body>
            <!-- HEADER -->
    <div id="header">
      <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
          <a class="navbar-brand" href="#">FOODIE</a>
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
              <a class="nav-link" aria-current="page" href="#">Home</a>
              <a class="nav-link" href="#">Food</a>
              <a class="nav-link" href="#">Restaurant</a>
              <a class="nav-link" href="#">Submit</a>
            </div>
            <div class="navbar__item">
              <a class="navbar__item--login" href="/OrderingSystem/login">Login</a>
              <div class="icon">
                <i class="ti-shopping-cart"></i>
              </div>
            </div>
          </div>
        </div>
      </nav>
    </div>

    <!-- SLIDER -->
    <div id="slider">
      <img src="./assets/img/sliderback.svg" alt="" class="sliderback" />
      <div class="slider-info">
        <h3 class="slider-des">Best food in city</h3>
        <h1 class="title">Delivery Food from the Best Restaurants.</h1>
        <div class="slider-search">
          <input class="default-input" type="text" value="Find Food..." />
          <button class="default-btnsearch">Search</button>
        </div>
        <div class="icon-group">
          <i class="ti-facebook slider-icon"></i>
          <i class="ti-instagram slider-icon"></i>
          <i class="ti-location-pin slider-icon"></i>
        </div>
      </div>
    </div>

    <!-- CATEGORY -->
    <div id="category">
      <div class="default-text-category">
        <div class="default-tag default-tag-category">CATEGORIES</div>
      </div>
      <div class="default-title default-title-category">Popular Categories</div>
      <div class="category-list">
        <div onclick="" class="card col-md-2" style="width: 11rem">
          <img
            src="./assets/img/banhmi.png"
            class="card-img-top card-img-top-category"
            alt="..."
          />
          <div class="card-body">
            <h5 class="card-title card-title-category">Food</h5>
            <p class="card-text">20 shop</p>
          </div>
        </div>
        <div onclick="" class="card col-md-2" style="width: 11rem">
          <img
            src="./assets/img/banhmi.png"
            class="card-img-top card-img-top-category"
            alt="..."
          />
          <div class="card-body">
            <h5 class="card-title card-title-category">Food</h5>
            <p class="card-text">20 shop</p>
          </div>
        </div>
        <div onclick="" class="card col-md-2" style="width: 11rem">
          <img
            src="./assets/img/banhmi.png"
            class="card-img-top card-img-top-category"
            alt="..."
          />
          <div class="card-body">
            <h5 class="card-title card-title-category">Food</h5>
            <p class="card-text">20 shop</p>
          </div>
        </div>
        <div onclick="" class="card col-md-2" style="width: 11rem">
          <img
            src="./assets/img/banhmi.png"
            class="card-img-top card-img-top-category"
            alt="..."
          />
          <div class="card-body">
            <h5 class="card-title card-title-category">Food</h5>
            <p class="card-text">20 shop</p>
          </div>
        </div>
        <div onclick="" class="card col-md-2" style="width: 11rem">
          <img
            src="./assets/img/banhmi.png"
            class="card-img-top card-img-top-category"
            alt="..."
          />
          <div class="card-body">
            <h5 class="card-title card-title-category">Food</h5>
            <p class="card-text">20 shop</p>
          </div>
        </div>
        <div onclick="" class="card col-md-2" style="width: 11rem">
          <img
            src="./assets/img/banhmi.png"
            class="card-img-top card-img-top-category"
            alt="..."
          />
          <div class="card-body">
            <h5 class="card-title card-title-category">Food</h5>
            <p class="card-text">20 shop</p>
          </div>
        </div>
      </div>
    </div>

    <!-- RESTAURANTS -->
    <div id="restaurant">
      <div class="default-text-restaurant">
        <div class="default-tag">RESTAURANTS</div>
        <div class="default-title">Restaurants</div>
        <div class="default-des">
          <p class="default-slogan">
            "Discover a world of flavors and enjoy the convenience of ordering
            your favorite meals today!"
          </p>
          <a href="#" class="restaurant-viewall">
            View all <i class="ti-arrow-right"></i>
          </a>
        </div>

        <div class="restaurant-list">
          <!-- Restaurant Card 1 -->
          <div class="card card-restaurant" style="width: 18rem">
            <div class="rating-box">9.8</div>
            <img
              class="card-img-top"
              src="./assets/img/category.png"
              alt="Card image cap"
            />
            <div class="card-body">
              <h5 class="card-title card-title-res">Foodie Restaurant</h5>
              <div class="restaurant-info">
                <p class="res-time">~20-30 min</p>
                <div class="res-category">Pizza</div>
              </div>
            </div>
          </div>

          <!-- Restaurant Card 2 -->
          <div class="card card-restaurant" style="width: 18rem">
            <div class="rating-box">9.8</div>
            <img
              class="card-img-top"
              src="./assets/img/category.png"
              alt="Card image cap"
            />
            <div class="card-body">
              <h5 class="card-title card-title-res">Foodie Restaurant</h5>
              <div class="restaurant-info">
                <p class="res-time">~20-30 min</p>
                <div class="res-category">Pizza</div>
              </div>
            </div>
          </div>

          <!-- Restaurant Card 3 -->
          <div class="card card-restaurant" style="width: 18rem">
            <div class="rating-box">9.8</div>
            <img
              class="card-img-top"
              src="./assets/img/category.png"
              alt="Card image cap"
            />
            <div class="card-body">
              <h5 class="card-title card-title-res">Foodie Restaurant</h5>
              <div class="restaurant-info">
                <p class="res-time">~20-30 min</p>
                <div class="res-category">Pizza</div>
              </div>
            </div>
          </div>

          <!-- Restaurant Card 4 -->
          <div class="card card-restaurant" style="width: 18rem;">
            <!-- Rating -->
            <div class="rating-box">9.8</div>
            
            <img class="card-img-top" src="./assets/img/category.png" alt="Card image cap" />
            <div class="card-body">
              <h5 class="card-title card-title-res">Foodie Restaurant</h5>
              <div class="restaurant-info">
                <p class="res-time">~20-30 min</p>
                <div class="res-category">Pizza</div>
              </div>
            </div>
          </div>
          
        </div>
      </div>
    </div>

    <!-- MAP RESTAURANTS -->
    <div id="map">
      <img src="./assets/img/mapimg.svg" alt="" class="map-img" />
      <div class="map-info col-md-5">
        <div class="map-tag">MAP</div>
        <div class="map-title default-title">
          Food Map with more than 900 Restaurants
        </div>
        <div class="map-des default-slogan">
          Explore a diverse menu from top-rated restaurants, all ready to serve
          you with just a few clicks!
        </div>
        <div class="map-search">
          <input
            class="map-input default-input"
            type="text"
            value="Find Restaurant..."
          />
          <button class="map-btnsearch default-btnsearch">Search</button>
        </div>
      </div>
    </div>

    <!-- BEST DEALS -->
    <div id="bestseller">
      <div class="default-tag default-tag-bestseller">RESTAURANTS</div>
      <div class="default-title">Best Deals</div>
      <div class="default-des">
        <p class="default-slogan">
          "Savor the finest cuisine from the best restaurants around, with easy
          and fast ordering at your fingertips!"
        </p>
        <a href="#" class="restaurant-viewall">
          View all <i class="ti-arrow-right"></i>
        </a>
      </div>
      <div class="restaurant-grid">
        <!-- Card 1 -->
        <div class="card-horizontal col-md-5">
          <img class="card-img" src="./assets/img/banhmi.png" alt="Best Burgers" />
          <div class="card-body">
            <div class="rating">9.8</div>
            <p class="bestseller-category">Burgers</p>
            <h5 class="card-title">Best Burgers</h5>
            <p class="card-text">42 Jong Panchester St, 9073</p>
            <div class="card-info">
              <p class="time">20-30 min</p>
              <p class="price">From $4</p>
            </div>
          </div>
        </div>
      
        <!-- Card 2 -->
        <div class="card-horizontal col-md-5">
          <img class="card-img" src="./assets/img/banhmi.png" alt="Pizza Masters" />
          <div class="card-body">
            <div class="rating">9.8</div>
            <p class="bestseller-category">Burgers</p>
            <h5 class="card-title">Pizza Masters</h5>
            <p class="card-text">Ac St. Schaumburg 84872</p>
            <div class="card-info">
              <p class="time">~45 min</p>
              <p class="price">From $4</p>
            </div>
          </div>
        </div>
      
       <!-- Card 3 -->
       <div class="card-horizontal col-md-5">
        <img class="card-img" src="./assets/img/banhmi.png" alt="Best Burgers" />
        <div class="card-body">
          <div class="rating">9.8</div>
          <p class="bestseller-category">Burgers</p>
          <h5 class="card-title">Best Burgers</h5>
          <p class="card-text">42 Jong Panchester St, 9073</p>
          <div class="card-info">
            <p class="time">20-30 min</p>
            <p class="price">From $4</p>
          </div>
        </div>
      </div>
    
      <!-- Card 4 -->
      <div class="card-horizontal col-md-5">
        <img class="card-img" src="./assets/img/banhmi.png" alt="Pizza Masters" />
        <div class="card-body">
          <div class="rating">9.8</div>
          <p class="bestseller-category">Burgers</p>
          <h5 class="card-title">Pizza Masters</h5>
          <p class="card-text">Ac St. Schaumburg 84872</p>
          <div class="card-info">
            <p class="time">~45 min</p>
            <p class="price">From $4</p>
          </div>
        </div>
      </div>

      <!-- Card 5 -->
      <div class="card-horizontal col-md-5">
        <img class="card-img" src="./assets/img/banhmi.png" alt="Best Burgers" />
        <div class="card-body">
          <div class="rating">9.8</div>
          <p class="bestseller-category">Burgers</p>
          <h5 class="card-title">Best Burgers</h5>
          <p class="card-text">42 Jong Panchester St, 9073</p>
          <div class="card-info">
            <p class="time">20-30 min</p>
            <p class="price">From $4</p>
          </div>
        </div>
      </div>
    
      <!-- Card 6 -->
      <div class="card-horizontal col-md-5">
        <img class="card-img" src="./assets/img/banhmi.png" alt="Pizza Masters" />
        <div class="card-body">
          <div class="rating">9.8</div>
          <p class="bestseller-category">Burgers</p>
          <h5 class="card-title">Pizza Masters</h5>
          <p class="card-text">Ac St. Schaumburg 84872</p>
          <div class="card-info">
            <p class="time">~45 min</p>
            <p class="price">From $4</p>
          </div>
        </div>
      </div>
      </div>
      </div>
    </div>
    <footer id="footer">
        <div class="footer-content">
          <div class="footer-logo">
            <h2>FOODIE</h2>
            <div class="footer-social">
              <a class="icon-footer" href="#"><i class="ti-facebook"></i></a>
              <a class="icon-footer" href="#"><i class="ti-instagram"></i></a>
              <a class="icon-footer" href="#"><i class="ti-location-pin"></i></a>
            </div>
          </div>
          <div class="footer-menu">
            <h4 style="display: flex; justify-content: center;">MENU</h4>
            <div class="menu-item">
                <ul>
                    <li><a href="#">About</a></li>
                    <li><a href="#">Restaurants</a></li>
                  </ul>
                  <ul>
                    <li><a href="#">Map</a></li>
                    <li><a href="#">Submit</a></li>
                  </ul>
            </div>
          </div>
        </div>
      </footer>
    </body>
</html>
