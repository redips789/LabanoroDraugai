/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Settings;
import Services.Email;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;

/**
 *
 * @author Povilas
 */
@Named
@RequestScoped
public class MeritBean implements Serializable {

    private String email = "";
    private int points = 0;
    private String merit = "";
    private Account account = new Account();

    @Inject
    AccountCRUD accountCRUD;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getMerit() {
        return merit;
    }

    public void setMerit(String merit) {
        this.merit = merit;
    }

    public AccountCRUD getAccountCRUD() {
        return accountCRUD;
    }

    public void setAccountCRUD(AccountCRUD accountCRUD) {
        this.accountCRUD = accountCRUD;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @PostConstruct
    public void init() {
    }

    public void sendReward() {
        try {
            Map<String, String> params
                    = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            int accId = Integer.parseInt(params.get("accId"));
            Account acc = accountCRUD.findAccountById(accId);
            acc.setPoints(acc.getPoints() + points);
            accountCRUD.updateAccount(acc);
            //accountCRUD.updatePoints(Integer.parseInt(accId), points);
            String body = Email.createBody(merit);
        } catch (Exception ex) {
            Map<String, String> params
                    = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            int accId = Integer.parseInt(params.get("accId"));
            Account acc = accountCRUD.findAccountById(accId);
            acc.setPoints(acc.getPoints() + points);
            accountCRUD.updateAccount(acc);
        }
        //Email.sendEmail(account.getEmail(), "Nuopelnas", body);
    }
}
