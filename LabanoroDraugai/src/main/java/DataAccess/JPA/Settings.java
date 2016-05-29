
package DataAccess.JPA;

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
import javax.persistence.Version;
import javax.validation.constraints.Size;

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
    @NamedQuery(name = "Settings.findByAllReservation", query = "SELECT s FROM Settings s WHERE s.allReservation = :allReservation"),
    @NamedQuery(name = "Settings.findByCloseReservation", query = "SELECT s FROM Settings s WHERE s.closeReservation = :closeReservation"),
    @NamedQuery(name = "Settings.findByMaxReservationDays", query = "SELECT s FROM Settings s WHERE s.maxReservationDays = :maxReservationDays"),
    @NamedQuery(name = "Settings.findByMembershipFee", query = "SELECT s FROM Settings s WHERE s.membershipFee = :membershipFee"),
    @NamedQuery(name = "Settings.findByMaxRecommendations", query = "SELECT s FROM Settings s WHERE s.maxRecommendations = :maxRecommendations"),
    @NamedQuery(name = "Settings.findByMinRecommendations", query = "SELECT s FROM Settings s WHERE s.minRecommendations = :minRecommendations"),
    @NamedQuery(name = "Settings.findByRecommendationsValidity", query = "SELECT s FROM Settings s WHERE s.recommendationsValidity = :recommendationsValidity"),
    @NamedQuery(name = "Settings.findByCancellationTime", query = "SELECT s FROM Settings s WHERE s.cancellationTime = :cancellationTime"),
    @NamedQuery(name = "Settings.findByVersion", query = "SELECT s FROM Settings s WHERE s.version = :version")})
public class Settings implements Serializable {

    @Size(max = 40)
    @Column(name = "STRIPE_PK")
    private String stripePk;
    @Size(max = 40)
    @Column(name = "STRIPE_SK")
    private String stripeSk;

    @Column(name = "FIRST_GROUP_SIZE")
    private Integer firstGroupSize;
    @Column(name = "SECOND_GROUP_SIZE")
    private Integer secondGroupSize;
    @Column(name = "THIRD_GROUP_SIZE")
    private Integer thirdGroupSize;

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
    
    @Column(name = "ALL_RESERVATION")
    @Temporal(TemporalType.DATE)
    private Date allReservation;
    
    @Column(name = "CLOSE_RESERVATION")
    @Temporal(TemporalType.DATE)
    private Date closeReservation;
    
    @Column(name = "MAX_RESERVATION_DAYS")
    private Integer maxReservationDays;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MEMBERSHIP_FEE")
    private Double membershipFee;
    
    @Column(name = "MAX_RECOMMENDATIONS")
    private Integer maxRecommendations;
    
    @Column(name = "MIN_RECOMMENDATIONS")
    private Integer minRecommendations;
    
    @Column(name = "RECOMMENDATIONS_VALIDITY")
    private Integer recommendationsValidity;
    
    @Column(name = "CANCELLATION_TIME")
    private Integer cancellationTime;
    
    @Version
    @Column(name = "VERSION")
    private Integer version;

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

    public Date getAllReservation() {
        return allReservation;
    }

    public void setAllReservation(Date allReservation) {
        this.allReservation = allReservation;
    }

    public Date getCloseReservation() {
        return closeReservation;
    }

    public void setCloseReservation(Date closeReservation) {
        this.closeReservation = closeReservation;
    }

    public Integer getMaxReservationDays() {
        return maxReservationDays;
    }

    public void setMaxReservationDays(Integer maxReservationDays) {
        this.maxReservationDays = maxReservationDays;
    }

    public Double getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(Double membershipFee) {
        this.membershipFee = membershipFee;
    }

    public Integer getMaxRecommendations() {
        return maxRecommendations;
    }

    public void setMaxRecommendations(Integer maxRecommendations) {
        this.maxRecommendations = maxRecommendations;
    }

    public Integer getMinRecommendations() {
        return minRecommendations;
    }

    public void setMinRecommendations(Integer minRecommendations) {
        this.minRecommendations = minRecommendations;
    }

    public Integer getRecommendationsValidity() {
        return recommendationsValidity;
    }

    public void setRecommendationsValidity(Integer recommendationsValidity) {
        this.recommendationsValidity = recommendationsValidity;
    }

    public Integer getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(Integer cancellationTime) {
        this.cancellationTime = cancellationTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
        return "DataAccess.JPA.Settings[ id=" + id + " ]";
    }

    public Integer getFirstGroupSize() {
        return firstGroupSize;
    }

    public void setFirstGroupSize(Integer firstGroupSize) {
        this.firstGroupSize = firstGroupSize;
    }

    public Integer getSecondGroupSize() {
        return secondGroupSize;
    }

    public void setSecondGroupSize(Integer secondGroupSize) {
        this.secondGroupSize = secondGroupSize;
    }

    public Integer getThirdGroupSize() {
        return thirdGroupSize;
    }

    public void setThirdGroupSize(Integer thirdGroupSize) {
        this.thirdGroupSize = thirdGroupSize;
    }

    public String getStripePk() {
        return stripePk;
    }

    public void setStripePk(String stripePk) {
        this.stripePk = stripePk;
    }

    public String getStripeSk() {
        return stripeSk;
    }

    public void setStripeSk(String stripeSk) {
        this.stripeSk = stripeSk;
    }

}
