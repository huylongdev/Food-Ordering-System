/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import context.AccountDAO;
import model.Account;
import util.PasswordUtil;

/**
 *
 * @author LENOVO
 */
public class Main {

    // check ket noi database successfully
    public static void main(String[] args) {

        AccountDAO dao = new AccountDAO();
        dao.createShopAccount(new Account("shop3", PasswordUtil.hashPassword("123"), "Nguyen Hue Linh", "0389898742", "nhs211306@gmail.com", "Ngu Hanh Son - Da Nang", 3, 2));
    
    }
}
