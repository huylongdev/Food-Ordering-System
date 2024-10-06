/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phuct
 */
public class BillDetail {
    private int billDetailID;
    private int billID;
    private CartItem cartItem; 
    private Product product;    
    private int quantity;
    private double totalPrice;

    public BillDetail() {
    }

    public BillDetail(int billDetailID, int billID, CartItem cartItem, Product product, int quantity, double totalPrice) {
        this.billDetailID = billDetailID;
        this.billID = billID;
        this.cartItem = cartItem;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public BillDetail(int billID, CartItem cartItem, Product product, int quantity, double totalPrice) {
        this.billID = billID;
        this.cartItem = cartItem;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(int billDetailID) {
        this.billDetailID = billDetailID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
