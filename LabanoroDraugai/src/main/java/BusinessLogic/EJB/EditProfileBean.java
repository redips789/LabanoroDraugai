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
    
    //construct
    
    @PostConstruct
    public void init() {
       fbId = "987654321123"; // kazkaip REIKS gauti is sesijos
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
    
    
}
