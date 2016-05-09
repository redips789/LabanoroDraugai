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

/**
 *
 * @author darbas
 */
@Named(value = "pointsBean")
@RequestScoped
@ManagedBean
public class PointsBean {

    /**
     * Creates a new instance of PointsBean
     */
    
    @EJB
    AccountDao accountEjb;
    
    /*@EJB
    SettingsDao settingsEjb;*/
    
    @ManagedProperty(value="#{loginBean}")
    LoginBean loginBean;
    
    private Account account;
    
    private Settings settings;
    
    private int points;
    
    private int price;
    
    private Date membership;
    
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
        DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
        String tempdate = dateFormatNeeded.format(membership);
        feeIsPaid=true;
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
        account=accountEjb.findAccount(loginBean.getId());
        points=account.getPoints();
        membership=account.getNextPayment();
        /*settings = settingsEjb.findSettings();*/
       /* double a = settings.getMembershipFee();*/
       /* price = (int) a;*/
        
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
    
    public void add1Point(){
        points=points+1;
    }
    
    
    
}
