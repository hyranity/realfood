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
@Table(name = "FOOD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f")
    , @NamedQuery(name = "Food.findByFoodid", query = "SELECT f FROM Food f WHERE f.foodid = :foodid")
    , @NamedQuery(name = "Food.findByFoodname", query = "SELECT f FROM Food f WHERE f.foodname = :foodname")
    , @NamedQuery(name = "Food.findByDateadded", query = "SELECT f FROM Food f WHERE f.dateadded = :dateadded")
    , @NamedQuery(name = "Food.findByIsdiscontinued", query = "SELECT f FROM Food f WHERE f.isdiscontinued = :isdiscontinued")
    , @NamedQuery(name = "Food.findByDatediscontinued", query = "SELECT f FROM Food f WHERE f.datediscontinued = :datediscontinued")
    , @NamedQuery(name = "Food.findByCalories", query = "SELECT f FROM Food f WHERE f.calories = :calories")})
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "FOODID")
    private String foodid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FOODNAME")
    private String foodname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEADDED")
    @Temporal(TemporalType.DATE)
    private Date dateadded;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDISCONTINUED")
    private Boolean isdiscontinued;
    @Column(name = "DATEDISCONTINUED")
    @Temporal(TemporalType.DATE)
    private Date datediscontinued;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALORIES")
    private int calories;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foodid")
    private List<Mealfood> mealfoodList;

    public Food() {
    }

    public Food(String foodid) {
        this.foodid = foodid;
    }

    public Food(String foodid, String foodname, Date dateadded, Boolean isdiscontinued, int calories) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.dateadded = dateadded;
        this.isdiscontinued = isdiscontinued;
        this.calories = calories;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public Boolean getIsdiscontinued() {
        return isdiscontinued;
    }

    public void setIsdiscontinued(Boolean isdiscontinued) {
        this.isdiscontinued = isdiscontinued;
    }

    public Date getDatediscontinued() {
        return datediscontinued;
    }

    public void setDatediscontinued(Date datediscontinued) {
        this.datediscontinued = datediscontinued;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @XmlTransient
    public List<Mealfood> getMealfoodList() {
        return mealfoodList;
    }

    public void setMealfoodList(List<Mealfood> mealfoodList) {
        this.mealfoodList = mealfoodList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (foodid != null ? foodid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.foodid == null && other.foodid != null) || (this.foodid != null && !this.foodid.equals(other.foodid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Food[ foodid=" + foodid + " ]";
    }
    
}
