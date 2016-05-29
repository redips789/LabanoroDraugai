/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.EJB;

import DataAccess.JPA.Account;
import DataAccess.JPA.ServicesReservation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static javax.persistence.PersistenceContextType.TRANSACTION;
import javax.persistence.Query;
import static javax.persistence.SynchronizationType.SYNCHRONIZED;
import static javax.persistence.SynchronizationType.UNSYNCHRONIZED;

/**
 *
 * @author Liudas
 */

@Stateless
public class ServiceReservationCRUD {
    
    @PersistenceContext(type = TRANSACTION, synchronization = UNSYNCHRONIZED)
    private EntityManager em;

    public List<ServicesReservation> getByAccount(Account acc) {
        Query query = em.createQuery("SELECT s FROM ServicesReservation s WHERE s.accountId = :acc").
                setParameter("acc", acc);
        return query.getResultList();
    }
    
     public void insertReservation (ServicesReservation reservation){
        em.persist(reservation);
        em.joinTransaction();
        em.flush();
    }
}
