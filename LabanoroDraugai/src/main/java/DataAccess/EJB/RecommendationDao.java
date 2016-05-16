
package DataAccess.EJB;

import DataAccess.JPA.Recommendation;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Kristaliukas
 */

@Stateless
public class RecommendationDao {
    @PersistenceContext
    EntityManager rm;
    
    public void addRecommendation(Recommendation rec){
	rm.persist(rec);
	rm.flush();
    }
    
    public void updateRecommendation(Recommendation rec){
        Recommendation r = findByBoth(rec.getRecommendationPK().getReceiverAccountid(), rec.getRecommendationPK().getGiverAccountid());
        r.setIsGiven(Boolean.TRUE);
        rm.persist(r);
	rm.flush();
    }
    
    public void deleteRecommendation(Recommendation rec) {
        Recommendation rcm = findByBoth(rec.getRecommendationPK().getReceiverAccountid(), rec.getRecommendationPK().getGiverAccountid());
        rm.remove(rcm);
        rm.flush();
    }
    
    public void deleteRecommendations(int id){
        int deletedCount = rm.createNamedQuery("Recommendation.deleteById").setParameter("receiverAccountid", id).executeUpdate();
    }
    
    public List<Recommendation> findRecByReceiver(int id){
        List<Recommendation> rec = rm.createNamedQuery("Recommendation.findByReceiverAccountid").setParameter("receiverAccountid", id).getResultList();
        return rec;
    }
    
    public List<Recommendation> findForConfirm(int id, boolean status){
        List<Recommendation> rec = rm.createNamedQuery("Recommendation.findForConfirm").setParameter("giverAccountid", id).setParameter("isGiven", status).getResultList();
        return rec;
    }
    
    public Recommendation findByBoth(int rec_id, int giv_id){
        return (Recommendation) rm.createNamedQuery("Recommendation.findByBothId").setParameter("receiverAccountid", rec_id).setParameter("giverAccountid", giv_id).getSingleResult();
    }
    
    public List<Recommendation> findAllRecommendations(){
        List<Recommendation> rec = rm.createNamedQuery("Recommendation.findAll").getResultList();
        return rec;
    }
    
    public List<Recommendation> findOldDate(Date dat){
        List<Recommendation> rec = rm.createNamedQuery("Recommendation.findByOldRecDate").setParameter("sendDate", dat).getResultList();
        return rec;
    }
}
