
package DataAccess.EJB;

import DataAccess.JPA.Invitation;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kristaliukas
 */

@Stateless
public class InvitationCRUD {
    
    @PersistenceContext
    EntityManager in;
    
    public void addInvitation(Invitation invitation){
	in.persist(invitation);
	in.flush();
    }

    public Invitation findByCode(String code){
        try {
            Invitation invitation = (Invitation) in.createNamedQuery("Invitation.findByCode").setParameter("code", code).getSingleResult();
            return invitation;
        } catch (Exception e) {
            return null;
        }
    }
    
    public void deleteInvitation (Invitation invitation){
        Invitation invit = findByCode(invitation.getCode());
        in.remove(invit);
        in.flush();
    }
}
