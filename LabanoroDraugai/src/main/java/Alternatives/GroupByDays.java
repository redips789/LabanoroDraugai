/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Alternatives;

import DataAccess.JPA.Account;
import DataAccess.JPA.Settings;
import Messages.Message;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

/**
 *
 * @author darbas
 */
@Alternative
@Stateless
public class GroupByDays implements GroupDistribution, Serializable {
    /**
     *
     * @param account
     * @param settings
     * @return
     */
    @Override
    public boolean canGroupReserve(Account account, Settings settings) {
        int first = settings.getFirstGroupSize();
        int second = settings.getSecondGroupSize();
        int group;
        boolean canReserve = false;
        Date reservationDay = new Date();

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        Date today = now.getTime();
        
        // grupės priskyrimas
        if (account.getTimeSpentOnHoliday()<=first) group = 1;
        else if (account.getTimeSpentOnHoliday()>first && account.getTimeSpentOnHoliday()<= second) group = 2;
        else group = 3;
        
        // rezervacijos datos priskyrimas
        switch (group) {
            case 1:  reservationDay = settings.getFirstReservation();
                     break;
            case 2:  reservationDay = settings.getSecondReservation();
                     break;
            case 3:  reservationDay = settings.getThirdReservation();
                     break;
        }
        
        // patikrinimas, ar leisti įeiti į rezervaciją
        int compare_begin = today.compareTo(reservationDay);
        int compare_end = today.compareTo(settings.getCloseReservation());
        canReserve = (compare_begin == 0 || compare_begin == 1) && (compare_end == 0 || compare_end == -1);
        
        if(!canReserve){
            Message.addWarningMessage("Kol kas neatėjo Jūsų grupės rezervacijos laikas!");
        }
        
        return canReserve; 
    }

    

    

  
}
