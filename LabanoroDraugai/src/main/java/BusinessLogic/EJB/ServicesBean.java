/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.ServicesCRUD;
import DataAccess.JPA.Services;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Liudas
 */

@Named
@RequestScoped
public class ServicesBean implements Serializable {
    
    @Inject ServicesCRUD serviceCRUD;
    
    private List<Services> services = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        services = serviceCRUD.findAllServices();
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }
}
