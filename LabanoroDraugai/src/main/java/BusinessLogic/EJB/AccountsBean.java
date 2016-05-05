/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
   private Account selectedAccount;

    public List<Account> getAccountList() {
        this.loadAccounts();
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void loadAccounts() {
        this.accountList = accountEjb.findAllAccounts();
        for(Account item : accountList){
		item.setAge(estimateAge(item.getDateOfBirth()));
	}
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
    
    private int estimateAge(Date bday) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(bday);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        //System.out.println(account.getDateOfBirth().getDate() + " seni geri metai" + today.get(Calendar.DAY_OF_MONTH) + " mėn" + today.get(Calendar.DAY_OF_WEEK_IN_MONTH) + " diena" + today.get(Calendar.DAY_OF_WEEK) + " ssav diena");
        return age;
    }
    
    
}
