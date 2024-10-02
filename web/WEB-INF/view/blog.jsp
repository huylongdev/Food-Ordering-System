<%-- 
    Document   : blog
    Created on : Oct 2, 2024, 1:47:43 PM
    Author     : phuct
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
     <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Foodie Blog</title>
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
    <script src="/js/blog.js"></script>
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet" href="./assets/css/blog.css" />
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
              <a class="nav-link" aria-current="page" href="/OrderingSystem/">Home</a>
              <a class="nav-link" href="/OrderingSystem/food">Food</a>
              <a class="nav-link" href="/OrderingSystem/restaurant">Restaurant</a>
              <a class="nav-link" href="/OrderingSystem/blog">Blog</a>
            </div>
            <div class="navbar__item">
              <a class="navbar__item--login" href="">Login</a>
              <div class="icon">
                <i class="ti-shopping-cart"></i>
              </div>
            </div>
          </div>
        </div>
      </nav>
    </div>

    <!-- PAGE INFO -->
    <div id="page-info">
      <div class="page-title">Foodie Community Blog</div>
      <div class="page-info-more">
        <a href="./">Home</a>
        <a style="border-left: 1px solid #e8e8ea" href="#">Post Management</a>
      </div>
    </div>

    <!-- SLIDER IMG -->
    <div id="slider">
      <img class="slider-img" src="./assets/img/image.png" alt="Slider" />
      <div class="title">
        <h2 style="color: white; width: 90%">
          The Impact of Technology on the Workplace: How Technology is Changing
        </h2>
        <div class="slider-author">
          <img src="./assets/img/image.png" alt="" class="author_avatar" />
          <p class="author_name">Tran Phuc Tien</p>
        </div>
      </div>
    </div>

    <!-- CONTENT -->
    <div id="content">
      <!-- Post Row 1 -->
      <div class="post-row content-load">
        <!-- Post Card 1 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>

        <!-- Post Card 2 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>

        <!-- Post Card 3 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>
      </div>

      <!-- Post Row 2 -->
      <div class="post-row content-load">
        <!-- Post Card 1 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>

        <!-- Post Card 2 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>

        <!-- Post Card 3 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>
      </div>

      <!-- Post Row 3 -->
      <div class="post-row content-load">
        <!-- Post Card 1 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>

        <!-- Post Card 2 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>

        <!-- Post Card 3 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>
      </div>

      <!-- Post Row 4 -->
      <div class="post-row content-load">
        <!-- Post Card 1 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>

        <!-- Post Card 2 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>

        <!-- Post Card 3 -->
        <a
          class="card card-post content"
          href="/OrderingSystem/blogdetails"
          style="width: 35%"
        >
          <img
            class="card-img-top card-img-post"
            src="./assets/img/category.png"
            alt="Card image cap"
          />
          <div class="card-body">
            <h5 class="card-title card-title-post">
              The Impact of Technology on the Workplace: How Technology is
              Changing
            </h5>
            <div class="card-post-author">
              <img
                src="./assets/img/image.png"
                alt=""
                class="author-post-avatar"
              />
              <p class="author_name author-post-name">Tran Phuc Tien</p>
            </div>
          </div>
        </a>
      </div>

      <a href="#" id="loadMore">Load More</a>
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
          <h4 style="display: flex; justify-content: center">MENU</h4>
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
  <script>
      document.addEventListener("DOMContentLoaded", function () {
  let loadMoreButton = document.getElementById("loadMore");
  let posts = document.querySelectorAll(".content-load");
  let currentPosts = 3; // Số lượng bài viết hiển thị ban đầu

  // Ẩn tất cả bài viết sau số lượng currentPosts
  for (let i = currentPosts; i < posts.length; i++) {
    posts[i].style.display = "none";
  }

  // Xử lý sự kiện khi nhấn vào nút "Load More"
  loadMoreButton.addEventListener("click", function (e) {
    e.preventDefault();
    let nextPosts = currentPosts + 2; // Số lượng bài viết cần tải thêm
    for (let i = currentPosts; i < nextPosts && i < posts.length; i++) {
      posts[i].style.display = "flex";
    }
    currentPosts += 2;

    // Kiểm tra nếu đã hiển thị hết bài viết thì ẩn nút "Load More"
    if (currentPosts >= posts.length) {
      loadMoreButton.style.display = "none";
    }
  });
});
  </script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>
