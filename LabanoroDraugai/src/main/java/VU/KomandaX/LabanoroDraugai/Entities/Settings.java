
package VU.KomandaX.LabanoroDraugai.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "SETTINGS")
@NamedQueries({
    @NamedQuery(name = "Settings.findAll", query = "SELECT s FROM Settings s"),
    @NamedQuery(name = "Settings.findById", query = "SELECT s FROM Settings s WHERE s.id = :id"),
    @NamedQuery(name = "Settings.findByMaxUsers", query = "SELECT s FROM Settings s WHERE s.maxUsers = :maxUsers"),
    @NamedQuery(name = "Settings.findByFirstReservation", query = "SELECT s FROM Settings s WHERE s.firstReservation = :firstReservation"),
    @NamedQuery(name = "Settings.findBySecondReservation", query = "SELECT s FROM Settings s WHERE s.secondReservation = :secondReservation"),
    @NamedQuery(name = "Settings.findByThirdReservation", query = "SELECT s FROM Settings s WHERE s.thirdReservation = :thirdReservation"),
    @NamedQuery(name = "Settings.findByCloseReservation", query = "SELECT s FROM Settings s WHERE s.closeReservation = :closeReservation"),
    @NamedQuery(name = "Settings.findByFirstGroupMaxDay", query = "SELECT s FROM Settings s WHERE s.firstGroupMaxDay = :firstGroupMaxDay"),
    @NamedQuery(name = "Settings.findBySecondGroupMaxDay", query = "SELECT s FROM Settings s WHERE s.secondGroupMaxDay = :secondGroupMaxDay")})

public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "MAX_USERS")
    private Integer maxUsers;
    
    @Column(name = "FIRST_RESERVATION")
    @Temporal(TemporalType.DATE)
    private Date firstReservation;
    
    @Column(name = "SECOND_RESERVATION")
    @Temporal(TemporalType.DATE)
    private Date secondReservation;
    
    @Column(name = "THIRD_RESERVATION")
    @Temporal(TemporalType.DATE)
    private Date thirdReservation;
    
    @Column(name = "CLOSE_RESERVATION")
    @Temporal(TemporalType.DATE)
    private Date closeReservation;
    
    @Column(name = "FIRST_GROUP_MAX_DAY")
    private Integer firstGroupMaxDay;
    
    @Column(name = "SECOND_GROUP_MAX_DAY")
    private Integer secondGroupMaxDay;

    public Settings() {
    }

    public Settings(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Date getFirstReservation() {
        return firstReservation;
    }

    public void setFirstReservation(Date firstReservation) {
        this.firstReservation = firstReservation;
    }

    public Date getSecondReservation() {
        return secondReservation;
    }

    public void setSecondReservation(Date secondReservation) {
        this.secondReservation = secondReservation;
    }

    public Date getThirdReservation() {
        return thirdReservation;
    }

    public void setThirdReservation(Date thirdReservation) {
        this.thirdReservation = thirdReservation;
    }

    public Date getCloseReservation() {
        return closeReservation;
    }

    public void setCloseReservation(Date closeReservation) {
        this.closeReservation = closeReservation;
    }

    public Integer getFirstGroupMaxDay() {
        return firstGroupMaxDay;
    }

    public void setFirstGroupMaxDay(Integer firstGroupMaxDay) {
        this.firstGroupMaxDay = firstGroupMaxDay;
    }

    public Integer getSecondGroupMaxDay() {
        return secondGroupMaxDay;
    }

    public void setSecondGroupMaxDay(Integer secondGroupMaxDay) {
        this.secondGroupMaxDay = secondGroupMaxDay;
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
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings other = (Settings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VU.KomandaX.LabanoroDraugai.Settings[ id=" + id + " ]";
    }
    
}
