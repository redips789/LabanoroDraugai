/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author darbas
 */
@ManagedBean(name = "accountsBean")
@RequestScoped
public class AccountsBean {
    
    @EJB
    AccountDao accountEjb;
   private List<Account> accountList = new ArrayList<>();

    public List<Account> getAccountList() {
        this.loadAccounts();
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void loadAccounts() {
        this.accountList = accountEjb.findAllAccounts();
    } 
}
