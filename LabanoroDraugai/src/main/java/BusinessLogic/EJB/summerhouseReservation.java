
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Liudas 
 */
@Named
@RequestScoped
public class summerhouseReservation implements Serializable {
    
    @Inject AccountCRUD accountCRUD;
    

}
