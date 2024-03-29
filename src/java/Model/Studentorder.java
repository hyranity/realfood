/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "STUDENTORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Studentorder.findAll", query = "SELECT s FROM Studentorder s")
    , @NamedQuery(name = "Studentorder.findByOrderid", query = "SELECT s FROM Studentorder s WHERE s.orderid = :orderid")
    , @NamedQuery(name = "Studentorder.findByChosendate", query = "SELECT s FROM Studentorder s WHERE s.chosendate = :chosendate")
    , @NamedQuery(name = "Studentorder.findByCouponcode", query = "SELECT s FROM Studentorder s WHERE s.couponcode = :couponcode")
    , @NamedQuery(name = "Studentorder.findByIsredeemed", query = "SELECT s FROM Studentorder s WHERE s.isredeemed = :isredeemed")
    , @NamedQuery(name = "Studentorder.findByIscanceled", query = "SELECT s FROM Studentorder s WHERE s.iscanceled = :iscanceled")
    , @NamedQuery(name = "Studentorder.findByTotalprice", query = "SELECT s FROM Studentorder s WHERE s.totalprice = :totalprice")
    , @NamedQuery(name = "Studentorder.findByDatecanceled", query = "SELECT s FROM Studentorder s WHERE s.datecanceled = :datecanceled")
    , @NamedQuery(name = "Studentorder.findByDatecreated", query = "SELECT s FROM Studentorder s WHERE s.datecreated = :datecreated")
    , @NamedQuery(name = "Studentorder.findByDateredeemed", query = "SELECT s FROM Studentorder s WHERE s.dateredeemed = :dateredeemed")})
public class Studentorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "ORDERID")
    private String orderid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHOSENDATE")
    @Temporal(TemporalType.DATE)
    private Date chosendate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "COUPONCODE")
    private String couponcode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISREDEEMED")
    private Boolean isredeemed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCANCELED")
    private Boolean iscanceled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALPRICE")
    private int totalprice;
    @Column(name = "DATECANCELED")
    @Temporal(TemporalType.DATE)
    private Date datecanceled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATECREATED")
    @Temporal(TemporalType.DATE)
    private Date datecreated;
    @Column(name = "DATEREDEEMED")
    @Temporal(TemporalType.DATE)
    private Date dateredeemed;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne(optional = false)
    private Student studentid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderid")
    private List<Ordermeal> ordermealList;

    public Studentorder() {
    }

    public Studentorder(String orderid) {
        this.orderid = orderid;
    }

    public Studentorder(String orderid, Date chosendate, String couponcode, Boolean isredeemed, Boolean iscanceled, int totalprice, Date datecreated) {
        this.orderid = orderid;
        this.chosendate = chosendate;
        this.couponcode = couponcode;
        this.isredeemed = isredeemed;
        this.iscanceled = iscanceled;
        this.totalprice = totalprice;
        this.datecreated = datecreated;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Date getChosendate() {
        return chosendate;
    }

    public void setChosendate(Date chosendate) {
        this.chosendate = chosendate;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    public Boolean getIsredeemed() {
        return isredeemed;
    }

    public void setIsredeemed(Boolean isredeemed) {
        this.isredeemed = isredeemed;
    }

    public Boolean getIscanceled() {
        return iscanceled;
    }

    public void setIscanceled(Boolean iscanceled) {
        this.iscanceled = iscanceled;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public Date getDatecanceled() {
        return datecanceled;
    }

    public void setDatecanceled(Date datecanceled) {
        this.datecanceled = datecanceled;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateredeemed() {
        return dateredeemed;
    }

    public void setDateredeemed(Date dateredeemed) {
        this.dateredeemed = dateredeemed;
    }

    public Student getStudentid() {
        return studentid;
    }

    public void setStudentid(Student studentid) {
        this.studentid = studentid;
    }

    @XmlTransient
    public List<Ordermeal> getOrdermealList() {
        return ordermealList;
    }

    public void setOrdermealList(List<Ordermeal> ordermealList) {
        this.ordermealList = ordermealList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studentorder)) {
            return false;
        }
        Studentorder other = (Studentorder) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Studentorder[ orderid=" + orderid + " ]";
    }
    
}
