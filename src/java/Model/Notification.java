/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author mast3
 */
@Entity
@Table(name = "NOTIFICATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n")
    , @NamedQuery(name = "Notification.findByNotificationid", query = "SELECT n FROM Notification n WHERE n.notificationid = :notificationid")
    , @NamedQuery(name = "Notification.findByNotificationname", query = "SELECT n FROM Notification n WHERE n.notificationname = :notificationname")
    , @NamedQuery(name = "Notification.findByDescription", query = "SELECT n FROM Notification n WHERE n.description = :description")
    , @NamedQuery(name = "Notification.findByDateissued", query = "SELECT n FROM Notification n WHERE n.dateissued = :dateissued")})
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NOTIFICATIONID")
    private String notificationid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NOTIFICATIONNAME")
    private String notificationname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEISSUED")
    @Temporal(TemporalType.DATE)
    private Date dateissued;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notificationid")
    private Collection<Notificationstudent> notificationstudentCollection;

    public Notification() {
    }

    public Notification(String notificationid) {
        this.notificationid = notificationid;
    }

    public Notification(String notificationid, String notificationname, String description, Date dateissued) {
        this.notificationid = notificationid;
        this.notificationname = notificationname;
        this.description = description;
        this.dateissued = dateissued;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getNotificationname() {
        return notificationname;
    }

    public void setNotificationname(String notificationname) {
        this.notificationname = notificationname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateissued() {
        return dateissued;
    }

    public void setDateissued(Date dateissued) {
        this.dateissued = dateissued;
    }

    @XmlTransient
    public Collection<Notificationstudent> getNotificationstudentCollection() {
        return notificationstudentCollection;
    }

    public void setNotificationstudentCollection(Collection<Notificationstudent> notificationstudentCollection) {
        this.notificationstudentCollection = notificationstudentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationid != null ? notificationid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notificationid == null && other.notificationid != null) || (this.notificationid != null && !this.notificationid.equals(other.notificationid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Notification[ notificationid=" + notificationid + " ]";
    }
    
}
