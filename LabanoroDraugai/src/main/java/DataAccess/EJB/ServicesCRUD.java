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
    
}
