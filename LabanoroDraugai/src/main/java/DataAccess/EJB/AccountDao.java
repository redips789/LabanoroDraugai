
package DataAccess.EJB;

import DataAccess.JPA.Account;
import Messages.MessageUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Part;

/**
 *
 * @author Kristaliukas
 */

@Stateless
@LocalBean
public class AccountDao {

    @PersistenceContext
    EntityManager ac;
   
    public List<Account> accounts = new ArrayList<>();
    
    public List<Account> findAllAccounts() {
	List<Account> list = ac.createNamedQuery("Account.findAll").getResultList();
	return list;
    }
    
    public Account findAccount(String fbId){

        System.out.println("--vol 2------------------ "+ fbId);
        return (Account) ac.createNamedQuery("Account.findByFacebookid").setParameter("facebookid", fbId).getResultList().get(0); // nes bus vienintelis
    }
    
    public Account updateAccount(Account changedAccount){
       Account b = ac.merge(changedAccount); // reference to another object than the one passed in when the object was already loaded in the current context.
       return b;
    }
    
    public void updateAccountStatus(String fbid){
        Account acc = findAccount(fbid);
        acc.setStatus("Neaktyvus");
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


    public byte[] findAccountPhoto(String id) { //foto vaizdavimui
        return (byte[]) ac.find(Account.class, id).getPhotoBlob();

    }
}
