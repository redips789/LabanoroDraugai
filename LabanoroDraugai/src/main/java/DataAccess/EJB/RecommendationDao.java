
package DataAccess.EJB;

import DataAccess.JPA.Recomendation;
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
    
    public void addRecommendation(Recomendation rec){
	rm.persist(rec);
	rm.flush();
    }
    
    public void updateRecommendation(Recomendation rec){
        Recomendation r = findByBoth(rec.getRecomendationPK().getReceiverFbid(), rec.getRecomendationPK().getGiverFbid());
        r.setIsGiven(Boolean.TRUE);
        rm.persist(r);
	rm.flush();
    }
    
    public void deleteRecommendation(Recomendation rec) {
        Recomendation rcm = findByBoth(rec.getRecomendationPK().getReceiverFbid(), rec.getRecomendationPK().getGiverFbid());
	rm.remove(rcm);
        rm.flush();
    }
    
    public void deleteRecommendations(String fbid){
        int deletedCount = rm.createNamedQuery("Recomendation.deleteByFbid").setParameter("receiverFbid", fbid).executeUpdate();
    }
    
    public List<Recomendation> findRecByReceiver(String fbId){
        List<Recomendation> rec = rm.createNamedQuery("Recomendation.findByReceiverFbid").setParameter("receiverFbid", fbId).getResultList();
        return rec;
    }
    
    public List<Recomendation> findForConfirm(String fbId, boolean status){
        List<Recomendation> rec = rm.createNamedQuery("Recomendation.findForConfirm").setParameter("giverFbid", fbId).setParameter("isGiven", status).getResultList();
        return rec;
    }
    
    public Recomendation findByBoth(String rec_fbId, String giv_fbId){
        return (Recomendation) rm.createNamedQuery("Recomendation.findByBothFbid").setParameter("receiverFbid", rec_fbId).setParameter("giverFbid", giv_fbId).getSingleResult();
    }
    
    public List<Recomendation> findAllRecommendations(){
        List<Recomendation> rec = rm.createNamedQuery("Recomendation.findAll").getResultList();
        return rec;
    }
    
    public List<Recomendation> findOldDate(Date dat){
        List<Recomendation> rec = rm.createNamedQuery("Recomendation.findByOldRecDate").setParameter("recDate", dat).getResultList();
        return rec;
    }
}
