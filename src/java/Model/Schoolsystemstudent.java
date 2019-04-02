/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mast3
 */
@Entity
@Table(name = "SCHOOLSYSTEMSTUDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schoolsystemstudent.findAll", query = "SELECT s FROM Schoolsystemstudent s")
    , @NamedQuery(name = "Schoolsystemstudent.findByStudentid", query = "SELECT s FROM Schoolsystemstudent s WHERE s.studentid = :studentid")
    , @NamedQuery(name = "Schoolsystemstudent.findByFirstname", query = "SELECT s FROM Schoolsystemstudent s WHERE s.firstname = :firstname")
    , @NamedQuery(name = "Schoolsystemstudent.findByLastname", query = "SELECT s FROM Schoolsystemstudent s WHERE s.lastname = :lastname")
    , @NamedQuery(name = "Schoolsystemstudent.findByStudentemail", query = "SELECT s FROM Schoolsystemstudent s WHERE s.studentemail = :studentemail")
    , @NamedQuery(name = "Schoolsystemstudent.findByGender", query = "SELECT s FROM Schoolsystemstudent s WHERE s.gender = :gender")
    , @NamedQuery(name = "Schoolsystemstudent.findByIsenrolled", query = "SELECT s FROM Schoolsystemstudent s WHERE s.isenrolled = :isenrolled")
    , @NamedQuery(name = "Schoolsystemstudent.findByDatejoined", query = "SELECT s FROM Schoolsystemstudent s WHERE s.datejoined = :datejoined")
    , @NamedQuery(name = "Schoolsystemstudent.findByMykad", query = "SELECT s FROM Schoolsystemstudent s WHERE s.mykad = :mykad")})
public class Schoolsystemstudent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "STUDENTID")
    private String studentid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "LASTNAME")
    private String lastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "STUDENTEMAIL")
    private String studentemail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GENDER")
    private Character gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISENROLLED")
    private Boolean isenrolled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEJOINED")
    @Temporal(TemporalType.DATE)
    private Date datejoined;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "MYKAD")
    private String mykad;

    public Schoolsystemstudent() {
    }

    public Schoolsystemstudent(String studentid) {
        this.studentid = studentid;
    }

    public Schoolsystemstudent(String studentid, String firstname, String lastname, String studentemail, Character gender, Boolean isenrolled, Date datejoined, String mykad) {
        this.studentid = studentid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.studentemail = studentemail;
        this.gender = gender;
        this.isenrolled = isenrolled;
        this.datejoined = datejoined;
        this.mykad = mykad;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStudentemail() {
        return studentemail;
    }

    public void setStudentemail(String studentemail) {
        this.studentemail = studentemail;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Boolean getIsenrolled() {
        return isenrolled;
    }

    public void setIsenrolled(Boolean isenrolled) {
        this.isenrolled = isenrolled;
    }

    public Date getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(Date datejoined) {
        this.datejoined = datejoined;
    }

    public String getMykad() {
        return mykad;
    }

    public void setMykad(String mykad) {
        this.mykad = mykad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentid != null ? studentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schoolsystemstudent)) {
            return false;
        }
        Schoolsystemstudent other = (Schoolsystemstudent) object;
        if ((this.studentid == null && other.studentid != null) || (this.studentid != null && !this.studentid.equals(other.studentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Schoolsystemstudent[ studentid=" + studentid + " ]";
    }
    
}
