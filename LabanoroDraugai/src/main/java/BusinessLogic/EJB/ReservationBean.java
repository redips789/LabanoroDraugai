
package BusinessLogic.EJB;

import Alternatives.GroupDistribution;
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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Laurute
 */
@Named 
@ViewScoped
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
    SummerhouseCRUD summerhouseEjb;
    
    @Inject
    GroupDistribution groupDistribution;
    
    private Account account;
    
    private Settings settings;
    
    private Date startDate;
    
    private Date endDate;
    
    private int weeks;
    
    private boolean canReserve;
    
    private Summerhouse summerhouse;
    
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

    public void setEndDate() {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.add(Calendar.DATE, weeks*7-1);
        this.endDate = start.getTime();
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
        
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

    public Summerhouse getSummerhouse() {
        return summerhouse;
    }

    public void setSummerhouse(Summerhouse summerhouse) {
        this.summerhouse = summerhouse;
    }
    
    
        //-business-//
    
    @PostConstruct
    public void init() {
        System.out.println("Susikuriau reservation bean");
        account = accountEjb.findAccount(loginBean.getFbid());
        settings = settingsEjb.findSettings();
         // cia reikia rasti pagal id is URL arba is summerhouseDetails egzemplioriaus - summerhouseDetails.getDetailedSummerhouse()
//        summerhouse = summerhouseDetails.getDetailedSummerhouse(); // pirma karta kuriant beansa yra gera info, o po to ne

        summerhouse = summerhouseDetails.getDetailedSummerhouse(); // pirma karta kuriant beansa yra gera info, o po to ne
        System.out.println("OOOOOOO"+summerhouse.getTitle());
        System.out.println("OOOOOOO"+summerhouse.getCost());
        
    }
    
    public void findMembersOnSamePeriod() {
        if (startDate != null && weeks != 0) {
            this.setEndDate();
            membersReservations = reservationEjb.findByPeriod(startDate, endDate);  // susirandam rezervacijas pagal datas     
            this.showThirdDialog();
        } 
        else{
            Message.addErrorMessage("Nepalikite nė vieno lauko tuščio!");
        }
    }
    
    @Interceptable
    public String saveReservation(){
        try {
            // nes jei naudojam this.account, kuris sukurtas čia, tai taškai mažėja, nors nenuimam 
            // tiesiog tokiam tikrinimui reikia gauti tikrojo accounto, o ne "kopijos" taškus
            Account acc = accountEjb.findAccountById(loginBean.getId());
            if (acc.getPoints() >= this.summerhouse.getCost()*weeks){
                em.isOpen();
           
                if (reservationEjb.existSimilarReservation(this.summerhouse, this.startDate, this.endDate) == false){
                    Reservation reservation = new Reservation();
                    reservation.setAccountId(account);
                    reservation.setVersion(0);
                    reservation.setSummerhouseId(this.summerhouse);
                    reservation.setStartDate(this.startDate);
                    reservation.setEndDate(this.endDate);
                    reservation.setCost(this.summerhouse.getCost()*weeks); // jei bus daugiau savaiciu, nebus taip paprasta (PAPRASTA :D - Kristina)          
                    reservationEjb.insertReservation(reservation);
                    
                    payForReservation(this.account, 7*weeks, this.summerhouse.getCost()*weeks); // 

                    em.joinTransaction();
                    this.hideFirstDialog();
                    this.hideSecondDialog();
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sveikiname", "Rezervacija sėkmingai atlikta!");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                }
                else  {
                    Message.addErrorMessage("Kažkas buvo greitesnis už Jus! Pakeiskite rezervacijos datas.");
                }
            }
            else {
                Message.addErrorMessage("Jūsų taškų likutis nepakankamas pasirinktai rezervacijai!");
            }
        }
        catch (Exception pe) {
            System.out.println("********************************" + pe);
            Message.addErrorMessage("Įvyko nenumatyta klaida. Bandykite dar kartą.");
        }
        return "";
    }
    
    @Interceptable
    private void payForReservation(Account acc, int days, int cost){
        acc.setPoints(acc.getPoints()-cost);
        acc.setReservedDays(acc.getReservedDays()+days);
        this.account = em.merge(acc);
    }
    
    public boolean canReserveSummerhouse(){
        this.setCanReserve(groupDistribution.canGroupReserve(account, settings));
        return this.isCanReserve();
    }
    
    public void canGoDeeperReservation(){
        Date reservationDay = new Date();

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        Date today = now.getTime();
        
        reservationDay = settings.getAllReservation();
        
        int compare = today.compareTo(reservationDay);
        
        if (startDate != null && weeks != 0) {
            this.setEndDate();
            if (account.getReservedDays() < settings.getMaxReservationDays()){
                if (account.getReservedDays()+weeks*7 <= settings.getMaxReservationDays()){
                    this.showSecondDialog();
                }
                else{
                    Message.addErrorMessage("Pasirinkite mažesnį savaičių skaičių, nes viršijamas rezevacijos limitas!");
                }
            }
            else {
                if (compare == 0 || compare == 1){
                    this.showSecondDialog();
                }
                else Message.addErrorMessage("Jūs jau esate užsirezeravę maksimalų dienų skaičių!");
            }
        } 
        else {
            Message.addErrorMessage("Jei norite rezervuoti vasarnamį, nepalikite tuščių laukų!");
        }
    }
    
    public void showFirstDialog(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('first').show();");
    }
    
    public void hideFirstDialog(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('first').hide();");
    }
    
    public void showSecondDialog(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('second').show();");
    }
    
    public void hideSecondDialog(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('second').hide();");
    }
    
    public void showThirdDialog(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('third').show();");
    }
    
    public void hideThirdDialog(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('third').hide();");
    }
    
    public void backFromSecond(){
        this.hideSecondDialog();
        this.showFirstDialog();
    }
    
    public void backFromThird(){
        this.hideThirdDialog();
        this.showFirstDialog();
    }
}
