/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.JPA;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Povilas
 */
@Entity
@Table(name = "REGISTRATION_FORM")
@NamedQueries({
    @NamedQuery(name = "RegistrationForm.findAll", query = "SELECT r FROM RegistrationForm r"),
    @NamedQuery(name = "RegistrationForm.findById", query = "SELECT r FROM RegistrationForm r WHERE r.id = :id"),
    @NamedQuery(name = "RegistrationForm.findByFirstName", query = "SELECT r FROM RegistrationForm r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "RegistrationForm.findByLastName", query = "SELECT r FROM RegistrationForm r WHERE r.lastName = :lastName"),
    @NamedQuery(name = "RegistrationForm.findByBirthday", query = "SELECT r FROM RegistrationForm r WHERE r.birthday = :birthday"),
    @NamedQuery(name = "RegistrationForm.findByCity", query = "SELECT r FROM RegistrationForm r WHERE r.city = :city"),
    @NamedQuery(name = "RegistrationForm.findByEmail", query = "SELECT r FROM RegistrationForm r WHERE r.email = :email"),
    @NamedQuery(name = "RegistrationForm.findByPhone", query = "SELECT r FROM RegistrationForm r WHERE r.phone = :phone"),
    @NamedQuery(name = "RegistrationForm.findByDescription", query = "SELECT r FROM RegistrationForm r WHERE r.description = :description")})
public class RegistrationForm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FIRST_NAME")
    private Boolean firstName;
    @Column(name = "LAST_NAME")
    private Boolean lastName;
    @Column(name = "BIRTHDAY")
    private Boolean birthday;
    @Column(name = "CITY")
    private Boolean city;
    @Column(name = "EMAIL")
    private Boolean email;
    @Column(name = "PHONE")
    private Boolean phone;
    @Column(name = "DESCRIPTION")
    private Boolean description;

    public RegistrationForm() {
    }

    public RegistrationForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFirstName() {
        return firstName;
    }

    public void setFirstName(Boolean firstName) {
        this.firstName = firstName;
    }

    public Boolean getLastName() {
        return lastName;
    }

    public void setLastName(Boolean lastName) {
        this.lastName = lastName;
    }

    public Boolean getBirthday() {
        return birthday;
    }

    public void setBirthday(Boolean birthday) {
        this.birthday = birthday;
    }

    public Boolean getCity() {
        return city;
    }

    public void setCity(Boolean city) {
        this.city = city;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public Boolean getPhone() {
        return phone;
    }

    public void setPhone(Boolean phone) {
        this.phone = phone;
    }

    public Boolean getDescription() {
        return description;
    }

    public void setDescription(Boolean description) {
        this.description = description;
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
        if (!(object instanceof RegistrationForm)) {
            return false;
        }
        RegistrationForm other = (RegistrationForm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.JPA.RegistrationForm[ id=" + id + " ]";
    }
    
}
