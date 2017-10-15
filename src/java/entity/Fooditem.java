/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author barodriguez
 */
@Entity
@Table(name = "fooditem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fooditem.findAll", query = "SELECT f FROM Fooditem f")
    , @NamedQuery(name = "Fooditem.findByFoodName", query = "SELECT f FROM Fooditem f WHERE f.foodName = :foodName")
    , @NamedQuery(name = "Fooditem.findByCalories", query = "SELECT f FROM Fooditem f WHERE f.calories = :calories")
    , @NamedQuery(name = "Fooditem.findByServingSize", query = "SELECT f FROM Fooditem f WHERE f.servingSize = :servingSize")
    , @NamedQuery(name = "Fooditem.findByServingUnit", query = "SELECT f FROM Fooditem f WHERE f.servingUnit = :servingUnit")})
public class Fooditem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "food_name")
    private String foodName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calories")
    private long calories;
    @Basic(optional = false)
    @NotNull
    @Column(name = "serving_size")
    private long servingSize;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "serving_unit")
    private String servingUnit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foodItem")
    private Collection<Fooddiary> fooddiaryCollection;

    public Fooditem() {
    }

    public Fooditem(String foodName) {
        this.foodName = foodName;
    }

    public Fooditem(String foodName, long calories, long servingSize, String servingUnit) {
        this.foodName = foodName;
        this.calories = calories;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public long getCalories() {
        return calories;
    }

    public void setCalories(long calories) {
        this.calories = calories;
    }

    public long getServingSize() {
        return servingSize;
    }

    public void setServingSize(long servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    @XmlTransient
    public Collection<Fooddiary> getFooddiaryCollection() {
        return fooddiaryCollection;
    }

    public void setFooddiaryCollection(Collection<Fooddiary> fooddiaryCollection) {
        this.fooddiaryCollection = fooddiaryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (foodName != null ? foodName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fooditem)) {
            return false;
        }
        Fooditem other = (Fooditem) object;
        if ((this.foodName == null && other.foodName != null) || (this.foodName != null && !this.foodName.equals(other.foodName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Fooditem[ foodName=" + foodName + " ]";
    }
    
}
