/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.EJB;

import DataAccess.JPA.PaidFees;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
