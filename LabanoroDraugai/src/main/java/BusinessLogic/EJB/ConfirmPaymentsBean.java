/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.PaidFeesCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Fee;
import DataAccess.JPA.PaidFees;
import Interceptors.Interceptable;
import Messages.Message;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;

/**
 *
 * @author darbas
 */
@Named
@RequestScoped
@Stateful
public class ConfirmPaymentsBean {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION, synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;

    @Inject
    LoginBean loginBean;

    @Inject
    AccountCRUD accountCRUD;

    @Inject
    PaidFeesCRUD paidFeesCRUD;
    
    private List<PaidFees> feeList;

    public List<PaidFees> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<PaidFees> feeList) {
        this.feeList = feeList;
    }
    
    @PostConstruct
    public void init() {
        feeList = paidFeesCRUD.findAllPaidFees();
        System.out.println("**************************Sukonstruojama******************************");
    }
    
    @Interceptable
    public void confirmPayment(int feeid){
        try{
        PaidFees paidFee = paidFeesCRUD.findPaidFeesById(feeid);
        Account user = accountCRUD.findAccountById(paidFee.getAccountId().getId());
        Fee fee = paidFee.getFee();
        try{
            user.setPoints(user.getPoints()+fee.getPoints());
            user = accountCRUD.updateAccount(user);
            paidFeesCRUD.deletePaidFees(paidFee);
            feeList.remove(paidFee);
            em.joinTransaction();
            em.flush();
            Account account = accountCRUD.findAccount(loginBean.getFbid());
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("interceptorAccount", account);
            Message.addSuccessMessage("Patvirtinimas sėkmingas");
        }catch(Exception ex2){
            Message.addErrorMessage("Įvyko klaida įrašant duomenis į duomenų bazę. Bandykite dar kartą");
        }
        }catch(Exception ex1){
            Message.addErrorMessage("Įvyko klaida nuskaitant duomenis iš duomenų bazės. Bandykite dar kartą");
            System.out.println("Nepaima user arba fee iš duomenu bazes");
        }
    }
}
