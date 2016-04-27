/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.JPA.Account;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
//import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.UploadedFile;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Laurute
 */
//@Named

//@Stateful
@ManagedBean
@RequestScoped
public class EditProfileBean implements Serializable {

    @EJB
    AccountDao accountEjb;

    // @PersistenceContext(type=PersistenceContextType.EXTENDED)veliau suzinosiu
    @PersistenceContext
    private EntityManager em;

    private Account account;

    String fbId;

    private int accountAge;
    private String accountPhoneNumber;

    private UploadedFile file;

    //construct
    @PostConstruct
    public void init() {
        fbId = "1371566362869682"; // kazkaip REIKS gauti is sesijos
        account = accountEjb.findAccount(fbId);
        System.out.println(this.account.getFirstName() + " inicijuota");
    }

    //get set
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccountAge() {
        estimateAge();
        return accountAge + "  metų,  ";
    }

    public String getAccountPhoneNumber() {
        return "+370 " + this.account.getPhoneNum();
    }

    //business  
    public String saveAccountChanges() {
        try {
            this.getAccount().setPhotoBlob(IOUtils.toByteArray(this.file.getInputstream()));
        } catch (IOException ex) {
            Logger.getLogger(EditProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Account a = new Account();
        a = accountEjb.updateAccount(this.getAccount());    //LUŠ kai updatins į esantį email
        System.out.println(a.getLastName() + " BAIGTA");
        return "myProfile";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    private void estimateAge() {
        Calendar dob = Calendar.getInstance();
        dob.setTime(this.account.getDateOfBirth());
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        System.out.println(this.account.getDateOfBirth().getDate() + " seni geri metai" + today.get(Calendar.DAY_OF_MONTH) + " mėn" + today.get(Calendar.DAY_OF_WEEK_IN_MONTH) + " diena" + today.get(Calendar.DAY_OF_WEEK) + " ssav diena");
        this.accountAge = age;
    }

}
