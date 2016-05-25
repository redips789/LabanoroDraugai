/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ReservationCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author darbas
 */
@Stateless
public class CancelReservationBean {
    
    @Inject ReservationCRUD reservationCRUD;
    @Inject AccountCRUD accountCRUD;
    
    public void cancelReservation(Reservation reservation){
        int cost = reservation.getCost();
        Account account = reservation.getAccountId();
        account.setPoints(account.getPoints()+cost);
        reservationCRUD.removeReservation(reservation);
    }
}
