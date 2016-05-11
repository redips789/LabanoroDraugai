
package DataAccess.JPA;

import java.io.Serializable;
import java.util.Objects;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Liudas 
 */
@Entity
@Table(name = "INVITATION")
@NamedQueries({
    @NamedQuery(name = "Invitation.findAll", query = "SELECT i FROM Invitation i"),
    @NamedQuery(name = "Invitation.findById", query = "SELECT i FROM Invitation i WHERE i.id = :id"),
    @NamedQuery(name = "Invitation.findByCode", query = "SELECT i FROM Invitation i WHERE i.code = :code"),
    @NamedQuery(name = "Invitation.findByIsUsed", query = "SELECT i FROM Invitation i WHERE i.isUsed = :isUsed")})

public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODE")
    private String code;
    
    @Column(name = "IS_USED")
    private Boolean isUsed;
    
    @JoinColumn(name = "INVITER_ACCOUNTID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Account inviterAccountid;

    public Invitation() {
    }

    public Invitation(Integer id) {
        this.id = id;
    }

    public Invitation(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Account getInviterAccountid() {
        return inviterAccountid;
    }

    public void setInviterAccountid(Account inviterAccountid) {
        this.inviterAccountid = inviterAccountid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.code);
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
        final Invitation other = (Invitation) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "DataAccess.JPA.Invitation[ id=" + id + " ]";
    }

}
