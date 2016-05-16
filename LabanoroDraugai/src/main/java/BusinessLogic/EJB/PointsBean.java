/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.EJB.SettingsDao;
import DataAccess.JPA.Account;
import DataAccess.JPA.Settings;
import Messages.MessageUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author darbas
 */
@Named(value = "pointsBean")
@ViewScoped
@ManagedBean
public class PointsBean {

    /**
     * Creates a new instance of PointsBean
     */
    
    @EJB
    AccountDao accountEjb;
    
    @EJB
    SettingsDao settingsEjb;
    
    @ManagedProperty(value="#{loginBean}")
    LoginBean loginBean;
    
    private Account account;
    
    private Settings settings;
    
    private int points;
    
    private int price;
    
    private Date membership;
    private boolean lackOfPoints = false;
    
    private String confirmationMessage;
    
    private String redirectingToPage;

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    public String getRedirectingToPage() {
        return redirectingToPage;
    }

    public void setRedirectingToPage(String redirectingToPage) {
        this.redirectingToPage = redirectingToPage;
    }

    public boolean isLackOfPoints() {
        return lackOfPoints;
    }

    public void setLackOfPoints(boolean lackOfPoints) {
        this.lackOfPoints = lackOfPoints;
    }
    
    
    private boolean feeIsPaid = true;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isFeeIsPaid() {
        return feeIsPaid;
    }

    public void setFeeIsPaid(boolean feeIsPaid) {
        this.feeIsPaid = feeIsPaid;
    }
    
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getMembership() {
        if(membership==null){
            feeIsPaid=false;
            return "Nesumokėtas";
        }
        if(membership.before(Calendar.getInstance().getTime())){
            feeIsPaid=false;
        }
        else{
            feeIsPaid=true;
        }
        DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
        String tempdate = dateFormatNeeded.format(membership);
        return tempdate;
    }
    

    public void setMembership(String membership){
        DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
        try{
        this.membership = dateFormatNeeded.parse(membership);
        }catch(Exception ex){
            this.membership=null;
        }
    }
    
    
    
    
    @PostConstruct
    public void init() {
        account=accountEjb.findAccount(loginBean.getFbid());
        points=account.getPoints();
        membership=account.getNextPayment();
        settings = settingsEjb.findSettings();
       double a = settings.getMembershipFee();
       price = (int) a;
       if(points-price<0){
           lackOfPoints=true;
       }
        
    }
    
    public String payWithPoints(){
        account.setPoints(points-price);
        Calendar d = Calendar.getInstance();
        d.add(Calendar.YEAR, 1);
        Date date = d.getTime();
        
        account.setNextPayment(date);    
        try{
         accountEjb.updateAccount(account);
          
         confirmationMessage="Mokėjimas atliktas sėkmingai";
         redirectingToPage="points?faces-redirect=true";
         MessageUtil.addSuccessMessage(confirmationMessage);
         
        return "points?faces-redirect=true";
        }catch(org.eclipse.persistence.exceptions.OptimisticLockException oex){
            confirmationMessage="Įvyko klaida! Nespėjote! Bandykite dar kartą!";
            redirectingToPage="points?faces-redirect=true";
            MessageUtil.addErrorMessage(confirmationMessage);
            System.out.println("gkfdhjfbjft*gfhgf*******");
             return null;
                 }
        catch(PersistenceException pex){
            confirmationMessage="Įvyko persistent klaida! Bandykite dar kartą!";
            redirectingToPage="points?faces-redirect=true";
            MessageUtil.addErrorMessage(confirmationMessage);
             return null;
                 }
        catch(Exception ex){
            confirmationMessage=ex.getMessage();
            redirectingToPage="points?faces-redirect=true";
            MessageUtil.addErrorMessage(confirmationMessage);
            ex.printStackTrace();
            return null;
        }
       //return "points?faces-redirect=true";
    }

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
}
