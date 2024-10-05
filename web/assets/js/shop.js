/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function showAddProductOverlay() {
    document.getElementById("add-product-overlay").style.display = "block";
}

function hideAddProductOverlay() {
    document.getElementById("add-product-overlay").style.display = "none";
}



function addButton(button) {
    const shopId = button.getAttribute('data-shopId');
    document.getElementById('overlayShopID').value = shopId;
}

function updateButton(button) {
    // Lấy các thuộc tính từ button
    const shopId = button.getAttribute('data-shopId');
    const productId = button.getAttribute('data-productId');
    const name = button.getAttribute('data-name');
    const price = button.getAttribute('data-price');
    const category = button.getAttribute('data-category');
    const description = button.getAttribute('data-description');
    const image = button.getAttribute('data-image');
    
    // Gán giá trị cho các trường trong form
    document.getElementById('shopID').value = shopId;
    document.getElementById('productID').value = productId;
    document.getElementById('name').value = name;
    document.getElementById('price').value = price;
    document.getElementById('category').value = category;
    document.getElementById('description').value = description;
    document.getElementById('image').value = image;
}

function showUpdateProductOverlay() {
    document.getElementById("update-product-overlay").style.display = "block";
}

function hideUpdateProductOverlay() {
    document.getElementById("update-product-overlay").style.display = "none";
}