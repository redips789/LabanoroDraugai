/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.ServicesCRUD;
import DataAccess.JPA.Services;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import Messages.Message;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author ADMIN
 */

@Named
@RequestScoped
public class EditServiceBean {
    
    @Inject ServicesCRUD servicesCRUD;
    
    private String title;
    
    private Services detailedService;
    
    @PostConstruct
    public void init() {
        detailedService = new Services();
    }
    
    public String saveServiceChanges() {
        try {
            Services temp = servicesCRUD.findByTitle(detailedService.getTitle());
            if (detailedService.getValidityStart().after(detailedService.getValidityEnd())) {
                Message.addErrorMessage("Nurodykite tinkamas datas!");
            } else if (temp != null && detailedService.getId() != temp.getId()) {
                Message.addErrorMessage("Paslauga su tokiu pavadinimu jau egzistuoja");
            } else {
                servicesCRUD.updateService(detailedService);
                Message.addSuccessMessage("Paslauga sėkmingai išsaugota!");
            }
        } catch(Exception e){
            Message.addErrorMessage("Įvyko klaida");
            return "editRegistrationForm?faces-redirect=true";
        }
        return "services";
    }

    public String deleteService() {
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String serviceTitle = params.get("title");
            Services serv = servicesCRUD.findByTitle(serviceTitle);
            servicesCRUD.deleteService(serv);
            Message.addSuccessMessage("Paslauga sėkmingai ištrinta");
        } catch (Exception ex) {
             Message.addErrorMessage("Įvyko klaida!");
            return "services?faces-redirect=true";
        }
        return "services?faces-redirect=true";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        detailedService = servicesCRUD.findByTitle(title);
    }

    public Services getDetailedService() {
        return detailedService;
    }

    public void setDetailedService(Services detailedService) {
        this.detailedService = detailedService;
    }
    
}
