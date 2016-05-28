/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.FeeCRUD;
import DataAccess.EJB.PaidFeesCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Fee;
import DataAccess.JPA.PaidFees;
import Interceptors.Interceptable;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.stripe.model.Charge;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.model.Token;
import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import Messages.Message;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;

/**
 *
 * @author darbas
 */
@Named(value = "stripePaymentBean")
@RequestScoped
@Stateful
public class StripePaymentBean implements Serializable {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION, synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;

    @Inject
    FeeCRUD feeCRUD;

    @Inject
    LoginBean loginBean;

    @Inject
    AccountCRUD accountCRUD;

    @Inject
    PaidFeesCRUD paidFeesCRUD;

    private List<Fee> feeList;

    public List<Fee> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<Fee> feeList) {
        this.feeList = feeList;
    }

    @PostConstruct
    public void init() {
        feeList = feeCRUD.findAllFees();
        System.out.println("**************************Sukonstruojama******************************");
    }

    @Interceptable
    public void chargeCard() {
        try {
            System.out.println("**************************Payment test******************************");
            FacesContext context = FacesContext.getCurrentInstance();
            Map map = context.getExternalContext().getRequestParameterMap();
            Stripe.apiKey = "sk_test_QctvU17iZvnJOtsh2kbNxpSF";
            String token = (String) map.get("stripeToken");
            String feeid = (String) map.get("feeID");
            int feeID = Integer.parseInt(feeid);
            System.out.println(token);
            Fee fee = feeCRUD.findFee(feeID);
            System.out.println("+");
            if (fee == null) {
                System.out.println("**************************Nepaima is db******************************");
                return;
            }
            Account account = accountCRUD.findAccount(loginBean.getFbid());
            if (account == null) {
                System.out.println("**************************Nepaima is db******************************");
                return;
            }
            try {
                double amount = fee.getAmount() * 100;
                int trueAmount = (int) amount;
                String name = account.getFirstName();
                String lastName = account.getLastName();
                int points = fee.getPoints();
                System.out.println("++");
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    System.out.println(dateFormat.format(cal.getTime()));
                    String dateString = dateFormat.format(cal.getTime());
                    Map<String, Object> chargeParams = new HashMap<String, Object>();
                    chargeParams.put("amount", trueAmount); // amount in cents, again
                    chargeParams.put("currency", "eur");
                    chargeParams.put("source", token);
                    chargeParams.put("description", "Vardas: " + name + ", pavardė: " + lastName + ", taškai: " + points + ", suma eurais: " + fee.getAmount() + ", laikas: " + dateString);
                    System.out.println("**************************Payment test pries charge******************************");
                    Charge charge = Charge.create(chargeParams);
                    System.out.println("+++");
                    try {
                        Date date = new Date();
                        date = dateFormat.parse(dateString);
                        PaidFees paidfee = new PaidFees();
                        paidfee.setAccountId(account);
                        paidfee.setFee(fee);
                        paidfee.setPaidDate(date);
                        paidFeesCRUD.addFee(paidfee);
                        em.joinTransaction();
                        em.flush();
                        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("interceptorAccount", account);
                        System.out.println("++++");
                    } catch (Exception ex) {
                        System.out.println("Nepavyko iterpti i db, kreipkites i administratorių");
                        return;
                    }
                } catch (CardException e) {
                    System.out.println("Nepavyko mokėjimas, atmetė kortele");
                    return;
                } catch (Exception ex) {
                    System.out.println("Nepavyko mokėjimas dėl kitos serverio klaidos");
                    System.out.println(ex.getMessage());
                    return;
                }
            } catch (Exception ex) {
                System.out.println("Nepavyko mokėjimas dėl kitos serverio klaidos");
                return;
            }
        } catch (Exception ex) {
            System.out.println("Nepavyko paimti duomenų is jsf");
            return;
        }
    }
}
