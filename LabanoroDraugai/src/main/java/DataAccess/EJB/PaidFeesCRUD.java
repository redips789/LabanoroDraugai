/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.EJB;

import DataAccess.JPA.PaidFees;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author darbas
 */
@Stateless
public class PaidFeesCRUD {
    
    @PersistenceContext
    private EntityManager em;
    
    public void addFee(PaidFees paidFees){
        em.persist(paidFees);
    }
    
    public List<PaidFees> findAllPaidFees() {
        Query query = em.createQuery("SELECT s FROM PaidFees s");
        return (List<PaidFees>) query.getResultList();
    }
    
    public PaidFees findPaidFeesById(int id){
        return (PaidFees) em.createNamedQuery("PaidFees.findById").setParameter("id", id).getSingleResult();
    }
    
    public void deletePaidFees(PaidFees paidFee){
        em.remove(em.merge(paidFee));
    }
    
}
