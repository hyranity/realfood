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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mast3
 */
@Entity
@Table(name = "ORDERMEAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordermeal.findAll", query = "SELECT o FROM Ordermeal o")
    , @NamedQuery(name = "Ordermeal.findByOrdermealid", query = "SELECT o FROM Ordermeal o WHERE o.ordermealid = :ordermealid")
    , @NamedQuery(name = "Ordermeal.findByQuantity", query = "SELECT o FROM Ordermeal o WHERE o.quantity = :quantity")
    , @NamedQuery(name = "Ordermeal.findByIscanceled", query = "SELECT o FROM Ordermeal o WHERE o.iscanceled = :iscanceled")
    , @NamedQuery(name = "Ordermeal.findByIsredeemed", query = "SELECT o FROM Ordermeal o WHERE o.isredeemed = :isredeemed")
    , @NamedQuery(name = "Ordermeal.findByDateredeemed", query = "SELECT o FROM Ordermeal o WHERE o.dateredeemed = :dateredeemed")})
public class Ordermeal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERMEALID")
    private Integer ordermealid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCANCELED")
    private Boolean iscanceled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISREDEEMED")
    private Boolean isredeemed;
    @Column(name = "DATEREDEEMED")
    @Temporal(TemporalType.DATE)
    private Date dateredeemed;
    @JoinColumn(name = "MEALID", referencedColumnName = "MEALID")
    @ManyToOne(optional = false)
    private Meal mealid;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @ManyToOne(optional = false)
    private Studentorder orderid;

    public Ordermeal() {
    }

    public Ordermeal(Integer ordermealid) {
        this.ordermealid = ordermealid;
    }

    public Ordermeal(Integer ordermealid, int quantity, Boolean iscanceled, Boolean isredeemed) {
        this.ordermealid = ordermealid;
        this.quantity = quantity;
        this.iscanceled = iscanceled;
        this.isredeemed = isredeemed;
    }

    public Integer getOrdermealid() {
        return ordermealid;
    }

    public void setOrdermealid(Integer ordermealid) {
        this.ordermealid = ordermealid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getIscanceled() {
        return iscanceled;
    }

    public void setIscanceled(Boolean iscanceled) {
        this.iscanceled = iscanceled;
    }

    public Boolean getIsredeemed() {
        return isredeemed;
    }

    public void setIsredeemed(Boolean isredeemed) {
        this.isredeemed = isredeemed;
    }

    public Date getDateredeemed() {
        return dateredeemed;
    }

    public void setDateredeemed(Date dateredeemed) {
        this.dateredeemed = dateredeemed;
    }

    public Meal getMealid() {
        return mealid;
    }

    public void setMealid(Meal mealid) {
        this.mealid = mealid;
    }

    public Studentorder getOrderid() {
        return orderid;
    }

    public void setOrderid(Studentorder orderid) {
        this.orderid = orderid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordermealid != null ? ordermealid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordermeal)) {
            return false;
        }
        Ordermeal other = (Ordermeal) object;
        if ((this.ordermealid == null && other.ordermealid != null) || (this.ordermealid != null && !this.ordermealid.equals(other.ordermealid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Ordermeal[ ordermealid=" + ordermealid + " ]";
    }
    
}
