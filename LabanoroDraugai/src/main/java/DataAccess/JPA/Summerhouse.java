
package DataAccess.JPA;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "SUMMERHOUSE")
@NamedQueries({
    @NamedQuery(name = "Summerhouse.findAll", query = "SELECT s FROM Summerhouse s"),
    @NamedQuery(name = "Summerhouse.findById", query = "SELECT s FROM Summerhouse s WHERE s.id = :id"),
    @NamedQuery(name = "Summerhouse.findByTitle", query = "SELECT s FROM Summerhouse s WHERE s.title = :title"),
    @NamedQuery(name = "Summerhouse.findByDescription", query = "SELECT s FROM Summerhouse s WHERE s.description = :description"),
    @NamedQuery(name = "Summerhouse.findByBeds", query = "SELECT s FROM Summerhouse s WHERE s.beds = :beds"),
    @NamedQuery(name = "Summerhouse.findByValidityStart", query = "SELECT s FROM Summerhouse s WHERE s.validityStart = :validityStart"),
    @NamedQuery(name = "Summerhouse.findByValidityEnd", query = "SELECT s FROM Summerhouse s WHERE s.validityEnd = :validityEnd"),
    @NamedQuery(name = "Summerhouse.findByCost", query = "SELECT s FROM Summerhouse s WHERE s.cost = :cost"),
    @NamedQuery(name = "Summerhouse.findByVersion", query = "SELECT s FROM Summerhouse s WHERE s.version = :version")})
public class Summerhouse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 30)
    @Column(name = "TITLE")
    private String title;
    
    @Size(max = 250)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "BEDS")
    private Integer beds;
    
    @Column(name = "VALIDITY_START")
    @Temporal(TemporalType.DATE)
    private Date validityStart;
    
    @Column(name = "VALIDITY_END")
    @Temporal(TemporalType.DATE)
    private Date validityEnd;
    
    @Column(name = "COST")
    private Integer cost;
    
    @Version
    @Column(name = "VERSION")
    private Integer version;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "summerhouseId")
    private List<Reservation> reservationList;
    
    @JoinColumn(name = "PHOTO_IMAGEID", referencedColumnName = "ID")
    @ManyToOne
    private Image photoImageid;

    public Summerhouse() {
    }

    public Summerhouse(Integer id) {
        this.id = id;
    }

    public Summerhouse(Integer id, String title) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Date getValidityStart() {
        return validityStart;
    }

    public void setValidityStart(Date validityStart) {
        this.validityStart = validityStart;
    }

    public Date getValidityEnd() {
        return validityEnd;
    }

    public void setValidityEnd(Date validityEnd) {
        this.validityEnd = validityEnd;
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

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public Image getPhotoImageid() {
        return photoImageid;
    }

    public void setPhotoImageid(Image photoImageid) {
        this.photoImageid = photoImageid;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.title);
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
        final Summerhouse other = (Summerhouse) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "DataAccess.JPA.Summerhouse[ id=" + id + " ]";
    }

}
