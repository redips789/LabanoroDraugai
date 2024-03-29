
package DataAccess.JPA;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Liudas 
 */
@Entity
@Table(name = "FEE")
@NamedQueries({
    @NamedQuery(name = "Fee.findAll", query = "SELECT f FROM Fee f"),
    @NamedQuery(name = "Fee.findById", query = "SELECT f FROM Fee f WHERE f.id = :id"),
    @NamedQuery(name = "Fee.findByAmount", query = "SELECT f FROM Fee f WHERE f.amount = :amount"),
    @NamedQuery(name = "Fee.findByDescription", query = "SELECT f FROM Fee f WHERE f.description = :description"),
    @NamedQuery(name = "Fee.findByVersion", query = "SELECT f FROM Fee f WHERE f.version = :version")})

public class Fee implements Serializable {

    @Column(name = "POINTS")
    private Integer points;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private Double amount;
    
    @Size(max = 254)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Version
    @Column(name = "VERSION")
    private Integer version;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fee")
    private List<PaidFees> paidFeesList;

    public Fee() {
    }

    public Fee(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<PaidFees> getPaidFeesList() {
        return paidFeesList;
    }

    public void setPaidFeesList(List<PaidFees> paidFeesList) {
        this.paidFeesList = paidFeesList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Fee other = (Fee) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    

    

    @Override
    public String toString() {
        return "DataAccess.JPA.Fee[ id=" + id + " ]";
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}
