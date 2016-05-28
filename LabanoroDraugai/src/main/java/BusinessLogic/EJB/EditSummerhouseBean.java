/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.ImageCRUD;
import DataAccess.EJB.SummerhouseCRUD;
import DataAccess.JPA.Image;
import DataAccess.JPA.Summerhouse;
import Messages.Message;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Povilas
 */
//@SessionScoped
@Named
@RequestScoped
public class EditSummerhouseBean implements Serializable {

    private String title;
    private Summerhouse detailedSummerhouse;
    private UploadedFile file;

    @Inject
    SummerhouseCRUD summerhouseCRUD;

    @Inject
    ImageCRUD imagesEjb;

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

    public String saveSummerhouseChanges() {
        try {
            //account.setPhotoBlob(IOUtils.toByteArray(this.file.getInputstream()));
            Summerhouse temp = summerhouseCRUD.findByTitle(detailedSummerhouse.getTitle());
            if (detailedSummerhouse.getValidityStart().after(detailedSummerhouse.getValidityEnd())) {

                Message.addErrorMessage("Kvaily, datas geras nurodyk");
            } else if (temp != null && detailedSummerhouse.getId() != temp.getId()) {

                Message.addErrorMessage("Vasarnamis su tokiu pavadinimu jau egzistuoja");
            } else {
                if (!"".equals(this.file.getFileName())) {
                    Image image = new Image();
                    image.setContent(IOUtils.toByteArray(this.file.getInputstream()));
                    Integer imageId = imagesEjb.addImage(image);
                    detailedSummerhouse.setPhotoImageid(image);

                }
                Message.addSuccessMessage("Vasarnamis sėkmingai išsaugotas");
                summerhouseCRUD.updateSummerhouse(detailedSummerhouse);
            }
        } catch (IOException ex) {
            Logger.getLogger(EditSummerhouseBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "settings";
    }

    public String deleteSummerhouse() {
        try {
            Map<String, String> params
                    = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String summerhouseTitle = params.get("title");
            Summerhouse sh = summerhouseCRUD.findByTitle(summerhouseTitle);
            summerhouseCRUD.deleteSummerhouse(sh);
            Message.addSuccessMessage("Vasarnamis sėkmingai ištrintas");
        } catch (Exception ex) {
             Message.addErrorMessage("Įvyko klaida!");
            return "summerhouse?faces-redirect=true";
        }
        return "summerhouse?faces-redirect=true";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
