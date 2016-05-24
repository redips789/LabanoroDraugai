
package BusinessLogic.EJB;

import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Liudas 
 */

@Named
@RequestScoped
public class SummerhouseDetails implements Serializable {
    
    private int id;   
    private Summerhouse detailedSummerhouse;
    
    @Inject 
    SummerhouseCRUD summerhouseCRUD;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        detailedSummerhouse = summerhouseCRUD.findById(id);
    }

    public Summerhouse getDetailedSummerhouse() {
        return detailedSummerhouse;
    }

    public void setDetailedSummerhouse(Summerhouse detailedSummerhouse) {
        this.detailedSummerhouse = detailedSummerhouse;
    }
}
