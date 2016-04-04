/*
 *  Author: Liudas Petrelis
 */
package VU.KomandaX.LabanoroDraugai;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "PAID_FEES")
@NamedQueries({
    @NamedQuery(name = "PaidFees.findAll", query = "SELECT p FROM PaidFees p"),
    @NamedQuery(name = "PaidFees.findById", query = "SELECT p FROM PaidFees p WHERE p.id = :id"),
    @NamedQuery(name = "PaidFees.findByPaidDate", query = "SELECT p FROM PaidFees p WHERE p.paidDate = :paidDate")})
public class PaidFees implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PAID_DATE")
    @Temporal(TemporalType.DATE)
    private Date paidDate;
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Account accountId;
    @JoinColumn(name = "FEE", referencedColumnName = "TITLE")
    @ManyToOne(optional = false)
    private Fee fee;

    public PaidFees() {
    }

    public PaidFees(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaidFees)) {
            return false;
        }
        PaidFees other = (PaidFees) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VU.KomandaX.LabanoroDraugai.PaidFees[ id=" + id + " ]";
    }
    
}
