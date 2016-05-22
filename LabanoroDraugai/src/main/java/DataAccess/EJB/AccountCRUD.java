
package DataAccess.EJB;

import DataAccess.JPA.Account;
import Interceptors.LogInterceptor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Kristaliukas
 */

@Stateless
public class AccountCRUD {

    @PersistenceContext
    EntityManager ac;
   
    public List<Account> accounts = new ArrayList<>();
    
    public List<Account> findAllAccounts() {
	List<Account> list = ac.createNamedQuery("Account.findAll").getResultList();
	return list;
    }
    
    public Account findAccount(String fbId){
        try {
            Account acc = (Account) ac.createNamedQuery("Account.findByFacebookid").setParameter("facebookid", fbId).getResultList().get(0);
            return acc;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Account findAccountById(int id){
        return (Account) ac.createNamedQuery("Account.findById").setParameter("id", id).getSingleResult();
    }
    
    public Account findAccountByEmail(String email){
        try {
            Account acc = (Account) ac.createNamedQuery("Account.findByEmail").setParameter("email", email).getSingleResult();
            return acc;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Account updateAccount(Account changedAccount){
       Account b = ac.merge(changedAccount); // reference to another object than the one passed in when the object was already loaded in the current context.
       return b;
    }
    
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateAccountStatus(int id){
        Account acc = findAccountById(id);
        acc.setStatus("Narys");
        Calendar now = Calendar.getInstance();
        Date today = new Date();
        now.setTime(today);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        acc.setPoints(500);
        acc.setMemberSince(now.getTime());
        acc.setNextPayment(now.getTime());
        ac.persist(acc);
	ac.flush();
    }
    
    private String error="none";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    @PostConstruct
    public void init() {
       // System.out.println("inicializuoju");
    }
    
    public void addAccount(Account account){
        ac.persist(account);
    }
    
    public List<Account> getAccounts() {
        return accounts;
    }
    
    public Account accountExistAccount(String id) {
        try{
            Query query = ac.createQuery("SELECT s FROM Account s WHERE s.facebookid =:fbid ").setParameter("fbid", id);
            
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
        ac.remove(ac.merge(account));
    }
    
    public boolean accountExistBoolean(String id) {
        try{
            Query query = ac.createQuery("SELECT s FROM Account s WHERE s.facebookid =:fbid ").setParameter("fbid", id);
            
            List<Account> accounts = (List<Account>) query.getResultList();
            if(accounts.isEmpty())
            {
                return false;
            }
            return true;
        }
        catch(Exception ex){
            setError(ex.getMessage());
            return true;
        }
    }

//    public byte[] findAccountPhoto(String id) { //foto vaizdavimui
//        return (byte[]) ac.find(Account.class, id).getPhotoBlob();
//
//    }
}
