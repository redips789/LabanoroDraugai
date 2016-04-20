/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.JPA.Account;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author darbas
 */
@Named
@Stateful
public class LoginAuthBean {
    
    private List<Account> accounts = new ArrayList<Account>();
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    public void init() {
       // System.out.println("inicializuoju");
    }
    
    public void addAccount(Account account){
        em.persist(account);
    }
    
    public List<Account> getAccounts() {
        return accounts;
    }
    
    public List<Account> findAllAccounts() {
        try{
            Query query = em.createQuery("SELECT s FROM Account s");
            return (List<Account>) query.getResultList();
        }
        catch(Exception ex){
            return null;
        }

    } 
    
    
    
    public Account accountExistAccount(String id) {
        try{
            Query query = em.createQuery("SELECT s FROM Account s WHERE s.facebookid = "+id);
            
            List<Account> accounts = (List<Account>) query.getResultList();
            if(accounts.isEmpty())
            {
                return null;
            }
            return accounts.get(0);
        }
        catch(Exception ex){
            return null;
        }

    }
    
    public void deleteAccount(Account account){
        em.remove(em.merge(account));
    }
    
    public boolean accountExistBoolean(String id) {
        try{
            Query query = em.createQuery("SELECT s FROM Account s WHERE s.facebookid = "+id);
            
            List<Account> accounts = (List<Account>) query.getResultList();
            if(accounts.isEmpty())
            {
                return false;
            }
            return true;
        }
        catch(Exception ex){
            return true;
        }

    }
    
}
