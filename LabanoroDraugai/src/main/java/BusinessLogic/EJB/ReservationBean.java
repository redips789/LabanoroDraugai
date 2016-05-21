
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Settings;
import Messages.Message;
import java.util.Date;
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
    LoginBean loginBean;
    
    private Account account;
    
    private Settings settings;
    
    private Date startDate;
    
    private Date endDate;
    
    private Date firstReservation;
    
    private Date closeReservalion; 

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
    
    //
    
    


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

    public Date getFirstReservation() {
        return firstReservation;
    }

    public void setFirstReservation(Date firstReservation) {
        this.firstReservation = firstReservation;
    }

    public Date getCloseReservalion() {
        return closeReservalion;
    }

    public void setCloseReservalion(Date closeReservalion) {
        this.closeReservalion = closeReservalion;
    }
        //-business-//
    
        @PostConstruct
    public void init() {
        
        account=accountEjb.findAccount(loginBean.getFbid());
//        settings = settingsEjb.findSettings().getFirstReservation();
        this.firstReservation = settingsEjb.findSettings().getFirstReservation();
        this.closeReservalion = settingsEjb.findSettings().getCloseReservation();
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
    

}
