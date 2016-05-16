/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountDao;
import DataAccess.EJB.ImageCrud;
import DataAccess.EJB.SettingsDao;
import DataAccess.JPA.Account;
import DataAccess.JPA.Image;
import DataAccess.JPA.Settings;
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
public class EditSettingsBean implements Serializable {

    @EJB
    SettingsDao settingsEjb;
    
    private Settings settings;

    @PostConstruct
    public void init() {
        settings = settingsEjb.findSettings();
    }

    public String saveSettingsChanges (){
        settingsEjb.updateSettings(settings);
        return "settings";
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    
    
}
