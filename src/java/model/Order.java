/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Order {
    private int orderId;
    private int userId;
    private String status;
    private String address;
    private Date createdDate;
    private String deliveryOption;
    private Date timePickup;
    private double totalAmount;
    private int discountId;
    private String paymentOption;

    public Order() {
    }

    public Order(int orderId, int userId, String status, String address, Date createdDate, String deliveryOption, Date timePickup, double totalAmount, int discountId, String paymentOption) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.address = address;
        this.createdDate = createdDate;
        this.deliveryOption = deliveryOption;
        this.timePickup = timePickup;
        this.totalAmount = totalAmount;
        this.discountId = discountId;
        this.paymentOption = paymentOption;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public Date getTimePickup() {
        return timePickup;
    }

    public void setTimePickup(Date timePickup) {
        this.timePickup = timePickup;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

}