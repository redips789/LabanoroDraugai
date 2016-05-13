/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.EJB.ImageCrud;
import DataAccess.EJB.SettingsDao;
import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Image;
import DataAccess.JPA.Settings;
import DataAccess.JPA.Summerhouse;
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
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Laurute
 */
//@Named
//@Stateful
@ManagedBean
@RequestScoped
public class AddSummerhouseBean implements Serializable {

    @EJB
    SummerhouseCRUD summerhouseCRUD;
    
    private Summerhouse summerhouse;
    
    private UploadedFile file;
    
    @EJB
    ImageCrud imagesEjb;

    @PostConstruct
    public void init() {
        summerhouse = new Summerhouse();
    }

    public String saveSummerhouse (){
        try {
            //account.setPhotoBlob(IOUtils.toByteArray(this.file.getInputstream()));
            Image image = new Image();
            image.setContent(IOUtils.toByteArray(this.file.getInputstream()));
            Integer imageId = imagesEjb.addImage(image);
            summerhouse.setPhotoImageid(image);
        } catch (IOException ex) {
            Logger.getLogger(AddSummerhouseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        summerhouseCRUD.addSummerhouse(summerhouse);
        return "summerhouse";
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
