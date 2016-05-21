
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ReservationCRUD;
import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Reservation;
import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
    
    @Inject
    LoginBean loginBean;
    
    private String accountFbId;
    private Account acc;
    private List<Reservation> myReservations = new ArrayList();
    private List<Summerhouse> mySummerhouses = new ArrayList();
    
    @PostConstruct
    public void init() {
        accountFbId = loginBean.getFbid();
        System.out.println("aaaaa"+accountFbId);
        
        
        if (accountFbId != null) acc = accountCRUD.findAccount(accountFbId);
        
        myReservations = reservationCRUD.getByAccount(acc);
        
        for (Reservation reservation : myReservations) {
            System.out.println(reservation.getSummerhouseId().getTitle());
            mySummerhouses.add(reservation.getSummerhouseId());
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

    public List<Summerhouse> getMySummerhouses() {
        return mySummerhouses;
    }

    public void setMySummerhouses(List<Summerhouse> mySummerhouses) {
        this.mySummerhouses = mySummerhouses;
    }

    public List<Reservation> getMyReservations() {
        return myReservations;
    }

    public void setMyReservations(List<Reservation> myReservations) {
        this.myReservations = myReservations;
    }
    
}
