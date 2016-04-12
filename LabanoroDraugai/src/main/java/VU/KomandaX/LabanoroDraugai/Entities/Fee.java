/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VU.KomandaX.LabanoroDraugai.Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author darbas
 */
@Entity
@Table(name = "FEE", catalog = "", schema = "LABANORASDB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fee.findAll", query = "SELECT f FROM Fee f"),
    @NamedQuery(name = "Fee.findById", query = "SELECT f FROM Fee f WHERE f.id = :id"),
    @NamedQuery(name = "Fee.findByTitle", query = "SELECT f FROM Fee f WHERE f.title = :title"),
    @NamedQuery(name = "Fee.findByAmount", query = "SELECT f FROM Fee f WHERE f.amount = :amount"),
    @NamedQuery(name = "Fee.findByDescription", query = "SELECT f FROM Fee f WHERE f.description = :description")})
public class Fee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TITLE")
    private String title;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private Double amount;
    @Size(max = 254)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fee")
    private Collection<PaidFees> paidFeesCollection;

    public Fee() {
    }

    public Fee(Integer id) {
        this.id = id;
    }

    public Fee(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<PaidFees> getPaidFeesCollection() {
        return paidFeesCollection;
    }

    public void setPaidFeesCollection(Collection<PaidFees> paidFeesCollection) {
        this.paidFeesCollection = paidFeesCollection;
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
        if (!(object instanceof Fee)) {
            return false;
        }
        Fee other = (Fee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VU.KomandaX.LabanoroDraugai.Entities.Fee[ id=" + id + " ]";
    }
    
}
