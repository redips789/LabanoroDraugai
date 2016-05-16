/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author darbas
 */
@Named(value = "registrationBean")
@ManagedBean
@SessionScoped
public class RegistrationBean {

    @Inject
    private AccountDao loginAuthBean;
    
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String bday;
    private Date birthday;
    private String picture;
    private String phone;
    private String description;
    private String city;
    private int errorCounter = 0;
    private boolean rendered = false;

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }
    

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    
    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public int getErrorCounter() {
        return errorCounter;
    }

    public void setErrorCounter(int errorCounter) {
        this.errorCounter = errorCounter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
    }
    public void takeDataFromFacebook(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        try{
        id = (String) map.get("id");
        }catch(Exception ex){
            errorCounter++;
        }
        try{
        first_name = (String) map.get("first_name");
        }catch(Exception ex){
            errorCounter++;
        }
        try{
        last_name = (String) map.get("last_name");
        }catch(Exception ex){
            errorCounter++;
        }
        try{
        email = (String) map.get("email");
        }catch(Exception ex){
            errorCounter++;
        }
        try{
        bday = (String) map.get("birthday");
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MMM-dd");
        Date b = sdf.parse(bday);
        String tempdate = dateFormatNeeded.format(b);
        birthday = dateFormatNeeded.parse(tempdate);
        }catch(Exception ex){
            errorCounter++;
        }
        try{
        picture = (String) map.get("picture");
        }catch(Exception ex){
            errorCounter++;
        }
        rendered=true;
    }
    
    public String register(){
        Account account = new Account();
        account.setFacebookid(id);
        account.setCity(city);
        account.setDateOfBirth(birthday);
        account.setEmail(email);
        account.setDescription(description);
        account.setFirstName(first_name);
        account.setLastName(last_name);
        account.setPhoneNum(phone);
        account.setPhoto(picture);
        account.setPoints(0);
        account.setStatus("kandidatas");
        account.setVersion(0);
        account.setTimeSpentOnHoliday(0);
        loginAuthBean.addAccount(account);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
    
    
    
}
