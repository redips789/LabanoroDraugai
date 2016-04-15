
package DataAccess.EJB;

import DataAccess.JPA.Account;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

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
}
