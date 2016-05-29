
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
    private int weeks = 1;
    
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
        account = accountEjb.findAccount(loginBean.getFbid());
        settings = settingsEjb.findSettings();
        canReserveSummerhouse();
        summerhouse = summerhouseDetails.getDetailedSummerhouse(); 
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
                    if(!(this.endDate.after(this.summerhouseDetails.getDetailedSummerhouse().getValidityEnd()))){
                    Reservation reservation = new Reservation();
                    reservation.setAccountId(acc);
                    reservation.setVersion(0);
                    reservation.setSummerhouseId(this.summerhouse);
                    reservation.setStartDate(this.startDate);
                    reservation.setEndDate(this.endDate);
                    reservation.setCost(this.summerhouse.getCost()*weeks); // jei bus daugiau savaiciu, nebus taip paprasta (PAPRASTA :D - Kristina). Daugint reik, sunku:D -Laura           
                    reservationEjb.insertReservation(reservation);
                    System.out.println("********************************IRASO rezrvacija");
                    
                    payForReservation(acc, 7*weeks, this.summerhouse.getCost()*weeks); // 

                    em.joinTransaction();
                    this.hideFirstDialog();
                    this.hideSecondDialog();
                    FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("interceptorAccount", accountEjb.findAccountById(loginBean.getId()));
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sveikiname", "Rezervacija sėkmingai atlikta!");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    }
                    else{
                        Message.addErrorMessage("Pasirinkite leidžiamą laikotarpį!");
                    }
                }
                else  {
                    Message.addErrorMessage("Nespėjote užsirezervuoti, pasirinkite kitas datas!");
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
    
    public void information(){
         Message.addErrorMessage("Tik sumokėję metinį mokestį galėsite rezervuoti vasarnamį!");
    }
    
    public void canReserveSummerhouse(){
        int isPaid = this.compareWithToday(accountEjb.findAccountById(loginBean.getId()).getNextPayment());
        if (isPaid == -1){
            this.setCanReserve(groupDistribution.canGroupReserve(account, settings));         
        }
        else {
            Message.addWarningMessage("Tik sumokėję metinį mokestį galėsite rezervuoti vasarnamį!"); // reik string metodo
            this.setCanReserve(false);
        }
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
    
    public int compareWithToday(Date dat){
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        Date today = now.getTime();
        
        int compare = today.compareTo(dat);
        return compare;
    }
    
    public String[] getDates(){
        List<Reservation> reservationList = reservationEjb.findBySummerhouse(summerhouse);
        ArrayList<String> show = new ArrayList<String>();
        Date first;
        Date last;
        String toPut;
        SimpleDateFormat oneDayFormat = new SimpleDateFormat("M-d-yyyy");//6-6-2016
        SimpleDateFormat twoDayFormat = new SimpleDateFormat("M-dd-yyyy"); //6-12-2016
        
        for (int i=0; i<reservationList.size(); i++){
            first = reservationList.get(i).getStartDate();
            last = reservationList.get(i).getEndDate();
            long diff = Math.abs(last.getTime() - first.getTime());
            int diffDays = (int) diff / (24 * 60 * 60 * 1000);
            System.out.println("Gautas skirtumas ***** "+diffDays);
            
            Calendar now = Calendar.getInstance();
            now.setTime(first);
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);
            Date data = now.getTime();
            int mark = this.checkDayType(now);
            if (mark == 0) toPut = oneDayFormat.format(data);
            else toPut = twoDayFormat.format(data);
            show.add(toPut);
            System.out.println("Duomuo -------- "+toPut);
            for (int j=0; j<diffDays; j++){
                now.add(Calendar.DATE, 1);
                data = now.getTime();
                mark = this.checkDayType(now);
                if (mark == 0) toPut = oneDayFormat.format(data);
                else toPut = twoDayFormat.format(data);
                show.add(toPut);
                System.out.println("Duomuo -------- "+toPut);
            }
        }
        int lenght = show.size();
        String[] result = new String[lenght];
        for (int i=0; i<lenght; i++)
        {
            result[i]=show.get(i);
        }
        return result;
    }
    
    public String toJavascriptArray(){
        String[] arr = this.getDates();
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=0; i<arr.length; i++){
            sb.append("\"").append(arr[i]).append("\"");
            if(i+1 < arr.length){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    public int checkDayType(Calendar date){
        int day = date.get(Calendar.DAY_OF_MONTH);
        if (day < 10) return 0;
        else return 1;
    }
}
