package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ReservationCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import DataAccess.JPA.Settings;
import DataAccess.JPA.Summerhouse;
import Interceptors.Interceptable;
import Messages.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Liudas
 */
@RequestScoped
@Named
@Stateful
public class MySummerhouses implements Serializable {
    
    @PersistenceContext(type=PersistenceContextType.TRANSACTION, synchronization=SynchronizationType.UNSYNCHRONIZED) 
    private EntityManager em;
    
    @Inject
    SummerhouseCRUD summerhouseCRUD;
    @Inject
    ReservationCRUD reservationCRUD;
    @Inject
    AccountCRUD accountCRUD;
    @Inject
    CancelReservationBean cancelReservationBean;
    @Inject
    SettingsCRUD settingsCRUD;

    @Inject
    LoginBean loginBean;

    private String accountFbId;
    private Account acc;
    private List<Reservation> myReservations = new ArrayList();

    @PostConstruct
    public void init() {
        accountFbId = loginBean.getFbid();
        if (accountFbId != null) {
            acc = accountCRUD.findAccount(accountFbId);
        }
        myReservations = reservationCRUD.getByAccount(acc);
    }

    @Interceptable
    public void cancelReservation(Reservation reservation) {
        try {
            

            Settings settings = settingsCRUD.findSettings();

            int days = settings.getCancellationTime();
            Date reservtionEnd = settings.getCloseReservation();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, days);

            Date today = calendar.getTime();

            if (today.after(reservtionEnd)) {
                Message.addErrorMessage("Rezervacijos atšaukti nebegalima.");
                return;
            }
            cancelReservationBean.cancelReservation(reservation);
            myReservations.remove(reservation);
            em.joinTransaction();
            em.flush();
            Account account = accountCRUD.findAccount(loginBean.getFbid());
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("interceptorAccount", account);
        } catch (Exception ex) {
            Message.addErrorMessage("Nesijaudinkite, bet įvyko klaida ir rezervacijos pašalinimas nebuvo atliktas. Bandykite dar kartą.");
            System.out.println("--------------------cancelReservationFail-----------------------");
        }
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getAccountFbId() {
        return accountFbId;
    }

    public void setAccountId(String accountFbId) {
        this.accountFbId = accountFbId;
    }

    public List<Reservation> getMyReservations() {
        return myReservations;
    }

    public void setMyReservations(List<Reservation> myReservations) {
        this.myReservations = myReservations;
    }

}
