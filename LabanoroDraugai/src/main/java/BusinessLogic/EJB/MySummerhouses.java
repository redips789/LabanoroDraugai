
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ReservationCRUD;
import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Liudas 
 */

@RequestScoped
@Named
public class MySummerhouses implements Serializable {
    
    @Inject SummerhouseCRUD summerhouseCRUD;
    @Inject ReservationCRUD reservationCRUD;
    @Inject AccountCRUD accountCRUD;
    @Inject CancelReservationBean cancelReservationBean;
    
    @Inject
    LoginBean loginBean;
    
    private String accountFbId;
    private Account acc;
    private List<Reservation> myReservations = new ArrayList();
    
    @PostConstruct
    public void init() {
        accountFbId = loginBean.getFbid();
        if (accountFbId != null) acc = accountCRUD.findAccount(accountFbId);      
        myReservations = reservationCRUD.getByAccount(acc); 
    }
    public void cancelReservation(Reservation reservation){
        cancelReservationBean.cancelReservation(reservation);
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
