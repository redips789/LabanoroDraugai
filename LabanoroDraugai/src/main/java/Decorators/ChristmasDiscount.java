/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorators;

import DataAccess.JPA.Account;
import java.util.Calendar;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

/**
 *
 * @author darbas
 */
@Decorator
public class ChristmasDiscount implements IPriceCalculator {

    
    @Inject
    @Delegate
    private IPriceCalculator calculator;
    
    @Override
    public int calculatePrice(int price, Account account) {
        Calendar today = Calendar.getInstance();
        Calendar christmas = Calendar.getInstance();
        
        int year = today.get(Calendar.YEAR);
        int month = 12;
        int day = 25;
        christmas.set(year, month, day);
        
        if(christmas.getTime().after(today.getTime()) || christmas.getTime().before(today.getTime()) ){
            System.out.println("Dekoratorius pavyko");
            return calculator.calculatePrice(price, account);
        }else{
            System.out.println("Dekoratorius pavyko");
            return calculator.calculatePrice(price-15, account);
        }
    }
    
}
