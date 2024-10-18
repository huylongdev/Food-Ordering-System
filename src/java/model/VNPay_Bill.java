/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author phuct
 */
public class VNPay_Bill {

    private String vnpTxnRef;            // vnp_TxnRef
    private float vnpAmount;         // vnp_Amount
    private String vnpPayDate;        // vnp_PayDate
    private String vnpTransactionStatus;  // vnp_TransactionStatus

    public VNPay_Bill() {
    }

    public VNPay_Bill(String vnpTxnRef, float vnpAmount, String vnpPayDate, String vnpTransactionStatus) {
        this.vnpTxnRef = vnpTxnRef;
        this.vnpAmount = vnpAmount;
        this.vnpPayDate = vnpPayDate;
        this.vnpTransactionStatus = vnpTransactionStatus;
    }

    public String getVnpTxnRef() {
        return vnpTxnRef;
    }

    public void setVnpTxnRef(String vnpTxnRef) {
        this.vnpTxnRef = vnpTxnRef;
    }

    public float getVnpAmount() {
        return vnpAmount;
    }

    public void setVnpAmount(float vnpAmount) {
        this.vnpAmount = vnpAmount;
    }

    public String getVnpPayDate() {
        return vnpPayDate;
    }

    public void setVnpPayDate(String vnpPayDate) {
        this.vnpPayDate = vnpPayDate;
    }

    public String getVnpTransactionStatus() {
        return vnpTransactionStatus;
    }

    public void setVnpTransactionStatus(String vnpTransactionStatus) {
        this.vnpTransactionStatus = vnpTransactionStatus;
    }

    
}
