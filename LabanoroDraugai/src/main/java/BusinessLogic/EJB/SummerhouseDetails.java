
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
    
    private String title;   
    private Summerhouse detailedSummerhouse;
    
    @Inject SummerhouseCRUD summerhouseCRUD;
    
//    @PostConstruct
//    public void init() {
//        System.out.println("pavadinimas"+title);
//        detailedSummerhouse = summerhouseCRUD.findByTitle(title);
//    }
    
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
