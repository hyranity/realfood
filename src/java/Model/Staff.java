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
@Table(name = "STAFF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s")
    , @NamedQuery(name = "Staff.findByStaffid", query = "SELECT s FROM Staff s WHERE s.staffid = :staffid")
    , @NamedQuery(name = "Staff.findByFirstname", query = "SELECT s FROM Staff s WHERE s.firstname = :firstname")
    , @NamedQuery(name = "Staff.findByLastname", query = "SELECT s FROM Staff s WHERE s.lastname = :lastname")
    , @NamedQuery(name = "Staff.findByGender", query = "SELECT s FROM Staff s WHERE s.gender = :gender")
    , @NamedQuery(name = "Staff.findByIshired", query = "SELECT s FROM Staff s WHERE s.ishired = :ishired")
    , @NamedQuery(name = "Staff.findByDatejoined", query = "SELECT s FROM Staff s WHERE s.datejoined = :datejoined")
    , @NamedQuery(name = "Staff.findByStaffrole", query = "SELECT s FROM Staff s WHERE s.staffrole = :staffrole")
    , @NamedQuery(name = "Staff.findByMykad", query = "SELECT s FROM Staff s WHERE s.mykad = :mykad")
    , @NamedQuery(name = "Staff.findByPasswordsalt", query = "SELECT s FROM Staff s WHERE s.passwordsalt = :passwordsalt")
    , @NamedQuery(name = "Staff.findByPassword", query = "SELECT s FROM Staff s WHERE s.password = :password")})
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "STAFFID")
    private String staffid;
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
    @Column(name = "GENDER")
    private Character gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISHIRED")
    private Boolean ishired;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEJOINED")
    @Temporal(TemporalType.DATE)
    private Date datejoined;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "STAFFROLE")
    private String staffrole;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "MYKAD")
    private String mykad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PASSWORDSALT")
    private String passwordsalt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PASSWORD")
    private String password;

    public Staff() {
    }

    public Staff(String staffid) {
        this.staffid = staffid;
    }

    public Staff(String staffid, String firstname, String lastname, Character gender, Boolean ishired, Date datejoined, String staffrole, String mykad, String passwordsalt, String password) {
        this.staffid = staffid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.ishired = ishired;
        this.datejoined = datejoined;
        this.staffrole = staffrole;
        this.mykad = mykad;
        this.passwordsalt = passwordsalt;
        this.password = password;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
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

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Boolean getIshired() {
        return ishired;
    }

    public void setIshired(Boolean ishired) {
        this.ishired = ishired;
    }

    public Date getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(Date datejoined) {
        this.datejoined = datejoined;
    }

    public String getStaffrole() {
        return staffrole;
    }

    public void setStaffrole(String staffrole) {
        this.staffrole = staffrole;
    }

    public String getMykad() {
        return mykad;
    }

    public void setMykad(String mykad) {
        this.mykad = mykad;
    }

    public String getPasswordsalt() {
        return passwordsalt;
    }

    public void setPasswordsalt(String passwordsalt) {
        this.passwordsalt = passwordsalt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staffid != null ? staffid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.staffid == null && other.staffid != null) || (this.staffid != null && !this.staffid.equals(other.staffid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Staff[ staffid=" + staffid + " ]";
    }
    
}
