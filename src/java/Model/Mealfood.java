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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mast3
 */
@Entity
@Table(name = "MEALFOOD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mealfood.findAll", query = "SELECT m FROM Mealfood m")
    , @NamedQuery(name = "Mealfood.findByMealfoodid", query = "SELECT m FROM Mealfood m WHERE m.mealfoodid = :mealfoodid")
    , @NamedQuery(name = "Mealfood.findByQuantity", query = "SELECT m FROM Mealfood m WHERE m.quantity = :quantity")
    , @NamedQuery(name = "Mealfood.findByIsdiscontinued", query = "SELECT m FROM Mealfood m WHERE m.isdiscontinued = :isdiscontinued")})
public class Mealfood implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MEALFOODID")
    private Integer mealfoodid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDISCONTINUED")
    private Boolean isdiscontinued;
    @JoinColumn(name = "FOODID", referencedColumnName = "FOODID")
    @ManyToOne(optional = false)
    private Food foodid;
    @JoinColumn(name = "MEALID", referencedColumnName = "MEALID")
    @ManyToOne(optional = false)
    private Meal mealid;

    public Mealfood() {
    }

    public Mealfood(Integer mealfoodid) {
        this.mealfoodid = mealfoodid;
    }

    public Mealfood(Integer mealfoodid, int quantity, Boolean isdiscontinued) {
        this.mealfoodid = mealfoodid;
        this.quantity = quantity;
        this.isdiscontinued = isdiscontinued;
    }

    public Integer getMealfoodid() {
        return mealfoodid;
    }

    public void setMealfoodid(Integer mealfoodid) {
        this.mealfoodid = mealfoodid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsdiscontinued() {
        return isdiscontinued;
    }

    public void setIsdiscontinued(Boolean isdiscontinued) {
        this.isdiscontinued = isdiscontinued;
    }

    public Food getFoodid() {
        return foodid;
    }

    public void setFoodid(Food foodid) {
        this.foodid = foodid;
    }

    public Meal getMealid() {
        return mealid;
    }

    public void setMealid(Meal mealid) {
        this.mealid = mealid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mealfoodid != null ? mealfoodid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mealfood)) {
            return false;
        }
        Mealfood other = (Mealfood) object;
        if ((this.mealfoodid == null && other.mealfoodid != null) || (this.mealfoodid != null && !this.mealfoodid.equals(other.mealfoodid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Mealfood[ mealfoodid=" + mealfoodid + " ]";
    }
    
}
