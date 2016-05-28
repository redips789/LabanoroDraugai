//package Schedules;
//
///**
// *
// * @author ADMIN
// */
//
//import DataAccess.EJB.ReservationCRUD;
//import DataAccess.EJB.SettingsCRUD;
//import java.util.Calendar;
//import java.util.Date;
//import javax.ejb.Schedule;
//import javax.inject.Inject;
//import javax.inject.Named;
//
//@Named
//public class ResetReservations {
//    
//    @Inject SettingsCRUD settingCRUD;
//    
//    @Inject ReservationCRUD resrvationCRUD;
//    
//    
//    @Schedule(second = "0", minute = "55", hour = "13", dayOfWeek = "*", persistent = false)
//    public void resetRecommendations() {
//        Calendar now = Calendar.getInstance();
//        Date today = new Date();
//        now.setTime(today);
//        now.set(Calendar.HOUR_OF_DAY, 0);
//        now.set(Calendar.MINUTE, 0);
//        now.set(Calendar.SECOND, 0);
//        System.out.println("resetRecommentations iskviestas");
//        
//    }
//    
//}
