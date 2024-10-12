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
    let productId = input.id;
    amountElement.textContent = formatPrice(amount) + '₫';

    console.log("Calling updateAmount for input with value:", input.value);



    $.ajax({
        url: '/OrderingSystem/cart',
        type: 'POST',
        data: {
            isUpdate: true,
            productId: productId,
            quantity: quantity
        },
        success: function (response) {
            console.log("Cập nhật thành công cho sản phẩm ID:", productId);
            updateTotal();
        },
        error: function (error) {
            console.log("Lỗi khi cập nhật sản phẩm ID:", productId, error);
        }
    });
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

<<<<<<< HEAD

=======
>>>>>>> 1200b1d61b158877619765441255394db8af4180
function submitForm(method) {
    var form = document.getElementById('myForm');
    if (method === 'method1') {
        form.action = './cart';
        form.method = 'post';
    } else if (method === 'method2') {
        form.action = './checkout';
        form.method = 'get';
    }
    form.submit();
}
<<<<<<< HEAD

=======
>>>>>>> 1200b1d61b158877619765441255394db8af4180



