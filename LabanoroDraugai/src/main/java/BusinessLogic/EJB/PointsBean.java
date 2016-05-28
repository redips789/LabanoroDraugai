package BusinessLogic.EJB;

import Alternatives.GroupDistribution;
import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Settings;
import Decorators.IPriceCalculator;
import Messages.Message;
import java.io.Console;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Calendar;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.SynchronizationType;
import Interceptors.Interceptable;
import javax.faces.context.FacesContext;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpSession;
import javax.faces.view.ViewScoped;

/**
 *
 * @author darbas
 */
@Named
@ViewScoped
@Stateful
//@Interceptable
public class PointsBean {

    /**
     * Creates a new instance of PointsBean
     */
    @PersistenceContext(type = PersistenceContextType.TRANSACTION, synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;

    @Inject
    AccountCRUD accountEjb;

    @Inject
    SettingsCRUD settingsEjb;

    @Inject
    LoginBean loginBean;

    @Inject
    private IPriceCalculator priceCalculator;

    private Account account;

    private Settings settings;

    private int price;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public PointsBean() {
    }

    public boolean isLackOfPoints() {
        settings = settingsEjb.findSettings();
        if (this.account.getPoints() - this.price < 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isFeeIsPaid() {
        if (account.getNextPayment() == null) {
            return false;
        }
        if (account.getNextPayment().before(Calendar.getInstance().getTime())) {
            return false;
        } else {
            Message.addWarningMessage("Mokėjimas jau buvo atliktas.");
            return true;
        }
    }

    public String checkMembership() {
        if (account.getNextPayment() == null) {
            return "Nesumokėtas";
        }
        if (account.getNextPayment().before(Calendar.getInstance().getTime())) {
            return "Nesumokėtas";
        } else {
            return "Sumokėtas";
        }
    }

    @PostConstruct
    public void init() {

        account = accountEjb.findAccount(loginBean.getFbid());
        settings = settingsEjb.findSettings();
        double a = settings.getMembershipFee();
        this.price = (int) a;
        System.out.println("---------INIT-----------");
        this.price = priceCalculator.calculatePrice(this.price, account);
        System.out.println(account.getFacebookid());
    }

    public String startPaying() {
        System.out.println("-------------------------------------------------test1-----------");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("mygtukas", true);
        return "payMembershipFee?faces-redirect=true";
    }

    @Interceptable
    public String payWithPoints() {
        double a = settings.getMembershipFee();
        int b = (int) a;
        this.account.setPoints(this.account.getPoints() - b);

        Calendar d = Calendar.getInstance();
        d.add(Calendar.YEAR, 1);
        Date date = d.getTime();

        this.account.setNextPayment(date);
        try {
            payMembershipFeeWithPoints(this.account);
            em.joinTransaction();
            em.flush();
            Message.addSuccessMessage("Mokėjimas sėkmingai atliktas!");
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("interceptorAccount", this.account);
            return "points?faces-redirect=true";
        } catch (OptimisticLockException ole) {
            Message.addWarningMessage("Mokėjimas jau buvo atliktas.");
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("interceptorAccount", null);
            return "points?faces-redirect=true";
        } catch (PersistenceException pe) {
            Message.addErrorMessage("Nesijaudinkite, bet įvyko klaida ir mokėjimas nebuvo atliktas. Bandykite dar kartą.");
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("interceptorAccount", null);
            return "points?faces-redirect=true";
        } catch (Exception pe) {
            Message.addErrorMessage("Nesijaudinkite, bet įvyko nežinoma klaida. Bandykite dar kartą.");
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("interceptorAccount", null);
            return "points?faces-redirect=true";
        }
    }

    public String goBack() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("mygtukas", false);
        return "points?faces-redirect=true";
    }

    @Interceptable
    public void payMembershipFeeWithPoints(Account account) {
        this.account = em.merge(account);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("mygtukas", false);
        System.out.println("-----------************------payMembershipFeeWithPoints-------************----------");
    }
}
