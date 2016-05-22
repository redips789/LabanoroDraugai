
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ReservationCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import DataAccess.JPA.Settings;
import Messages.Message;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;

/**
 *
 * @author Laurute
 */
@Named 
@ConversationScoped
@Stateful
public class ReservationBean {
    
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
    
    private Account account;
    
    private Settings settings;
    
    private Date startDate;
    
    private Date endDate;
    
    private boolean canReserve;
    
    private List<Reservation> membersReservations = new ArrayList<>();
    private List<Account> membersAccounts = new ArrayList<>();

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
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCanReserve() {
        return canReserve;
    }

    public List<Reservation> getMembersReservations() {
        return membersReservations;
    }

    public void setMembersReservations(List<Reservation> membersReservations) {
        this.membersReservations = membersReservations;
    }

    public List<Account> getMembersAccounts() {
        return membersAccounts;
    }

    public void setMembersAccounts(List<Account> membersAccounts) {
        this.membersAccounts = membersAccounts;
    }

    public void setCanReserve(boolean canReserve) {
        this.canReserve = canReserve;
    }
        //-business-//
    
        @PostConstruct
    public void init() {
        
        account = accountEjb.findAccount(loginBean.getFbid());
        settings = settingsEjb.findSettings();
        canReserveValidation();
    }
    
    public List<Account> findMembersOnSamePeriod() {
        membersReservations = reservationEjb.findByPeriod(startDate, endDate);  // susirandam rezervacijas pagal datas
        
        for (Reservation reservation : membersReservations) {  
            membersAccounts.add(reservation.getAccountId());    // pagal atrinktas rezervacijas paimam accountus
        }
        
        return membersAccounts;
    }
    
    public void throwMsg(){
        //Message.addWarningMessage("Uoj negerai");
    }
    
    public String saveReservation(){
        try{
        payForReservation();
        Message.addSuccessMessage("Rezervacija sėkmingai atlikta!");
        }
        catch (Exception pe) {
            Message.addErrorMessage("Nesijaudinkite, bet įvyko nežinoma klaida. Bandykite dar kartą.");
        }
        return "reservation?faces-redirect=true";
    }
    
    private void payForReservation(){
    
    }
    
    private void canReserveValidation(){
        //1. ar turi pinigų bent savaitei - nžn ar reikia sitoj vietoj, kol kas nera
        //2. pagal siandienos data kurios grupes rezervuotis gali
        //3. ar naudotojas patenka i ta grupe 
        int reservedDays = this.account.getTimeSpentOnHoliday();
        if (settings.getSecondReservation().after(Calendar.getInstance().getTime())){
            //1grupe. Maksimumas 1 sav.
            int max1 = 7;
            if (reservedDays < max1) this.canReserve = true;
            else                     this.canReserve = false;
        }
        else{
            if (settings.getThirdReservation().after(Calendar.getInstance().getTime())){
                //1 ir 2 grupe. Maksimumas 2 sav.
                int max2 = 14;
                if (reservedDays < max2) this.canReserve = true;
                else                     this.canReserve = false;
            
            }
            else{
                if (settings.getCloseReservation().after(Calendar.getInstance().getTime())){
                    //1, 2, 3 grupe. Maksimumas 3 sav.
                    int max3 = 21;
                    if (reservedDays < max3) this.canReserve = true;
                    else                     this.canReserve = false;
                }
                else{
                    //rezervacijos laikas baigtas
                    this.canReserve = false;
                }
            }
        
        }
    }
    
    
    

}
