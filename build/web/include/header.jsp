<%-- 
    Document   : header
    Created on : Oct 3, 2024, 1:38:49 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- HEADER -->
    <div id="header">
      <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
          <a class="navbar-brand" href="./">FOODIE</a>
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
              <a class="navbar__item--login" href="./account">Account</a>
              <a href = "./cart"><div class="icon">
                <i class="ti-shopping-cart"></i>
              </div></a>
            </div>
          </div>
        </div>
      </nav>
    </div>
