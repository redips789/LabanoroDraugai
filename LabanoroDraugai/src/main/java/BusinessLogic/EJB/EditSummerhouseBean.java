/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.ImageCrud;
import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Image;
import DataAccess.JPA.Summerhouse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Liudas 
 */

//@SessionScoped
public class EditSummerhouseBean implements Serializable {
    
    private String title;   
    private Summerhouse detailedSummerhouse;
    private UploadedFile file;
    
    @EJB SummerhouseCRUD summerhouseCRUD;
    
    @EJB
    ImageCrud imagesEjb;

    @PostConstruct
    public void init() {
        //account = accountEjb.findAccountById(loginBean.getId()); //randa pagal sesijos FbId
        detailedSummerhouse = new Summerhouse();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        detailedSummerhouse = summerhouseCRUD.findByTitle(title);
    }

    public Summerhouse getDetailedSummerhouse() {
        return detailedSummerhouse;
    }

    public void setDetailedSummerhouse(Summerhouse detailedSummerhouse) {
        this.detailedSummerhouse = detailedSummerhouse;
    }
    
    public String saveSummerhouseChanges (){
        try {
            //account.setPhotoBlob(IOUtils.toByteArray(this.file.getInputstream()));
            Image image = new Image();
            image.setContent(IOUtils.toByteArray(this.file.getInputstream()));
            Integer imageId = imagesEjb.addImage(image);
            detailedSummerhouse.setPhotoImageid(image);
        } catch (IOException ex) {
            Logger.getLogger(EditSummerhouseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        summerhouseCRUD.updateSummerhouse(detailedSummerhouse);
        return "settings";
    }

    public void deleteSummerhouse(ActionEvent event){
        String summerhouseTitle=(String)event.getComponent().getAttributes().get("title");
        Summerhouse sh =summerhouseCRUD.findByTitle(summerhouseTitle);
        summerhouseCRUD.deleteSummerhouse(sh);
    }
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
}
