/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author darbas
 */
@Named(value = "loginBean")
@ManagedBean
@SessionScoped
public class LoginBean {

    /**
     * Creates a new instance of LoginBean
     */
    private String id;
    public LoginBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
