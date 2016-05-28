/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.EJB;

import DataAccess.EJB.AccountCRUD;
import DataAccess.EJB.ImageCRUD;
import DataAccess.EJB.SettingsCRUD;
import DataAccess.JPA.Account;
import DataAccess.JPA.Image;
import DataAccess.JPA.Settings;
import Messages.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
//import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.UploadedFile;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
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
public class EditSettingsBean implements Serializable {

    @Inject
    SettingsCRUD settingsEjb;

    private Settings settings;

    @PostConstruct
    public void init() {
        settings = settingsEjb.findSettings();
        if (settings == null) {
            settings = new Settings();
        }
    }

    public String saveSettingsChanges() {
        if (settings.getAllReservation().after(settings.getCloseReservation())) {
            Message.addErrorMessage("Visų rezervacijų data negali būti didesnė nei rezervacijų uždarymo datą");
        }else{
        Message.addSuccessMessage("Sėkmingai išsaugota");
        settingsEjb.updateSettings(settings);}
        return "settings";
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

}
