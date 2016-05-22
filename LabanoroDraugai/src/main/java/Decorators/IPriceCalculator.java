/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorators;

import DataAccess.JPA.Account;

/**
 *
 * @author darbas
 */
public interface IPriceCalculator {
    public int calculatePrice(int price, Account account);
}
