/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */




function increment(button) {
    let input = button.previousElementSibling;
    if (parseInt(input.value) < 10) {
        input.value = parseInt(input.value) + 1;
        updateAmount(input);
    }
}

function decrement(button) {
    let input = button.nextElementSibling;
    if (parseInt(input.value) > 1) {
        input.value = parseInt(input.value) - 1;
        updateAmount(input);
    }
}

function updateAmount(input) {
    let quantity = parseInt(input.value);
    let priceElement = input.closest('.cart-item').querySelector('.price');
    let price = parseInt(priceElement.textContent.split('.')[0]); // Remove dot separator from price
    let amountElement = input.closest('.cart-item').querySelector('.amount');
    let amount = price * quantity;
    amountElement.textContent = formatPrice(amount) + '₫';
    
    console.log("Calling updateAmount for input with value:", input.value);
    updateTotal();
}

function updateTotal() {
    let total = 0;
    document.querySelectorAll('.amount').forEach(amountElement => {
        total += parseInt(amountElement.textContent.replace(/\./g, '')); // Remove dot separator from amount

    });

    document.querySelector('.totals_price').textContent = formatPrice(total) + '₫';
}

function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
}

window.onload = function () {
    updateTotal();
};





function submitForm(method) {
    var form = document.getElementById('myForm');
    if (method === 'method1') {
        form.action = './cart';
        form.method = 'post';
    } else if (method === 'method2') {
        form.action = './order';
        form.method = 'post';
    }
    form.submit();
}
