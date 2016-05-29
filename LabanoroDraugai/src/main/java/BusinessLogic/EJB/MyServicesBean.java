/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ServiceReservationCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.ServicesReservation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Liudas
 */

@Named
@RequestScoped
public class MyServicesBean implements Serializable {
    
    @Inject ServiceReservationCRUD ServiceReservationCRUD;
    @Inject AccountCRUD accountCRUD;
    @Inject LoginBean loginBean;
    
    private String accountFbId;
    private Account acc;
    private List<ServicesReservation> myReservations = new ArrayList();
    
    @PostConstruct
    public void init() {
        accountFbId = loginBean.getFbid();
        if (accountFbId != null) acc = accountCRUD.findAccount(accountFbId);      
        myReservations = ServiceReservationCRUD.getByAccount(acc); 
    }

    public String getAccountFbId() {
        return accountFbId;
    }

    public void setAccountFbId(String accountFbId) {
        this.accountFbId = accountFbId;
    }

    public Account getAcc() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

    public List<ServicesReservation> getMyReservations() {
        return myReservations;
    }

    public void setMyReservations(List<ServicesReservation> myReservations) {
        this.myReservations = myReservations;
    }
}
