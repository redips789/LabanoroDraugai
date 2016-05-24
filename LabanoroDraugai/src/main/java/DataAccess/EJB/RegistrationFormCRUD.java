/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.EJB;

import DataAccess.JPA.RegistrationForm;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Povilas
 */
@Stateless
@LocalBean
public class RegistrationFormCRUD {

    @PersistenceContext
    EntityManager em;

    public RegistrationForm findRegistrationForm() {
        try {
            RegistrationForm form = (RegistrationForm) em.createNamedQuery("RegistrationForm.findAll").getSingleResult();
            return form;
        } catch (Exception e) {
            System.err.println("Caught manoException: " + "klaiidaaaaa " + e);
            return null;
        }
    }

    public RegistrationForm updateRegistrationForm(RegistrationForm changedForm) {
        RegistrationForm b = em.merge(changedForm); // reference to another object than the one passed in when the object was already loaded in the current context.
        em.flush();
        return b;
    }
}
