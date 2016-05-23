/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorators;

import DataAccess.JPA.Account;
import java.util.Calendar;
import java.util.Date;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

/**
 *
 * @author darbas
 */
@Decorator
public class BirthdayDiscount implements IPriceCalculator {

    
    @Inject
    @Delegate
    private IPriceCalculator calculator;

    @Override
    public int calculatePrice(int price, Account account) {
        Date birth = account.getDateOfBirth();
        Calendar today = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(birth);
        
        int year = today.get(Calendar.YEAR);
        int month = birthday.get(Calendar.MONTH);
        int day = birthday.get(Calendar.DAY_OF_MONTH);
        
        birthday.set(year, month, day);
        
        if(birthday.getTime().after(today.getTime()) || birthday.getTime().before(today.getTime()) ){
            System.out.println("Dekoratorius pavyko");
            return calculator.calculatePrice(price, account);
        }else{
            System.out.println("Dekoratorius pavyko");
            return calculator.calculatePrice(price-10, account);
        }
    }
    
}
