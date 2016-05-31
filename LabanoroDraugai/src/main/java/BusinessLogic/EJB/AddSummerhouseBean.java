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
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.UploadedFile;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;

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
            System.out.println("Summerhouse pavadinimas : " + summerhouse.getTitle());
            if (summerhouse.getBeds() == null || summerhouse.getCost() == null || summerhouse.getDescription() == null
                    || summerhouse.getValidityStart() == null
                    || summerhouse.getValidityEnd() == null || summerhouse.getTitle() == null) {
                Message.addErrorMessage("Užpildykite visus laukus");
                return "addSummerhouse?faces-redirect=false";
            } else if (summerhouse.getValidityStart().after(summerhouse.getValidityEnd())) {

                Message.addErrorMessage("Įvedėte neteisingą informaciją - rezervacijos pabaigos data yra ankstesnė negu pradžios");
                return "addSummerhouse?faces-redirect=false";
            } else if (summerhouseCRUD.findByTitle(summerhouse.getTitle()) != null) {

                Message.addErrorMessage("Įvedėte neteisingą informaciją - toks vasarnamio pavadinimas jau egzistuoja");
                return "addSummerhouse?faces-redirect=false";
            } else if (!"".equals(this.file.getFileName())) {
                Image image = new Image();
                image.setContent(IOUtils.toByteArray(this.file.getInputstream()));
                Integer imageId = imagesEjb.addImage(image);
                summerhouse.setPhotoImageid(image);
                summerhouseCRUD.addSummerhouse(summerhouse);
                Message.addSuccessMessage("Vasarnamis sėkmingai išsaugotas");
            } else {
                Message.addErrorMessage("Pridėkite nuotrauką");
                return "addSummerhouse?faces-redirect=false";
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
