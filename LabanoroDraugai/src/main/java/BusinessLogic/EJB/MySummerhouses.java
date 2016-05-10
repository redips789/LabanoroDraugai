
package BusinessLogic.EJB;

import DataAccess.EJB.ReservationCRUD;
import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Reservation;
import DataAccess.JPA.Summerhouse;
import java.io.Serializable;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Liudas 
 */

@ManagedBean
@RequestScoped
public class MySummerhouses implements Serializable {
    
    @EJB SummerhouseCRUD summerhouseCRUD;
    @EJB ReservationCRUD reservationCRUD;
    
    @ManagedProperty(value="#{loginBean}")
    LoginBean loginBean;
    
    private String accountId;
    private List<Reservation> myReservations = new ArrayList();
    private List<Summerhouse> mySummerhouses = new ArrayList();
    
    @PostConstruct
    public void init() {
        accountId = loginBean.getId();
        System.out.println("aaaaa"+accountId);
        
        if (accountId != null) myReservations = reservationCRUD.getByAccountId(accountId);
        else System.out.println("negavau account id"); // veliau istrint
        
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
