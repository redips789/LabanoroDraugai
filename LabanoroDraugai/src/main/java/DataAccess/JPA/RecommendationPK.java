
package DataAccess.JPA;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Liudas 
 */
@Embeddable
public class RecommendationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEIVER_ACCOUNTID")
    private int receiverAccountid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GIVER_ACCOUNTID")
    private int giverAccountid;

    public RecommendationPK() {
    }

    public RecommendationPK(int receiverAccountid, int giverAccountid) {
        this.receiverAccountid = receiverAccountid;
        this.giverAccountid = giverAccountid;
    }

    public int getReceiverAccountid() {
        return receiverAccountid;
    }

    public void setReceiverAccountid(int receiverAccountid) {
        this.receiverAccountid = receiverAccountid;
    }

    public int getGiverAccountid() {
        return giverAccountid;
    }

    public void setGiverAccountid(int giverAccountid) {
        this.giverAccountid = giverAccountid;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.receiverAccountid;
        hash = 17 * hash + this.giverAccountid;
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
        final RecommendationPK other = (RecommendationPK) obj;
        if (this.receiverAccountid != other.receiverAccountid) {
            return false;
        }
        if (this.giverAccountid != other.giverAccountid) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "DataAccess.JPA.RecommendationPK[ receiverAccountid=" + receiverAccountid + ", giverAccountid=" + giverAccountid + " ]";
    }

}
