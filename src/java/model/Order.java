package model;

import java.util.Date;

public class Order {

    private int orderId;
    private Account user;
    private String address;
    private Date createdDate;
    private double totalAmount;
    private String paymentOption;
    private String status;
    private String createdDateString;
    private String deliveryOption;  
    private Date timePickup;        
    private int discountId;   

    public Order() {
    }

    public Order(int orderId, Account user, String address, Date createdDate, double totalAmount, String paymentOption) {
        this.orderId = orderId;
        this.user = user;
        this.address = address;
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
        this.paymentOption = paymentOption;
    }

    public Order(int orderID, Account user, double totalAmount, String paymentOption, String status, String address, String createdDateString) {
        this.orderId = orderID;
        this.user = user;
        this.totalAmount = totalAmount;
        this.paymentOption = paymentOption;
        this.status = status;
        this.address = address;
        this.createdDateString = createdDateString;
    }

    public Order(int orderId, Account user, String address, Date createdDate, double totalAmount, String paymentOption, String status, String createdDateString, String deliveryOption, Date timePickup, int discountId) {
        this.orderId = orderId;
        this.user = user;
        this.address = address;
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
        this.paymentOption = paymentOption;
        this.status = status;
        this.createdDateString = createdDateString;
        this.deliveryOption = deliveryOption;
        this.timePickup = timePickup;
        this.discountId = discountId;
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

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }
    
    
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDateString() {
        return createdDateString;
    }

    public void setCreatedDateString(String createdDateString) {
        this.createdDateString = createdDateString;
    }
}
