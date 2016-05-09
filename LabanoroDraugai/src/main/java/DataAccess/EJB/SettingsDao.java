
package DataAccess.EJB;

import DataAccess.JPA.Settings;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kristaliukas
 */

@Stateless
@LocalBean
public class SettingsDao {
    
    @PersistenceContext
    EntityManager st;

    public Settings findSettings() {
        try {
	    Settings set = (Settings) st.createNamedQuery("Settings.findAll").getSingleResult();	
	    return set;
	} catch (Exception e)
	{
	    System.err.println("Caught manoException: " + "klaiidaaaaa "+  e);
            return null;
	}			
    }
}
