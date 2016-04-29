
package DataAccess.EJB;

import DataAccess.JPA.Account;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
        return (Account) ac.createNamedQuery("Account.findByFacebookid").setParameter("facebookid", fbId).getResultList().get(0); // nes bus vienintelis
    }
    
    public Account updateAccount(Account changedAccount){
       Account b = ac.merge(changedAccount); // reference to another object than the one passed in when the object was already loaded in the current context.
       ac.flush();
       return b;
    }
    public byte[] findAccountPhoto(String id) { //foto vaizdavimui
        return (byte[]) ac.find(Account.class, id).getPhotoBlob();
    }
}
