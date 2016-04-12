/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author darbas
 */
@Named(value = "loginBean")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    private String id;
    private String accesstoken;
    private String signedrequest;
    private String expiresin;
    private boolean isloggedin=false;
    private boolean isregistered=false;

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
        isloggedin=true;
        return "home";
    }
    
    public String checkPageStage(){
        if(isloggedin)
            return "home";
        else
            return "login";
    }
    
    
}
