/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.InvitationCRUD;
import DataAccess.EJB.RecommendationCRUD;
import DataAccess.EJB.RegistrationFormCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Invitation;
import DataAccess.JPA.Recommendation;
import DataAccess.JPA.RecommendationPK;
import DataAccess.JPA.RegistrationForm;
import DataAccess.JPA.Settings;
import Messages.Message;
import Services.Encryption;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author darbas
 */
@Named(value = "registrationBean")
@SessionScoped
public class RegistrationBean implements Serializable {

    @Inject
    private AccountCRUD loginAuthBean;

    @Inject
    private InvitationCRUD InvitationEjb;

    @Inject
    private RecommendationCRUD recommendationEjb;

    @Inject
    RegistrationFormCRUD registrationFormCRUD;

    private String id;
    private String first_name;
    private RegistrationForm registrationForm;
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

    private String code = "";

    @PostConstruct
    public void init() {
        registrationForm = registrationFormCRUD.findRegistrationForm();
        if (registrationForm == null) {
            registrationForm = new RegistrationForm();
        }
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public RegistrationForm getRegistrationForm() {
        return registrationForm;
    }

    public void setRegistrationForm(RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
    }

    public void takeDataFromFacebook() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        try {
            id = (String) map.get("id");
        } catch (Exception ex) {
            errorCounter++;
        }
        try {
            first_name = (String) map.get("first_name");
        } catch (Exception ex) {
            errorCounter++;
        }
        try {
            last_name = (String) map.get("last_name");
        } catch (Exception ex) {
            errorCounter++;
        }
        try {
            email = (String) map.get("email");
        } catch (Exception ex) {
            errorCounter++;
        }
        try {
            bday = (String) map.get("birthday");
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MMM-dd");
            Date b = sdf.parse(bday);
            String tempdate = dateFormatNeeded.format(b);
            birthday = dateFormatNeeded.parse(tempdate);
        } catch (Exception ex) {
            errorCounter++;
        }
        try {
            picture = (String) map.get("picture");
        } catch (Exception ex) {
            errorCounter++;
        }
        rendered = true;
    }

    public String register() {
        Calendar d = Calendar.getInstance();
        Date date = d.getTime();
        Account account = new Account();
        account.setFacebookid(id);
        if (registrationForm.getCity()) {
            account.setCity(city);
        }
        if (registrationForm.getBirthday()) {
            account.setDateOfBirth(birthday);
        }
        if (registrationForm.getEmail()) {
            account.setEmail(email);
        }
        if (registrationForm.getDescription()) {
            account.setDescription(description);
        }
        if (registrationForm.getFirstName()) {
            account.setFirstName(first_name);
        }
        if (registrationForm.getLastName()) {
            account.setLastName(last_name);
        }
        if (registrationForm.getPhone()) {
            account.setPhoneNum(phone);
        }
        account.setPhoto(picture);
        account.setPoints(0);
        account.setStatus("Kandidatas");
        account.setVersion(0);
        account.setTimeSpentOnHoliday(0);

        if (!"".equals(code)) {
            try {
                String encrypted = Encryption.encrypt(code);
                Invitation invitation = InvitationEjb.findByCode(encrypted);
                if (invitation != null) {
                    try {
                        loginAuthBean.addAccount(account);   ///IDEDA KANDIDATA

                        Recommendation recommend = new Recommendation();
                        RecommendationPK recPK = new RecommendationPK();
                        recPK.setReceiverAccountid(account.getId());
                        recPK.setGiverAccountid(invitation.getInviterAccountid().getId());
                        recommend.setRecommendationPK(recPK);
                        recommend.setIsGiven(Boolean.TRUE);
                        recommend.setSendDate(Calendar.getInstance().getTime());
                        recommendationEjb.addRecommendation(recommend);    //IDEDA REKOMENDACIJA

                        InvitationEjb.deleteInvitation(invitation);       //ISTRINA PAKVIETIMA

                        code = "";
                        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                        return "login?faces-redirect=true";
                    } catch (Exception e) {
                        Message.addErrorMessage("Sistemos klaida. Apgailestaujame.");
                        return "registration?faces-redirect=true";
                    }
                } else {
                    Message.addErrorMessage("Toks kodas neegzistuoja. Įveskite teisingą pakvietimo kodą arba palikite lauką tuščią!");
                    return "registration?faces-redirect=true";
                }
            } catch (Exception e) {
                Message.addErrorMessage("Sistemos klaida. Apgailestaujame.");
                return "registration?faces-redirect=true";
            }
        } else {
            loginAuthBean.addAccount(account);
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "login?faces-redirect=true";
        }
    }

}
