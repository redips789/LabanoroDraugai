
package BusinessLogic.EJB;

import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
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
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB SummerhouseCRUD summerhouseCRUD;
         
    public List<Summerhouse> getSummerhouses() {
        return summerhouseCRUD.findAllSummerhouses();
    }
    
    
     
}
