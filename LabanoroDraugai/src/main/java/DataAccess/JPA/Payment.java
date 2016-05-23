
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Liudas 
 */
@Entity
@Table(name = "PAYMENT")
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"),
    @NamedQuery(name = "Payment.findByFirstName", query = "SELECT p FROM Payment p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "Payment.findByLastName", query = "SELECT p FROM Payment p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Payment.findByDateTime", query = "SELECT p FROM Payment p WHERE p.dateTime = :dateTime"),
    @NamedQuery(name = "Payment.findByStatus", query = "SELECT p FROM Payment p WHERE p.status = :status"),
    @NamedQuery(name = "Payment.findByClassName", query = "SELECT p FROM Payment p WHERE p.className = :className"),
    @NamedQuery(name = "Payment.findByMethodName", query = "SELECT p FROM Payment p WHERE p.methodName = :methodName")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 30)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 40)
    @Column(name = "CLASS_NAME")
    private String className;
    @Size(max = 40)
    @Column(name = "METHOD_NAME")
    private String methodName;

    public Payment() {
    }

    public Payment(Integer id) {
        this.id = id;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Payment other = (Payment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    

    

    @Override
    public String toString() {
        return "DataAccess.JPA.Payment[ id=" + id + " ]";
    }

}
