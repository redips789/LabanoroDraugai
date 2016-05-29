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
import javax.validation.constraints.Size;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "SERVICES_RESERVATION")
@NamedQueries({
    @NamedQuery(name = "ServicesReservation.findAll", query = "SELECT s FROM ServicesReservation s"),
    @NamedQuery(name = "ServicesReservation.findById", query = "SELECT s FROM ServicesReservation s WHERE s.id = :id"),
    @NamedQuery(name = "ServicesReservation.findByStartDate", query = "SELECT s FROM ServicesReservation s WHERE s.startDate = :startDate"),
    @NamedQuery(name = "ServicesReservation.findByEndDate", query = "SELECT s FROM ServicesReservation s WHERE s.endDate = :endDate"),
    @NamedQuery(name = "ServicesReservation.findByCost", query = "SELECT s FROM ServicesReservation s WHERE s.cost = :cost"),
    @NamedQuery(name = "ServicesReservation.findByVersion", query = "SELECT s FROM ServicesReservation s WHERE s.version = :version")})
public class ServicesReservation implements Serializable {

    @Size(max = 50)
    @Column(name = "NOTE")
    private String note;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "COST")
    private Integer cost;
    @Column(name = "VERSION")
    private Integer version;
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Account accountId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Services serviceId;

    public ServicesReservation() {
    }

    public ServicesReservation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Services getServiceId() {
        return serviceId;
    }

    public void setServiceId(Services serviceId) {
        this.serviceId = serviceId;
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
        if (!(object instanceof ServicesReservation)) {
            return false;
        }
        ServicesReservation other = (ServicesReservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.JPA.ServicesReservation[ id=" + id + " ]";
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}
