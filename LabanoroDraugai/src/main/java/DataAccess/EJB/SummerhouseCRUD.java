
package DataAccess.EJB;

import DataAccess.JPA.Summerhouse;
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
public class SummerhouseCRUD {
    
    @PersistenceContext(type=TRANSACTION, synchronization=UNSYNCHRONIZED)
    private EntityManager em;
    
    @PersistenceContext
    EntityManager sh;
    
    public Summerhouse findById(int id) {
        Query query = em.createNamedQuery("Summerhouse.findById", Summerhouse.class).
                setParameter("id", id);
        return (Summerhouse) query.getSingleResult();
    }
    
    public List<Summerhouse> findAllSummerhouses() {
        Query query = em.createQuery("SELECT s FROM Summerhouse s");
        return (List<Summerhouse>) query.getResultList();
    }
    
    public Summerhouse findByTitle(String title) {
        Query query = em.createQuery("SELECT s FROM Summerhouse s WHERE s.title = :title").setParameter("title", title);
        return (Summerhouse) query.getSingleResult();
    }
    
    public List<Summerhouse> findByPrice(double priceFrom, double priceTo) {
        Query query = em.createQuery("SELECT s FROM Summerhouse s WHERE s.cost >= :priceFrom AND s.cost <= :priceTo")
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo);
        return (List<Summerhouse>) query.getResultList();
    }
    
    public List<Summerhouse> findByPriceFrom(double priceFrom) {
        Query query = em.createQuery("SELECT s FROM Summerhouse s WHERE s.cost >= :priceFrom").setParameter("priceFrom", priceFrom);
        return (List<Summerhouse>) query.getResultList();
    }
    
    public List<Summerhouse> findByPriceTo(double priceTo) {
        Query query = em.createQuery("SELECT s FROM Summerhouse s WHERE s.cost <= :priceTo").setParameter("priceTo", priceTo);
        return (List<Summerhouse>) query.getResultList();
    }
    
    public void addSummerhouse(Summerhouse summerhouse){
	sh.persist(summerhouse);
	sh.flush();
    }
}
