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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Liudas
 */
@Entity
@Table(name = "ACCOUNT")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByFirstName", query = "SELECT a FROM Account a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Account.findByLastName", query = "SELECT a FROM Account a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Account.findByNextPayment", query = "SELECT a FROM Account a WHERE a.nextPayment = :nextPayment"),
    @NamedQuery(name = "Account.findByDateOfBirth", query = "SELECT a FROM Account a WHERE a.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Account.findByPhoneNum", query = "SELECT a FROM Account a WHERE a.phoneNum = :phoneNum"),
    @NamedQuery(name = "Account.findByStatus", query = "SELECT a FROM Account a WHERE a.status = :status"),
    @NamedQuery(name = "Account.findByPoints", query = "SELECT a FROM Account a WHERE a.points = :points"),
    @NamedQuery(name = "Account.findByTimeSpentOnHoliday", query = "SELECT a FROM Account a WHERE a.timeSpentOnHoliday = :timeSpentOnHoliday"),
    @NamedQuery(name = "Account.findByCity", query = "SELECT a FROM Account a WHERE a.city = :city"),
    @NamedQuery(name = "Account.findByDescription", query = "SELECT a FROM Account a WHERE a.description = :description"),
    @NamedQuery(name = "Account.findByPhoto", query = "SELECT a FROM Account a WHERE a.photo = :photo"),
    @NamedQuery(name = "Account.findByFacebookid", query = "SELECT a FROM Account a WHERE a.facebookid = :facebookid"),
    @NamedQuery(name = "Account.findByVersion", query = "SELECT a FROM Account a WHERE a.version = :version"),
    @NamedQuery(name = "Account.findByMemberSince", query = "SELECT a FROM Account a WHERE a.memberSince = :memberSince")})

public class Account implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private List<Messages> messagesList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private List<ServicesReservation> servicesReservationList;

    @Column(name = "RESERVED_DAYS")
    private Integer reservedDays;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private List<Reservation> reservationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private List<PaidFees> paidFeesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Recommendation> recommendationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account1")
    private List<Recommendation> recommendationList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(min = 1, max = 50)
    @Column(name = "EMAIL")
    private String email;

    @Size(max = 30)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Size(max = 30)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "NEXT_PAYMENT")
    @Temporal(TemporalType.DATE)
    private Date nextPayment;

    @Column(name = "DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Size(max = 20)
    @Column(name = "PHONE_NUM")
    private String phoneNum;

    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;

    @Column(name = "POINTS")
    private Integer points;

    @Column(name = "TIME_SPENT_ON_HOLIDAY")
    private Integer timeSpentOnHoliday;

    @Size(max = 50)
    @Column(name = "CITY")
    private String city;

    @Size(max = 250)
    @Column(name = "DESCRIPTION")
    private String description;

    @Size(max = 254)
    @Column(name = "PHOTO")
    private String photo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FACEBOOKID")
    private String facebookid;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "MEMBER_SINCE")
    @Temporal(TemporalType.DATE)
    private Date memberSince;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inviterAccountid")
    private List<Invitation> invitationList;

    @JoinColumn(name = "PHOTO_IMAGEID", referencedColumnName = "ID")
    @ManyToOne
    private Image photoImageid;

    @Transient
    private int age;

    @Transient
    private boolean renderFb;

    @Transient
    private boolean renderDb;

    public Account() {
    }

    public boolean isRenderFb() {
        return renderFb;
    }

    public void setRenderFb(boolean renderFb) {
        this.renderFb = renderFb;
    }

    public boolean isRenderDb() {
        return renderDb;
    }

    public void setRenderDb(boolean renderDb) {
        this.renderDb = renderDb;
    }
    
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Account(Integer id) {
        this.id = id;
    }

    public Account(Integer id, String email, String facebookid) {
        this.id = id;
        this.email = email;
        this.facebookid = facebookid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(Date nextPayment) {
        this.nextPayment = nextPayment;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getTimeSpentOnHoliday() {
        return timeSpentOnHoliday;
    }

    public void setTimeSpentOnHoliday(Integer timeSpentOnHoliday) {
        this.timeSpentOnHoliday = timeSpentOnHoliday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(Date memberSince) {
        this.memberSince = memberSince;
    }

    public List<Invitation> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(List<Invitation> invitationList) {
        this.invitationList = invitationList;
    }

    public Image getPhotoImageid() {
        return photoImageid;
    }

    public void setPhotoImageid(Image photoImageid) {
        this.photoImageid = photoImageid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.facebookid);
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
        final Account other = (Account) obj;
        if (!Objects.equals(this.facebookid, other.facebookid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.JPA.Account[ id=" + id + " ]";
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public List<PaidFees> getPaidFeesList() {
        return paidFeesList;
    }

    public void setPaidFeesList(List<PaidFees> paidFeesList) {
        this.paidFeesList = paidFeesList;
    }

    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public List<Recommendation> getRecommendationList1() {
        return recommendationList1;
    }

    public void setRecommendationList1(List<Recommendation> recommendationList1) {
        this.recommendationList1 = recommendationList1;
    }

    public Integer getReservedDays() {
        return reservedDays;
    }

    public void setReservedDays(Integer reservedDays) {
        this.reservedDays = reservedDays;
    }

    public List<ServicesReservation> getServicesReservationList() {
        return servicesReservationList;
    }

    public void setServicesReservationList(List<ServicesReservation> servicesReservationList) {
        this.servicesReservationList = servicesReservationList;
    }

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

}
