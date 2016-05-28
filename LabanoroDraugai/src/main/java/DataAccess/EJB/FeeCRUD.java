/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.EJB;

import DataAccess.JPA.Fee;
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
public class FeeCRUD {
    
    @PersistenceContext
    private EntityManager em;
    
    
    public Fee findFee(int feeId){
        try {
            Fee fee = (Fee) em.createNamedQuery("Fee.findById").setParameter("id", feeId).getResultList().get(0);
            return fee;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    
    public List<Fee> findAllFees() {
        Query query = em.createQuery("SELECT s FROM Fee s");
        return (List<Fee>) query.getResultList();
    }
}
