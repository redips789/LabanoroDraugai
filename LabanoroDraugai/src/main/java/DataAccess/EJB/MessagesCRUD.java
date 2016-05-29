/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.EJB;

import DataAccess.JPA.Messages;
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
public class MessagesCRUD {
    
    @PersistenceContext
    private EntityManager em;
    
    
    public Messages findMessages(int messageId){
        try {
            Messages messages = (Messages) em.createNamedQuery("Messages.findById").setParameter("id", messageId).getResultList().get(0);
            return messages;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    
    public List<Messages> findAllMessages() {
        Query query = em.createQuery("SELECT s FROM Messages s");
        return (List<Messages>) query.getResultList();
    }
    
    public void removeMessages(Messages messages){
        em.remove(em.merge(messages));
    }
    
    public void addMessages(Messages messages){
        em.persist(messages);
    }
}
