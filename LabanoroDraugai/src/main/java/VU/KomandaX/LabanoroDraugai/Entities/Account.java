/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VU.KomandaX.LabanoroDraugai.Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author darbas
 */
@Entity
@Table(name = "ACCOUNT", catalog = "", schema = "LABANORASDB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByFirstName", query = "SELECT a FROM Account a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Account.findByLastName", query = "SELECT a FROM Account a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Account.findByNextPayment", query = "SELECT a FROM Account a WHERE a.nextPayment = :nextPayment"),
    @NamedQuery(name = "Account.findByDateOfBirth", query = "SELECT a FROM Account a WHERE a.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Account.findByPhoneNum", query = "SELECT a FROM Account a WHERE a.phoneNum = :phoneNum"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByStatus", query = "SELECT a FROM Account a WHERE a.status = :status"),
    @NamedQuery(name = "Account.findByPoints", query = "SELECT a FROM Account a WHERE a.points = :points"),
    @NamedQuery(name = "Account.findByTimeSpentOnHoliday", query = "SELECT a FROM Account a WHERE a.timeSpentOnHoliday = :timeSpentOnHoliday"),
    @NamedQuery(name = "Account.findByFacebookid", query = "SELECT a FROM Account a WHERE a.facebookid = :facebookid")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
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
    @Column(name = "PHONE_NUM")
    private Integer phoneNum;
    @Size(max = 30)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "POINTS")
    private Integer points;
    @Column(name = "TIME_SPENT_ON_HOLIDAY")
    private Integer timeSpentOnHoliday;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FACEBOOKID")
    private String facebookid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<Reservation> reservationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<PaidFees> paidFeesCollection;

    public Account() {
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

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    @XmlTransient
    public Collection<Reservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<Reservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VU.KomandaX.LabanoroDraugai.Entities.Account[ id=" + id + " ]";
    }
    
}
