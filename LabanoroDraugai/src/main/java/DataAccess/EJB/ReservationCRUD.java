
package DataAccess.EJB;

import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import java.util.Date;
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
    
    public List<Reservation> getByAccount(Account acc) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.accountId = :acc").
                setParameter("acc", acc);
        return query.getResultList();
    }
    
    public List<Reservation> findByPeriod(Date from, Date to) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.startDate >= :from AND r.endDate <= :to").
                setParameter("from", from).setParameter("to", to);
        return query.getResultList();
    }

}
