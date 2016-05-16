
package DataAccess.JPA;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Liudas 
 */
@Entity
@Table(name = "RESERVATION")
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.id = :id"),
    @NamedQuery(name = "Reservation.findByReservationNumber", query = "SELECT r FROM Reservation r WHERE r.reservationNumber = :reservationNumber"),
    @NamedQuery(name = "Reservation.findByStartDate", query = "SELECT r FROM Reservation r WHERE r.startDate = :startDate"),
    @NamedQuery(name = "Reservation.findByEndDate", query = "SELECT r FROM Reservation r WHERE r.endDate = :endDate"),
    @NamedQuery(name = "Reservation.findByVersion", query = "SELECT r FROM Reservation r WHERE r.version = :version")})
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RESERVATION_NUMBER")
    private String reservationNumber;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Version
    @Column(name = "VERSION")
    private Integer version;
    
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Account accountId;
    
    @JoinColumn(name = "SUMMERHOUSE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Summerhouse summerhouseId;

    public Reservation() {
    }

    public Reservation(Integer id) {
        this.id = id;
    }

    public Reservation(Integer id, String reservationNumber) {
        this.id = id;
        this.reservationNumber = reservationNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
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

    public Summerhouse getSummerhouseId() {
        return summerhouseId;
    }

    public void setSummerhouseId(Summerhouse summerhouseId) {
        this.summerhouseId = summerhouseId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.reservationNumber);
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
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.reservationNumber, other.reservationNumber)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "DataAccess.JPA.Reservation[ id=" + id + " ]";
    }

}
