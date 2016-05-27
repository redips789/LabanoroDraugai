
package DataAccess.EJB;

import BusinessLogic.EJB.SummerhouseDetails;
import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import DataAccess.JPA.Summerhouse;
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
    
    public List<Reservation> findByPeriod(Date startDate, Date endDate) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE (r.startDate <=:startDate AND r.endDate >=:startDate) or (r.startDate <=:endDate AND r.endDate >=:endDate)").
                setParameter("startDate", startDate).setParameter("endDate", endDate);
        return query.getResultList();
    }
    
    public void insertReservation (Reservation reservation){
        em.persist(reservation);
        System.out.println(this + ": gavau EntityManager CRUD = " + em.getDelegate());
    }
    
    /*public void insertReservation2 (Reservation reservation){
        em.persist(reservation);
        //em.joinTransaction();
        //em.flush();
    }*/
    
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
    
    public boolean existSimilarReservation(Summerhouse sumD, Date startDate, Date endDate) {
        try {
            Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.summerhouseId =:sumId AND (r.startDate <=:startDate AND r.endDate >=:startDate) or (r.startDate <=:endDate AND r.endDate >=:endDate)").setParameter("sumId", sumD).setParameter("startDate", startDate).setParameter("endDate", endDate);
            List<Reservation> reservations = (List<Reservation>) query.getResultList();
            System.out.println("TIKRINIMAS    *******  "+reservations.size());
            return !reservations.isEmpty();
        }
        catch(Exception ex){
            System.out.println("Iejo i catcha  "+ex);
            return true;
        }
    }

}
