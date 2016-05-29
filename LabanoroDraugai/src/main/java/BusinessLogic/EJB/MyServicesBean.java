/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ServiceReservationCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.ServicesReservation;
import DataAccess.JPA.Settings;
import Messages.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;

/**
 *
 * @author Liudas
 */
@Named
@RequestScoped
public class MyServicesBean implements Serializable {

    @Inject
    ServiceReservationCRUD ServiceReservationCRUD;
    @Inject
    AccountCRUD accountCRUD;
    @Inject
    LoginBean loginBean;
    @Inject
    SettingsCRUD settingsCRUD;

    private String accountFbId;
    private Account acc;
    private List<ServicesReservation> myReservations = new ArrayList();

    @PostConstruct
    public void init() {
        accountFbId = loginBean.getFbid();
        if (accountFbId != null) {
            acc = accountCRUD.findAccount(accountFbId);
        }
        myReservations = ServiceReservationCRUD.getByAccount(acc);
    }

    public void cancelReservation(ServicesReservation reservation) {
        try {
            Settings settings = settingsCRUD.findSettings();
            int days = settings.getCancellationTime();

            Date reservationEnd = settings.getCloseReservation();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, days);

            Date today = calendar.getTime();

            if (today.after(reservationEnd)) {
                System.out.println("atejau i rezervacijos atsaukt negalima");
                return;
            }

            ServiceReservationCRUD.removeReservation(reservation);
            myReservations.remove(reservation);
            Message.addSuccessMessage("Rezervacija atšaukta");

        } catch(Exception ex) {
             Message.addErrorMessage("Nesijaudinkite, bet įvyko klaida. Paslauga nebuvo atšaukta. Bandykite dar kartą.");
        }

    }

    public String getAccountFbId() {
        return accountFbId;
    }

    public void setAccountFbId(String accountFbId) {
        this.accountFbId = accountFbId;
    }

    public Account getAcc() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

    public List<ServicesReservation> getMyReservations() {
        return myReservations;
    }

    public void setMyReservations(List<ServicesReservation> myReservations) {
        this.myReservations = myReservations;
    }
}
