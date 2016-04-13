
package BusinessLogic.EJB;

import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
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
 * @author Liudas 
 */

@Named
@Stateful
public class SummerhouseBean implements Serializable {
    
    public List<Summerhouse> summerhouses = new ArrayList<Summerhouse>();
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    public void init() {
       // System.out.println("inicializuoju");
        summerhouses = findAllSummerhouses();
    }
    
    public List<Summerhouse> getSummerhouses() {
        return summerhouses;
    }
    
    public List<Summerhouse> findAllSummerhouses() {
        Query query = em.createQuery("SELECT s FROM Summerhouse s");
        return (List<Summerhouse>) query.getResultList();
    }    
}
