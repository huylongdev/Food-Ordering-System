package model;

import java.util.Date;

public class Discount {

    private int discountID;
    private int userID;
    private String discountCODE;
    private int numberOfDiscount;
    private int totalUse;
    private double discountPercentage;
    private int ShopID;
    private int status;

    public Discount() {
    }

    public Discount(int discountID, int userID, String discountCODE, int numberOfDiscount, int totalUse, double discountPercentage, int ShopID, int status) {
        this.discountID = discountID;
        this.userID = userID;
        this.discountCODE = discountCODE;
        this.numberOfDiscount = numberOfDiscount;
        this.totalUse = totalUse;
        this.discountPercentage = discountPercentage;
        this.ShopID = ShopID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
    public int getShopID() {
        return ShopID;
    }

    public void setShopID(int ShopID) {
        this.ShopID = ShopID;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDiscountCODE() {
        return discountCODE;
    }

    public void setDiscountCODE(String discountCODE) {
        this.discountCODE = discountCODE;
    }

    public int getNumberOfDiscount() {
        return numberOfDiscount;
    }

    public void setNumberOfDiscount(int numberOfDiscount) {
        this.numberOfDiscount = numberOfDiscount;
    }

    public int getTotalUse() {
        return totalUse;
    }

    public void setTotalUse(int totalUse) {
        this.totalUse = totalUse;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
