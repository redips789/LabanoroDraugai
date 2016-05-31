/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ServiceReservationCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Services;
import DataAccess.JPA.ServicesReservation;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import Messages.Message;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import static javax.persistence.PersistenceContextType.TRANSACTION;
import javax.persistence.SynchronizationType;
import static javax.persistence.SynchronizationType.SYNCHRONIZED;

/**
 *
 * @author Liudas
 */
@Named
@ViewScoped
public class ServiceReservationBean implements Serializable {

    @Inject
    AccountCRUD accountCRUD;

    @Inject
    ServiceReservationCRUD serviceReservationCRUD;

    @Inject
    LoginBean loginBean;

    private boolean canReserve;

    private ServicesReservation reservation = new ServicesReservation();

    private Services service;

    private Account account;

    @PostConstruct
    public void init() {
        account = accountCRUD.findAccount(loginBean.getFbid());
    }

    public boolean canReserveService() {
        int isPaid = this.compareWithToday(accountCRUD.findAccountById(loginBean.getId()).getNextPayment());
        
        if (isPaid == -1) {
            this.setCanReserve(true);
        } else {
            System.out.println("atejau kur reikia");
            Message.addWarningMessage("Tik sumokėję metinį mokestį galėsite rezervuoti paslaugas!");
            this.setCanReserve(false);
        }
        return this.isCanReserve();
    }

    public int compareWithToday(Date dat) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        Date today = now.getTime();

        int compare = today.compareTo(dat);
        return compare;
    }

    public void showfirstDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('first').show();");
    }

    public void hideFirstDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('first').hide();");
    }

    public void saveReservation() {
        try {
            if (reservation.getStartDate() != null && reservation.getEndDate() != null) {
                if (!reservation.getStartDate().after(reservation.getEndDate())) {
                    reservation.setAccountId(account);
                    reservation.setServiceId(service);
                    reservation.setCost(service.getCost());
                    serviceReservationCRUD.insertReservation(reservation);
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sveikiname", "Paslauga užsakyta!");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    this.hideFirstDialog();
                } else {
                    Message.addErrorMessage("Nurodykite teisingas datas!");
                }
            } else {
                Message.addErrorMessage("Įveskite rezervacijos pradžios ir pabaigos datas!");
            }
        } catch (Exception pe) {
            Message.addErrorMessage("Įvyko nenumatyta klaida. Bandykite dar kartą.");
        }
    }

    public boolean isCanReserve() {
        return canReserve;
    }

    public void setCanReserve(boolean canReserve) {
        this.canReserve = canReserve;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ServicesReservation getReservation() {
        return reservation;
    }

    public void setReservation(ServicesReservation reservation) {
        this.reservation = reservation;
    }

}
