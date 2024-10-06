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
console.log("aaa"+shopId);
    document.getElementById('overlayShopID').value = shopId;
}
