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
    
    String name;
    
    //private Date birthday;
    
    //construct
    
    @PostConstruct
    public void init() {
       // System.out.println("inicializuoju");
       fbId = "987654321123"; // kazkaip reiks gauti is sesijos
       account = accountEjb.findAccount(fbId);
      // setName(account.getFirstName());
       System.out.println(this.getName()+ " pirmas");
    }
    
     //get set
    
   

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println(this.getName()+ " nussetintas");
    }

//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Date birthday) {
//        this.birthday = birthday;
//    }
    
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
        a = accountEjb.updateAccount(this.getAccount());
        System.out.println(a.getLastName()+ " BAIGTAA");
        return "myProfile";
    
    }
    
    
}
