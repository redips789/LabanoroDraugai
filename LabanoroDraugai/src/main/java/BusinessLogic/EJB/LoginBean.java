/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.io.Serializable;
import java.util.Map;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author darbas
 */
@Named(value = "loginBean")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private AccountDao loginAuthBean;

    /**
     * Creates a new instance of LoginBean
     */
    private String id;
    private String accesstoken;
    private String signedrequest;
    private String expiresin;
    private boolean isloggedin=false;
    private boolean isregistered=false;
    private String redirecdedPage = "login";
//    private String first_name;
//    private String last_name;
//    private String email;
//    private Date birthday;
//    private String picture;
    private int errorCounter = 0;

//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Date birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getPicture() {
//        return picture;
//    }
//
//    public void setPicture(String picture) {
//        this.picture = picture;
//    }

    public int getErrorCounter() {
        return errorCounter;
    }

    public void setErrorCounter(int errorCounter) {
        this.errorCounter = errorCounter;
    }

    public String getRedirecdedPage() {
        return redirecdedPage;
    }

    public void setRedirecdedPage(String redirecdedPage) {
        this.redirecdedPage = redirecdedPage;
    }

    public boolean isIsloggedin() {
        return isloggedin;
    }

    public void setIsloggedin(boolean isloggedin) {
        this.isloggedin = isloggedin;
    }

    public boolean isIsregistered() {
        return isregistered;
    }

    public void setIsregistered(boolean isregistered) {
        this.isregistered = isregistered;
    }

    public String getSignedrequest() {
        return signedrequest;
    }

    public void setSignedrequest(String signedrequest) {
        this.signedrequest = signedrequest;
    }

    public String getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(String expiresin) {
        this.expiresin = expiresin;
    }
    
    public LoginBean() {
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
    
    public String nextPage(){
        return "home?faces-redirect=true";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getParams() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        id = (String) map.get("id");
        accesstoken = (String) map.get("accesstoken");
        signedrequest = (String) map.get("signedrequest");
        expiresin = (String) map.get("expiresin");
        if(loginAuthBean.accountExistBoolean(id)){
            redirecdedPage = "home?faces-redirect=true";
        }
        else{
            redirecdedPage = "registration?faces-redirect=true";
        }
        return redirecdedPage;
    }
    
    public String leaveForever(){
        Account account = loginAuthBean.accountExistAccount(id);
        if(account==null){
            return "";
        }else{
            loginAuthBean.deleteAccount(account);
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "login?faces-redirect=true";
        }
    }
    
    public String checkPageStage(){
        return redirecdedPage;
    }
    
    
}
