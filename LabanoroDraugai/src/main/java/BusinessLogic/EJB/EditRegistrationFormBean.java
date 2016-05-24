/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.RegistrationFormCRUD;
import DataAccess.JPA.RegistrationForm;
import DataAccess.JPA.Settings;
import Messages.Message;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Povilas
 */
@Named
@RequestScoped
public class EditRegistrationFormBean implements Serializable{
    @Inject
    RegistrationFormCRUD registrationFormCRUD;
    
    private RegistrationForm registrationForm;
    
    private String message;

    public RegistrationForm getRegistrationForm() {
        return registrationForm;
    }

    public void setRegistrationForm(RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
    }
    
    @PostConstruct
    public void init() {
        registrationForm = registrationFormCRUD.findRegistrationForm();
        if(registrationForm == null){
            registrationForm = new RegistrationForm();
        }
    }
    
    public String saveRegistrationForm(){
        try{
        registrationFormCRUD.updateRegistrationForm(registrationForm);
        Message.addSuccessMessage("Pavyko išsaugoti pakeitimus");
        return "editRegistrationForm?faces-redirect=true";
        }catch(Exception e){
            Message.addErrorMessage("Įvyko klaida");
            return "editRegistrationForm?faces-redirect=true";
        }
    }
           
}
