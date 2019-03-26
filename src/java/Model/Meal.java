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
@Table(name = "MEAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Meal.findAll", query = "SELECT m FROM Meal m")
    , @NamedQuery(name = "Meal.findByMealid", query = "SELECT m FROM Meal m WHERE m.mealid = :mealid")
    , @NamedQuery(name = "Meal.findByMealname", query = "SELECT m FROM Meal m WHERE m.mealname = :mealname")
    , @NamedQuery(name = "Meal.findByDescription", query = "SELECT m FROM Meal m WHERE m.description = :description")
    , @NamedQuery(name = "Meal.findByPrice", query = "SELECT m FROM Meal m WHERE m.price = :price")
    , @NamedQuery(name = "Meal.findByIsdiscontinued", query = "SELECT m FROM Meal m WHERE m.isdiscontinued = :isdiscontinued")
    , @NamedQuery(name = "Meal.findByDateadded", query = "SELECT m FROM Meal m WHERE m.dateadded = :dateadded")
    , @NamedQuery(name = "Meal.findByDatediscontinued", query = "SELECT m FROM Meal m WHERE m.datediscontinued = :datediscontinued")
    , @NamedQuery(name = "Meal.findByTotalcalories", query = "SELECT m FROM Meal m WHERE m.totalcalories = :totalcalories")
    , @NamedQuery(name = "Meal.findByMealimagelink", query = "SELECT m FROM Meal m WHERE m.mealimagelink = :mealimagelink")
    , @NamedQuery(name = "Meal.findByIsbreakfast", query = "SELECT m FROM Meal m WHERE m.isbreakfast = :isbreakfast")
    , @NamedQuery(name = "Meal.findByIslunch", query = "SELECT m FROM Meal m WHERE m.islunch = :islunch")})
public class Meal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "MEALID")
    private String mealid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MEALNAME")
    private String mealname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private int price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISDISCONTINUED")
    private Boolean isdiscontinued;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATEADDED")
    @Temporal(TemporalType.DATE)
    private Date dateadded;
    @Column(name = "DATEDISCONTINUED")
    @Temporal(TemporalType.DATE)
    private Date datediscontinued;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALCALORIES")
    private int totalcalories;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MEALIMAGELINK")
    private String mealimagelink;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISBREAKFAST")
    private Boolean isbreakfast;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISLUNCH")
    private Boolean islunch;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mealid")
    private Collection<Mealfood> mealfoodCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mealid")
    private Collection<Ordermeal> ordermealCollection;

    public Meal() {
    }

    public Meal(String mealid) {
        this.mealid = mealid;
    }

    public Meal(String mealid, String mealname, String description, int price, Boolean isdiscontinued, Date dateadded, int totalcalories, String mealimagelink, Boolean isbreakfast, Boolean islunch) {
        this.mealid = mealid;
        this.mealname = mealname;
        this.description = description;
        this.price = price;
        this.isdiscontinued = isdiscontinued;
        this.dateadded = dateadded;
        this.totalcalories = totalcalories;
        this.mealimagelink = mealimagelink;
        this.isbreakfast = isbreakfast;
        this.islunch = islunch;
    }

    public String getMealid() {
        return mealid;
    }

    public void setMealid(String mealid) {
        this.mealid = mealid;
    }

    public String getMealname() {
        return mealname;
    }

    public void setMealname(String mealname) {
        this.mealname = mealname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getIsdiscontinued() {
        return isdiscontinued;
    }

    public void setIsdiscontinued(Boolean isdiscontinued) {
        this.isdiscontinued = isdiscontinued;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public Date getDatediscontinued() {
        return datediscontinued;
    }

    public void setDatediscontinued(Date datediscontinued) {
        this.datediscontinued = datediscontinued;
    }

    public int getTotalcalories() {
        return totalcalories;
    }

    public void setTotalcalories(int totalcalories) {
        this.totalcalories = totalcalories;
    }

    public String getMealimagelink() {
        return mealimagelink;
    }

    public void setMealimagelink(String mealimagelink) {
        this.mealimagelink = mealimagelink;
    }

    public Boolean getIsbreakfast() {
        return isbreakfast;
    }

    public void setIsbreakfast(Boolean isbreakfast) {
        this.isbreakfast = isbreakfast;
    }

    public Boolean getIslunch() {
        return islunch;
    }

    public void setIslunch(Boolean islunch) {
        this.islunch = islunch;
    }

    @XmlTransient
    public Collection<Mealfood> getMealfoodCollection() {
        return mealfoodCollection;
    }

    public void setMealfoodCollection(Collection<Mealfood> mealfoodCollection) {
        this.mealfoodCollection = mealfoodCollection;
    }

    @XmlTransient
    public Collection<Ordermeal> getOrdermealCollection() {
        return ordermealCollection;
    }

    public void setOrdermealCollection(Collection<Ordermeal> ordermealCollection) {
        this.ordermealCollection = ordermealCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mealid != null ? mealid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Meal)) {
            return false;
        }
        Meal other = (Meal) object;
        if ((this.mealid == null && other.mealid != null) || (this.mealid != null && !this.mealid.equals(other.mealid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Meal[ mealid=" + mealid + " ]";
    }
    
}
