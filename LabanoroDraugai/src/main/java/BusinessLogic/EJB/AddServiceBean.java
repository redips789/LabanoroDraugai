/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.ImageCRUD;
import DataAccess.EJB.ServicesCRUD;
import DataAccess.JPA.Image;
import DataAccess.JPA.Services;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;
import Messages.Message;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Liudas
 */
@Named
@RequestScoped
public class AddServiceBean {

    @Inject
    ServicesCRUD servicesCRUD;

    private Services service;

    @PostConstruct
    public void init() {
        service = new Services();
    }

    public String saveService() {
        try {
            if (service.getCost() == null || service.getDescription() == null
                    || service.getValidityStart() == null
                    || service.getValidityEnd() == null || service.getTitle() == null) {
                Message.addErrorMessage("Užpildykite visus laukus");
                return "addService?faces-redirect=false";
            } else if (service.getValidityStart().after(service.getValidityEnd())) {
                Message.addErrorMessage("Nurodykite tinkamas datas");
                return "addService?faces-redirect=false";
            } else if (servicesCRUD.findByTitle(service.getTitle()) != null) {
                Message.addErrorMessage("Paslauga su tokiu pavadinimu jau egzistuoja");
                return "addService?faces-redirect=false";
            } else {
                servicesCRUD.insertService(service);
                Message.addSuccessMessage("Paslauga sėkmingai išsaugota");
            }
        } catch (Exception ex) {
            Message.addErrorMessage("Nepavyko pridėti Paslaugos");
            return "addService?faces-redirect=true";
        }
        return "services?faces-redirect=true";
    }


    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

}
