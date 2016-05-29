
package Schedules;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.RecommendationCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Recommendation;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;


/**
 *
 * @author Kristaliukas
 */

@Stateless
@Named
public class Cleaner {
    
    @Inject
    RecommendationCRUD recommendationEjb;
    
    @Inject
    SettingsCRUD settingsEjb;
    
    @Inject
    AccountCRUD accountEjb;
    
    @Schedule(second = "0", minute = "55", hour = "13", dayOfWeek = "*", persistent = false)
    public void cleanRecommendationDatabase() {
        Calendar now = Calendar.getInstance();
        Date today = new Date();
        now.setTime(today);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        System.out.println("Patikrinimas, ar veikia");
        
        try{
            int validity_date = settingsEjb.findSettings().getRecommendationsValidity();
            now.add(Calendar.DATE, -validity_date);
            Date dat = now.getTime();

            List<Recommendation> deleteList = recommendationEjb.findOldDate(dat);

            for (int i=0; i<deleteList.size(); i++){
                recommendationEjb.deleteRecommendation(deleteList.get(i));
            }
            
            System.out.println("Patikrinimas, ar vykdo");

        } catch(Exception e) {
            System.out.println("Klaida automatiškai vykdomame metode: "+e);
        }  
    }
    
    @Schedule(second = "0", minute = "0", hour = "0", month="1", dayOfMonth="1", year ="*" , persistent = false)
    private void endOfSeason(){
        List<Account> tempList = accountEjb.findAllAccounts();
        System.out.println("Atnaujina visų vartotojų sąrašą - rezervuotų dienų skaičių perkelia į praleistų dienų skaičių");
        for(Account item : tempList){
            item.setTimeSpentOnHoliday(item.getReservedDays());
            item.setReservedDays(0);
            accountEjb.updateAccount(item);
	}
    }
}
