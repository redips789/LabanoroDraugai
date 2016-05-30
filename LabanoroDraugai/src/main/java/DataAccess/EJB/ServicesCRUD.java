/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.EJB;

import DataAccess.JPA.Services;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static javax.persistence.PersistenceContextType.TRANSACTION;
import javax.persistence.Query;
import static javax.persistence.SynchronizationType.UNSYNCHRONIZED;

/**
 *
 * @author Liudas
 */
@Stateless
public class ServicesCRUD {

    @PersistenceContext(type = TRANSACTION, synchronization = UNSYNCHRONIZED)
    private EntityManager em;

    public List<Services> findAllServices() {
        Query query = em.createQuery("SELECT s FROM Services s");
        return (List<Services>) query.getResultList();
    }

    public void insertService(Services service) {
        em.persist(service);
        em.joinTransaction();
        em.flush();
    }

    public void deleteService(Services service) {
        em.remove(em.merge(service));
        em.joinTransaction();
        em.flush();
    }

    public Services updateService(Services service) {
        Services b = em.merge(service); // reference to another object than the one passed in when the object was already loaded in the current context.
        em.joinTransaction();
        em.flush();
        return b;
    }

    public Services findByTitle(String title) {
        try {
            Services query = (Services) em.createQuery("SELECT s FROM Services s WHERE s.title = :title").setParameter("title", title).getSingleResult();
            return query;
        } catch (Exception e) {
            return null;
        }
    }

}
