/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function submitForm(method) {
    var form = document.getElementById('myForm'); 
    
    if (method === 'method1') { 
        form.action = './order?action=checkVoucher';  
        form.method = 'GET';
    } else if (method === 'method2') {
        form.action = './order?action=deleteOrder';  
        form.method = 'GET';
    } else if (method === 'method3') {
        form.action = './order?action=placeAnOrder';  
        form.method = 'GET';
    }
    
    form.submit();
}


