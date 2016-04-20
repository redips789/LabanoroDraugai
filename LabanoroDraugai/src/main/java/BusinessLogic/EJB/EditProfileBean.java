/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.beans.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
//import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author Laurute
 */
//@Named
//@SessionScoped
//@Stateful
@ManagedBean
@RequestScoped
public class EditProfileBean implements Serializable {
    @EJB
    AccountDao accountEjb;
    
   // @PersistenceContext(type=PersistenceContextType.EXTENDED)veliau suzinosiu
    @PersistenceContext
    private EntityManager em;
    
    private Account account;
    
    String fbId;
    
    private int accountAge;
    private String accountPhoneNumber;
    
    //construct
    
    @PostConstruct
    public void init() {
       fbId = "579849092162769"; // kazkaip REIKS gauti is sesijos
       account = accountEjb.findAccount(fbId);
       System.out.println(this.account.getFirstName()+ " inicijuota");
    }
    
     //get set
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccountAge() {
        estimateAge();
        return accountAge +"  metų,  ";
    }

    public String getAccountPhoneNumber() {
        return "+370 " + this.account.getPhoneNum();
    }
    
    
    
     //business  
    public String saveAccountChanges(){
        System.out.println(this.getAccount().getLastName()+ "Pradedu last");
        System.out.println(this.getAccount().getFirstName()+ "Pradedu first");    
        System.out.println(this.getAccount().getDateOfBirth() + "Pradedu bday"); 
        System.out.println(this.getAccount().getEmail() + "Pradedu email");
        System.out.println(this.getAccount().getPhoneNum()+ "Pradedu phone");
        System.out.println(this.getAccount().getCity()+ "Pradedu city");
        System.out.println(this.getAccount().getDescription()+ "Pradedu description");
        Account a = new Account();
        a = accountEjb.updateAccount(this.getAccount());    //LUŠ kai updatins į esantį email
        System.out.println(a.getLastName()+ " BAIGTA");
        return "myProfile";
    }
    
    private void estimateAge(){
        Calendar dob = Calendar.getInstance();
        dob.setTime(this.account.getDateOfBirth());
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR))
        {age--;}
        System.out.println(this.account.getDateOfBirth().getDate() + " seni geri metai" + today.get(Calendar.DAY_OF_MONTH)+ " mėn" + today.get(Calendar.DAY_OF_WEEK_IN_MONTH) + " diena" + today.get(Calendar.DAY_OF_WEEK) + " ssav diena");
        this.accountAge = age;
    }
        
}
