/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.JPA;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Povilas
 */
@Entity
@Table(name = "RECOMENDATION")
@NamedQueries({
    @NamedQuery(name = "Recomendation.findAll", query = "SELECT r FROM Recomendation r"),
    @NamedQuery(name = "Recomendation.findByReceiverFbid", query = "SELECT r FROM Recomendation r WHERE r.recomendationPK.receiverFbid = :receiverFbid"),
    @NamedQuery(name = "Recomendation.findByGiverFbid", query = "SELECT r FROM Recomendation r WHERE r.recomendationPK.giverFbid = :giverFbid"),
    @NamedQuery(name = "Recomendation.findByIsGiven", query = "SELECT r FROM Recomendation r WHERE r.isGiven = :isGiven"),
    @NamedQuery(name = "Recomendation.findByRecDate", query = "SELECT r FROM Recomendation r WHERE r.recDate = :recDate")})
public class Recomendation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecomendationPK recomendationPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_GIVEN")
    private Boolean isGiven;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REC_DATE")
    @Temporal(TemporalType.DATE)
    private Date recDate;

    public Recomendation() {
    }

    public Recomendation(RecomendationPK recomendationPK) {
        this.recomendationPK = recomendationPK;
    }

    public Recomendation(RecomendationPK recomendationPK, Boolean isGiven, Date recDate) {
        this.recomendationPK = recomendationPK;
        this.isGiven = isGiven;
        this.recDate = recDate;
    }

    public Recomendation(String receiverFbid, String giverFbid) {
        this.recomendationPK = new RecomendationPK(receiverFbid, giverFbid);
    }

    public RecomendationPK getRecomendationPK() {
        return recomendationPK;
    }

    public void setRecomendationPK(RecomendationPK recomendationPK) {
        this.recomendationPK = recomendationPK;
    }

    public Boolean getIsGiven() {
        return isGiven;
    }

    public void setIsGiven(Boolean isGiven) {
        this.isGiven = isGiven;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recomendationPK != null ? recomendationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recomendation)) {
            return false;
        }
        Recomendation other = (Recomendation) object;
        if ((this.recomendationPK == null && other.recomendationPK != null) || (this.recomendationPK != null && !this.recomendationPK.equals(other.recomendationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.JPA.Recomendation[ recomendationPK=" + recomendationPK + " ]";
    }
    
}
