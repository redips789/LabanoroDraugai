
package BusinessLogic.EJB;

import java.io.Serializable;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Liudas 
 */
public class summerhouseReservation implements Serializable {
    
    @ManagedProperty(value="#{loginBean}")
    LoginBean loginBean;

}
