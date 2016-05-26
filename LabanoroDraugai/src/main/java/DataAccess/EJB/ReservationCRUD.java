
package DataAccess.EJB;

import BusinessLogic.EJB.SummerhouseDetails;
import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import static javax.persistence.PersistenceContextType.TRANSACTION;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import static javax.persistence.SynchronizationType.UNSYNCHRONIZED;
import javax.persistence.TypedQuery;

/**
 *
 * @author Liudas 
 */

@Stateless
public class ReservationCRUD {
    
    @PersistenceContext(type=PersistenceContextType.TRANSACTION, synchronization=SynchronizationType.UNSYNCHRONIZED)
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
    
    public void insertReservation (Reservation reservation){
        em.persist(reservation);
    }
    
    public void insertReservation2 (Reservation reservation){
        em.persist(reservation);
        //em.joinTransaction();
        //em.flush();
    }
    
    public boolean isReservationExist(SummerhouseDetails sumD, Reservation reservation) {
        try{
            Query query = em.createQuery("SELECT s FROM Reservation s WHERE s.summerhouseId =:sumId AND s.startDate =:sumStart").setParameter("sumId", sumD.getId()).setParameter("sumStart", reservation.getStartDate());
            
            List<Reservation> reservations = (List<Reservation>) query.getResultList();
            if(reservations.isEmpty())
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
