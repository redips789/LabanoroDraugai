/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorators;

import DataAccess.JPA.Account;
import javax.ejb.Stateless;

/**
 *
 * @author darbas
 */
@Stateless
public class CalculatorBean implements IPriceCalculator {

    @Override
    public int calculatePrice(int price, Account account) {
        return price;
    }
    
}
