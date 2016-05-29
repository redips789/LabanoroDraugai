/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ImageCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Image;
import DataAccess.JPA.Settings;
import DataAccess.JPA.Summerhouse;
import Messages.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
//import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.UploadedFile;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Povilas
 */
@Named
//@Stateful
@RequestScoped
public class AddSummerhouseBean implements Serializable {

    @Inject
    SummerhouseCRUD summerhouseCRUD;

    private Summerhouse summerhouse;

    private UploadedFile file;

    @Inject
    ImageCRUD imagesEjb;

    @PostConstruct
    public void init() {
        summerhouse = new Summerhouse();
    }

    public String saveSummerhouse() {
        try {
            //account.setPhotoBlob(IOUtils.toByteArray(this.file.getInputstream()));
            System.out.println("Summerhouse pavadinimas : " +summerhouse.getTitle());
            if (summerhouse.getValidityStart().after(summerhouse.getValidityEnd())) {
                
                Message.addErrorMessage("Kvaily, datas geras nurodyk");
                return "addSummerhouse?faces-redirect=true";
            } else if (summerhouseCRUD.findByTitle(summerhouse.getTitle())!= null){
                
                Message.addErrorMessage("Vasarnamis su tokiu pavadinimu jau egzistuoja");
                return "addSummerhouse?faces-redirect=true";
            } else{
                Image image = new Image();
                image.setContent(IOUtils.toByteArray(this.file.getInputstream()));
                Integer imageId = imagesEjb.addImage(image);
                summerhouse.setPhotoImageid(image);
                summerhouseCRUD.addSummerhouse(summerhouse);
                Message.addSuccessMessage("Vasarnamis sėkmingai išsaugotas");
            }
        } catch (IOException ex) {
            Logger.getLogger(AddSummerhouseBean.class.getName()).log(Level.SEVERE, null, ex);
            return "addSummerhouse?faces-redirect=true";
        }
        return "summerhouse?faces-redirect=true";
    }

    public Summerhouse getSummerhouse() {
        return summerhouse;
    }

    public void setSummerhouse(Summerhouse summerhouse) {
        this.summerhouse = summerhouse;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
