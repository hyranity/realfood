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
@Table(name = "ORDERMEAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordermeal.findAll", query = "SELECT o FROM Ordermeal o")
    , @NamedQuery(name = "Ordermeal.findByOrdermealid", query = "SELECT o FROM Ordermeal o WHERE o.ordermealid = :ordermealid")
    , @NamedQuery(name = "Ordermeal.findByQuantity", query = "SELECT o FROM Ordermeal o WHERE o.quantity = :quantity")
    , @NamedQuery(name = "Ordermeal.findByIscanceled", query = "SELECT o FROM Ordermeal o WHERE o.iscanceled = :iscanceled")})
public class Ordermeal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "ORDERMEALID")
    private String ordermealid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCANCELED")
    private Boolean iscanceled;
    @JoinColumn(name = "MEALID", referencedColumnName = "MEALID")
    @ManyToOne(optional = false)
    private Meal mealid;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @ManyToOne(optional = false)
    private Studentorder orderid;

    public Ordermeal() {
    }

    public Ordermeal(String ordermealid) {
        this.ordermealid = ordermealid;
    }

    public Ordermeal(String ordermealid, int quantity, Boolean iscanceled) {
        this.ordermealid = ordermealid;
        this.quantity = quantity;
        this.iscanceled = iscanceled;
    }

    public String getOrdermealid() {
        return ordermealid;
    }

    public void setOrdermealid(String ordermealid) {
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
