
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
public class SettingsCRUD {
    
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
    
    public Settings updateSettings(Settings changedSettings){
       Settings b = st.merge(changedSettings); // reference to another object than the one passed in when the object was already loaded in the current context.
       st.flush();
       return b;
    }
}
