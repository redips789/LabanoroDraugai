
package DataAccess.JPA;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Liudas 
 */
@Entity
@Table(name = "RECOMMENDATION")
@NamedQueries({
    @NamedQuery(name = "Recommendation.findAll", query = "SELECT r FROM Recommendation r"),
    @NamedQuery(name = "Recommendation.findByReceiverAccountid", query = "SELECT r FROM Recommendation r WHERE r.recommendationPK.receiverAccountid = :receiverAccountid"),
    @NamedQuery(name = "Recommendation.findByGiverAccountid", query = "SELECT r FROM Recommendation r WHERE r.recommendationPK.giverAccountid = :giverAccountid"),
    @NamedQuery(name = "Recommendation.findByIsGiven", query = "SELECT r FROM Recommendation r WHERE r.isGiven = :isGiven"),
    @NamedQuery(name = "Recommendation.findBySendDate", query = "SELECT r FROM Recommendation r WHERE r.sendDate = :sendDate")})
public class Recommendation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecommendationPK recommendationPK;
    @Column(name = "IS_GIVEN")
    private Boolean isGiven;
    @Column(name = "SEND_DATE")
    @Temporal(TemporalType.DATE)
    private Date sendDate;
    @JoinColumn(name = "RECEIVER_ACCOUNTID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account;
    @JoinColumn(name = "GIVER_ACCOUNTID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account1;

    public Recommendation() {
    }

    public Recommendation(RecommendationPK recommendationPK) {
        this.recommendationPK = recommendationPK;
    }

    public Recommendation(int receiverAccountid, int giverAccountid) {
        this.recommendationPK = new RecommendationPK(receiverAccountid, giverAccountid);
    }

    public RecommendationPK getRecommendationPK() {
        return recommendationPK;
    }

    public void setRecommendationPK(RecommendationPK recommendationPK) {
        this.recommendationPK = recommendationPK;
    }

    public Boolean getIsGiven() {
        return isGiven;
    }

    public void setIsGiven(Boolean isGiven) {
        this.isGiven = isGiven;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.recommendationPK);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recommendation other = (Recommendation) obj;
        if (!Objects.equals(this.recommendationPK, other.recommendationPK)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "DataAccess.JPA.Recommendation[ recommendationPK=" + recommendationPK + " ]";
    }

}
