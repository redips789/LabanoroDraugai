
package DataAccess.EJB;

import DataAccess.JPA.Reservation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static javax.persistence.PersistenceContextType.TRANSACTION;
import javax.persistence.Query;
import static javax.persistence.SynchronizationType.UNSYNCHRONIZED;
import javax.persistence.TypedQuery;

/**
 *
 * @author Liudas 
 */

@Stateless
public class ReservationCRUD {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Reservation> getByAccountId(String accId) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.accountId.id = :accId").
                setParameter("accId", accId);
        return query.getResultList();
    }

}
