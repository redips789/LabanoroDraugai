//
//package Schedules;
//
//import DataAccess.EJB.RecommendationCRUD;
//import DataAccess.EJB.SettingsCRUD;
//import DataAccess.JPA.Recommendation;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import javax.ejb.Schedule;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.inject.Named;
//
//
///**
// *
// * @author Kristaliukas
// */
//
//@Stateless
//@Named
//public class Cleaner {
//    
//    @Inject
//    RecommendationCRUD recommendationEjb;
//    @Inject
//    SettingsCRUD settingsEjb;
//    
//    @Schedule(second = "0", minute = "58", hour = "11", dayOfWeek = "*", persistent = false)
//    public void cleanRecommendationDatabase() {
//        Calendar now = Calendar.getInstance();
//        Date today = new Date();
//        now.setTime(today);
//        now.set(Calendar.HOUR_OF_DAY, 0);
//        now.set(Calendar.MINUTE, 0);
//        now.set(Calendar.SECOND, 0);
//        System.out.println("Patikrinimas, ar veikia");
//        
//        try{
//            int validity_date = settingsEjb.findSettings().getRecommendationsValidity();
//            now.add(Calendar.DATE, -validity_date);
//            Date dat = now.getTime();
//
//            List<Recommendation> deleteList = recommendationEjb.findOldDate(dat);
//
//            for (int i=0; i<deleteList.size(); i++){
//                recommendationEjb.deleteRecommendation(deleteList.get(i));
//            }
//            
//            System.out.println("Patikrinimas, ar vykdo");
//
//        } catch(Exception e) {
//            System.out.println("Klaida automatiškai vykdomame metode: "+e);
//        }  
//    }
//}
