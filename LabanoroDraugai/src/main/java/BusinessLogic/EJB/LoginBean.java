/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.JPA.Account;
import java.io.Serializable;
import java.util.Map;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author darbas
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private AccountCRUD loginAuthBean;

    /**
     * Creates a new instance of LoginBean
     */
    private String fbid;
    private String accesstoken;
    private String signedrequest;
    private String expiresin;
    private int id;
    private boolean isloggedin = false;
    private boolean isregistered = false;
    private String redirecdedPage = "/login.xhtml";
    private int errorCounter = 0;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String nextPage() {
        return "home?faces-redirect=true";
    }

    public String getParams() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        Map map = facesContext.getExternalContext().getRequestParameterMap();
        fbid = (String) map.get("id");
        accesstoken = (String) map.get("accesstoken");
        signedrequest = (String) map.get("signedrequest");
        expiresin = (String) map.get("expiresin");
        if (loginAuthBean.accountExistBoolean(fbid)) {
            Account acc = loginAuthBean.findAccount(fbid);
            session.setAttribute("status", acc.getStatus());
            session.setAttribute("account", acc);
            id = acc.getId();
            redirecdedPage = "home?faces-redirect=true";
        } else {
            redirecdedPage = "registration?faces-redirect=true";
        }
        return redirecdedPage;
    }

    public String leaveForever() {
        Account account = loginAuthBean.accountExistAccount(fbid);
        if (account == null) {
            return "";
        } else {
            loginAuthBean.deleteAccount(account);
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "login?faces-redirect=true";
        }
    }

    public String checkPageStage() {
        return redirecdedPage;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
}
