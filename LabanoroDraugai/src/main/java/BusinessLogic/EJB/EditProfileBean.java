/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ImageCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Image;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.primefaces.model.UploadedFile;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Laurute
 */
@Named
//@Stateful
@RequestScoped
public class EditProfileBean implements Serializable {

    @Inject
    AccountCRUD accountEjb;
    private Account account;
    private UploadedFile file;

    @Inject
    ImageCRUD imagesEjb;
    // @PersistenceContext(type=PersistenceContextType.EXTENDED)veliau suzinosiu
    // @PersistenceContext
    // private EntityManager em;

    @Inject
    LoginBean loginBean;
    
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    //construct
    @PostConstruct
    public void init() {
        account = accountEjb.findAccountById(loginBean.getId()); 
    }

    //get set
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    //business  
    public String saveAccountChanges() {
        try {
            //account.setPhotoBlob(IOUtils.toByteArray(this.file.getInputstream()));
            System.out.println("Failo vardas "+this.file.getFileName());
            if (!"".equals(this.file.getFileName())){
                System.out.println("Ieinu");
                Image image = new Image();
                image.setContent(IOUtils.toByteArray(this.file.getInputstream()));
                Integer imageId = imagesEjb.addImage(image);
                account.setPhotoImageid(image);
            }     
        } catch (IOException ex) {
            Logger.getLogger(EditProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Account a = new Account();
        a = accountEjb.updateAccount(account);    //LUŠ kai updatins į esantį email
        System.out.println(a.getLastName() + " BAIGTA");
        return "myProfile";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
