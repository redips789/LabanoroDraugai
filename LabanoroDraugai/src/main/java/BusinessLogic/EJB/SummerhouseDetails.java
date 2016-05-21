
package BusinessLogic.EJB;

import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author Liudas 
 */

//@SessionScoped
public class SummerhouseDetails implements Serializable {
    
    private String title;   
    private Summerhouse detailedSummerhouse;
    
    @Inject SummerhouseCRUD summerhouseCRUD;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        detailedSummerhouse = summerhouseCRUD.findByTitle(title);
    }

    public Summerhouse getDetailedSummerhouse() {
        return detailedSummerhouse;
    }

    public void setDetailedSummerhouse(Summerhouse detailedSummerhouse) {
        this.detailedSummerhouse = detailedSummerhouse;
    }
}
