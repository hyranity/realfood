/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mast3
 */
@Entity
@Table(name = "NOTIFICATIONSTUDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificationstudent.findAll", query = "SELECT n FROM Notificationstudent n")
    , @NamedQuery(name = "Notificationstudent.findByNotificationstudentid", query = "SELECT n FROM Notificationstudent n WHERE n.notificationstudentid = :notificationstudentid")
    , @NamedQuery(name = "Notificationstudent.findByIsread", query = "SELECT n FROM Notificationstudent n WHERE n.isread = :isread")})
public class Notificationstudent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "NOTIFICATIONSTUDENTID")
    private String notificationstudentid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISREAD")
    private Boolean isread;
    @JoinColumn(name = "NOTIFICATIONID", referencedColumnName = "NOTIFICATIONID")
    @ManyToOne(optional = false)
    private Notification notificationid;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne(optional = false)
    private Student studentid;

    public Notificationstudent() {
    }

    public Notificationstudent(String notificationstudentid) {
        this.notificationstudentid = notificationstudentid;
    }

    public Notificationstudent(String notificationstudentid, Boolean isread) {
        this.notificationstudentid = notificationstudentid;
        this.isread = isread;
    }

    public String getNotificationstudentid() {
        return notificationstudentid;
    }

    public void setNotificationstudentid(String notificationstudentid) {
        this.notificationstudentid = notificationstudentid;
    }

    public Boolean getIsread() {
        return isread;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    public Notification getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(Notification notificationid) {
        this.notificationid = notificationid;
    }

    public Student getStudentid() {
        return studentid;
    }

    public void setStudentid(Student studentid) {
        this.studentid = studentid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationstudentid != null ? notificationstudentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificationstudent)) {
            return false;
        }
        Notificationstudent other = (Notificationstudent) object;
        if ((this.notificationstudentid == null && other.notificationstudentid != null) || (this.notificationstudentid != null && !this.notificationstudentid.equals(other.notificationstudentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Notificationstudent[ notificationstudentid=" + notificationstudentid + " ]";
    }
    
}
