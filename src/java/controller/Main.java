/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import context.AccountDAO;
import java.util.List;
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
        List<Account> a = dao.getCustomerList();
        for(Account ac: a){
             System.out.println(ac.getFullName());
        }
        String p = PasswordUtil.hashPassword("huelinh123");
        
        
        String aa = dao.checkAccountByUserName("huelinh").getPassword();
        if(PasswordUtil.checkPassword("huelinh123", aa)){
        System.out.println(aa);
        }
    }
}
