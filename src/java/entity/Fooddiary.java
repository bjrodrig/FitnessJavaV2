/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author barodriguez
 */
@Entity
@Table(name = "fooddiary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fooddiary.findAll", query = "SELECT f FROM Fooddiary f")
    , @NamedQuery(name = "Fooddiary.findByDateAdded", query = "SELECT f FROM Fooddiary f WHERE f.dateAdded = :dateAdded")
    , @NamedQuery(name = "Fooddiary.findByMeal", query = "SELECT f FROM Fooddiary f WHERE f.meal = :meal")
    , @NamedQuery(name = "Fooddiary.findByCaloriesPerItem", query = "SELECT f FROM Fooddiary f WHERE f.caloriesPerItem = :caloriesPerItem")
    , @NamedQuery(name = "Fooddiary.findByQuantity", query = "SELECT f FROM Fooddiary f WHERE f.quantity = :quantity")
    , @NamedQuery(name = "Fooddiary.findByTotalCalories", query = "SELECT f FROM Fooddiary f WHERE f.totalCalories = :totalCalories")
    , @NamedQuery(name = "Fooddiary.findByFooddiaryId", query = "SELECT f FROM Fooddiary f WHERE f.fooddiaryId = :fooddiaryId")
    , @NamedQuery(name = "Fooddiary.findByUsernameAndDateAdded", query= "SELECT f FROM Fooddiary f WHERE f.fooddiaryUsername = :fooddiaryUsername"
                                                                  + " and f.dateAdded = :dateAdded")
    , @NamedQuery(name = "Fooddiary.sumByUsernameDateAddedMeal", query="SELECT SUM(f.totalCalories) FROM Fooddiary f WHERE f.fooddiaryUsername ="
                                                                       + " :fooddiaryUsername and f.dateAdded = :dateAdded"
                                                                       + " and f.meal = :meal")
    , @NamedQuery(name = "Fooddiary.sumByUsernameDateAdded", query="SELECT SUM(f.totalCalories) FROM Fooddiary f WHERE f.fooddiaryUsername ="
                                                                       + ":fooddiaryUsername and f.dateAdded = :dateAdded")
    , @NamedQuery(name = "Fooddiary.deleteFoodItem", query="DELETE FROM Fooddiary WHERE fooddiaryId = :fooddiaryId")
    })
    
public class Fooddiary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_added")
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "meal")
    private String meal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calories_per_item")
    private long caloriesPerItem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private float quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_calories")
    private float totalCalories;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fooddiary_id")
    private Short fooddiaryId;
    @JoinColumn(name = "food_item", referencedColumnName = "food_name")
    @ManyToOne(optional = false)
    private Fooditem foodItem;
    @JoinColumn(name = "fooddiary_username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User fooddiaryUsername;

    public Fooddiary() {
    }

    public Fooddiary(Short fooddiaryId) {
        this.fooddiaryId = fooddiaryId;
    }

    public Fooddiary(Short fooddiaryId, Date dateAdded, String meal, long caloriesPerItem, float quantity, float totalCalories) {
        this.fooddiaryId = fooddiaryId;
        this.dateAdded = dateAdded;
        this.meal = meal;
        this.caloriesPerItem = caloriesPerItem;
        this.quantity = quantity;
        this.totalCalories = totalCalories;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public long getCaloriesPerItem() {
        return caloriesPerItem;
    }

    public void setCaloriesPerItem(long caloriesPerItem) {
        this.caloriesPerItem = caloriesPerItem;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(float totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Short getFooddiaryId() {
        return fooddiaryId;
    }

    public void setFooddiaryId(Short fooddiaryId) {
        this.fooddiaryId = fooddiaryId;
    }

    public Fooditem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(Fooditem foodItem) {
        this.foodItem = foodItem;
    }

    public User getFooddiaryUsername() {
        return fooddiaryUsername;
    }

    public void setFooddiaryUsername(User fooddiaryUsername) {
        this.fooddiaryUsername = fooddiaryUsername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fooddiaryId != null ? fooddiaryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fooddiary)) {
            return false;
        }
        Fooddiary other = (Fooddiary) object;
        if ((this.fooddiaryId == null && other.fooddiaryId != null) || (this.fooddiaryId != null && !this.fooddiaryId.equals(other.fooddiaryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Fooddiary[ fooddiaryId=" + fooddiaryId + " ]";
    }
    
}
