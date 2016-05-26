
package BusinessLogic.EJB;

import Alternatives.GroupDistribution;
import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ReservationCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import DataAccess.JPA.Settings;
import Messages.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Laurute
 */
@Named 
@RequestScoped
@Stateful
public class ReservationBean implements Serializable {
    
    @PersistenceContext(type=PersistenceContextType.TRANSACTION, synchronization=SynchronizationType.UNSYNCHRONIZED) 
    private EntityManager em;
    
    @Inject
    private Conversation conversation;
    
    @Inject
    AccountCRUD accountEjb;
    
    @Inject
    SettingsCRUD settingsEjb;
    
    @Inject
    ReservationCRUD reservationEjb;
    
    @Inject
    LoginBean loginBean;
    
    @Inject 
    SummerhouseDetails summerhouseDetails;
    
    @Inject
    GroupDistribution groupDistribution;
    
    private Account account;
    
    private Settings settings;
    
    private Date startDate;
    
    private Date endDate;
    
    private boolean canReserve;
    
    private List<Reservation> membersReservations = new ArrayList<>();

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        System.out.println("setteris start date");
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Reservation> getMembersReservations() {
        return membersReservations;
    }

    public void setMembersReservations(List<Reservation> membersReservations) {
        this.membersReservations = membersReservations;
    }

    public boolean isCanReserve() {
        return canReserve;
    }

    public void setCanReserve(boolean canReserve) {
        this.canReserve = canReserve;
    }
        //-business-//
    
    @PostConstruct
    public void init() {
        account = accountEjb.findAccount(loginBean.getFbid());
        settings = settingsEjb.findSettings();
    }
    
    public void findMembersOnSamePeriod() {
        if (startDate != null && endDate != null) {
            membersReservations = reservationEjb.findByPeriod(startDate, endDate);  // susirandam rezervacijas pagal datas     
        } 
    }
    
    public void throwMsg(){
        Message.addWarningMessage("Uoj negerai");
    }
    
    public String saveReservation(){
        try {
            System.out.println("aaaa"+summerhouseDetails.getDetailedSummerhouse().getTitle());
            payForReservation(); // 
            Reservation reservation = new Reservation();
            reservation.setAccountId(account);
            reservation.setSummerhouseId(summerhouseDetails.getDetailedSummerhouse()); //cia cj nepaskolins
            reservation.setStartDate(this.startDate);
            reservation.setEndDate(this.endDate);
            reservation.setCost(summerhouseDetails.getDetailedSummerhouse().getCost()); // jei bus daugiau savaiciu, nebus taip paprasta
            
            //tikrinimas ar jau toks yra
            reservationEjb.insertReservation(reservation);
            Message.addSuccessMessage("Rezervacija sėkmingai atlikta!");
        }
        catch (Exception pe) {
            System.out.println("********************************" + pe.getMessage() + pe.getStackTrace().toString());
            Message.addErrorMessage("Nesijaudinkite, bet įvyko nežinoma klaida. Bandykite dar kartą.");
        }
        return "reservation?faces-redirect=true";
    }
    
    private void payForReservation(){
    
    }
    
    public boolean canReserveSummerhouse(){
        this.setCanReserve(groupDistribution.canGroupReserve(account, settings));
        return this.isCanReserve();
    }
    
}
