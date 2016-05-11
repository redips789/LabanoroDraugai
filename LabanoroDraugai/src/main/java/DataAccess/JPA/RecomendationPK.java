/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.JPA;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Povilas
 */
@Embeddable
public class RecomendationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RECEIVER_FBID")
    private String receiverFbid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "GIVER_FBID")
    private String giverFbid;

    public RecomendationPK() {
    }

    public RecomendationPK(String receiverFbid, String giverFbid) {
        this.receiverFbid = receiverFbid;
        this.giverFbid = giverFbid;
    }

    public String getReceiverFbid() {
        return receiverFbid;
    }

    public void setReceiverFbid(String receiverFbid) {
        this.receiverFbid = receiverFbid;
    }

    public String getGiverFbid() {
        return giverFbid;
    }

    public void setGiverFbid(String giverFbid) {
        this.giverFbid = giverFbid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiverFbid != null ? receiverFbid.hashCode() : 0);
        hash += (giverFbid != null ? giverFbid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecomendationPK)) {
            return false;
        }
        RecomendationPK other = (RecomendationPK) object;
        if ((this.receiverFbid == null && other.receiverFbid != null) || (this.receiverFbid != null && !this.receiverFbid.equals(other.receiverFbid))) {
            return false;
        }
        if ((this.giverFbid == null && other.giverFbid != null) || (this.giverFbid != null && !this.giverFbid.equals(other.giverFbid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.JPA.RecomendationPK[ receiverFbid=" + receiverFbid + ", giverFbid=" + giverFbid + " ]";
    }
    
}
