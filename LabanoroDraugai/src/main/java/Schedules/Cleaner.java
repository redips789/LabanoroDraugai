
package Schedules;

import DataAccess.EJB.RecommendationDao;
import DataAccess.EJB.SettingsDao;
import DataAccess.JPA.Recomendation;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;


/**
 *
 * @author Kristaliukas
 */

@Stateless
@ManagedBean
public class Cleaner {
    
    @EJB
    RecommendationDao recommendationEjb;
    @EJB
    SettingsDao settingsEjb;
    
    @Schedule(second = "0", minute = "0", hour = "3", dayOfWeek = "*")
    public void cleanRecommendationDatabase() {
        Calendar now = Calendar.getInstance();
        Date today = new Date();
        now.setTime(today);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        
        try{
            int validity_date = settingsEjb.findSettings().getRecommendationsValidity();
            now.add(Calendar.DATE, -validity_date);
            Date dat = now.getTime();

            List<Recomendation> deleteList = recommendationEjb.findOldDate(dat);

            for (int i=0; i<deleteList.size(); i++){
                recommendationEjb.deleteRecommendation(deleteList.get(i));
            }
            
            // System.out.println("Patikrinimas, ar vykdo");

        } catch(Exception e) {
            System.out.println("Klaida automatiškai vykdomame metode: "+e);
        }  
    }
}
