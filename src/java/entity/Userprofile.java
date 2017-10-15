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
@Table(name = "userprofile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userprofile.findAll", query = "SELECT u FROM Userprofile u")
    , @NamedQuery(name = "Userprofile.findByHeightInFt", query = "SELECT u FROM Userprofile u WHERE u.heightInFt = :heightInFt")
    , @NamedQuery(name = "Userprofile.findByHeightInIn", query = "SELECT u FROM Userprofile u WHERE u.heightInIn = :heightInIn")
    , @NamedQuery(name = "Userprofile.findByWeight", query = "SELECT u FROM Userprofile u WHERE u.weight = :weight")
    , @NamedQuery(name = "Userprofile.findByGender", query = "SELECT u FROM Userprofile u WHERE u.gender = :gender")
    , @NamedQuery(name = "Userprofile.findByWeightGoal", query = "SELECT u FROM Userprofile u WHERE u.weightGoal = :weightGoal")
    , @NamedQuery(name = "Userprofile.findByBmr", query = "SELECT u FROM Userprofile u WHERE u.bmr = :bmr")
    , @NamedQuery(name = "Userprofile.findByCalorieGoalPerDay", query = "SELECT u FROM Userprofile u WHERE u.calorieGoalPerDay = :calorieGoalPerDay")
    , @NamedQuery(name = "Userprofile.findById", query = "SELECT u FROM Userprofile u WHERE u.id = :id")
    , @NamedQuery(name = "Userprofile.findByLifestyle", query = "SELECT u FROM Userprofile u WHERE u.lifestyle = :lifestyle")
    , @NamedQuery(name = "Userprofile.findByBirthDate", query = "SELECT u FROM Userprofile u WHERE u.birthDate = :birthDate")
    , @NamedQuery(name = "Userprofile.findByUsername", query = "SELECT u FROM Userprofile u WHERE u.username = :username")
})
public class Userprofile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "height_in_ft")
    private int heightInFt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "height_in_in")
    private int heightInIn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weight")
    private Double weight;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weight_goal")
    private String weightGoal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BMR")
    private Double bmr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calorie_goal_per_day")
    private long calorieGoalPerDay;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "lifestyle")
    private String lifestyle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "change_in_pounds_per_week")
    private Float changeInPoundsPerWeek;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gain_or_lose")
    private String gainOrLose;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gain_or_loss")
    private String gainOrLoss;
    @Basic(optional = false)
    @NotNull
    @Column(name = "five_week_projection")
    private Float fiveWeekProjection;

    public Userprofile() {
    }

    public Userprofile(Short id) {
        this.id = id;
    }

    public Userprofile(Short id, int heightInFt, int heightInIn, Double weight, String gender, String weightGoal, Double bmr, long calorieGoalPerDay, String lifestyle, Date birthDate) {
        this.id = id;
        this.heightInFt = heightInFt;
        this.heightInIn = heightInIn;
        this.weight = weight;
        this.gender = gender;
        this.weightGoal = weightGoal;
        this.bmr = bmr;
        this.calorieGoalPerDay = calorieGoalPerDay;
        this.lifestyle = lifestyle;
        this.birthDate = birthDate;
    }

    public int getHeightInFt() {
        return heightInFt;
    }

    public void setHeightInFt(int heightInFt) {
        this.heightInFt = heightInFt;
    }

    public int getHeightInIn() {
        return heightInIn;
    }

    public void setHeightInIn(int heightInIn) {
        this.heightInIn = heightInIn;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(String weightGoal) {
        this.weightGoal = weightGoal;
    }

    public Double getBmr() {
        return bmr;
    }

    public void setBmr(Double bmr) {
        this.bmr = bmr;
    }

    public long getCalorieGoalPerDay() {
        return calorieGoalPerDay;
    }

    public void setCalorieGoalPerDay(long calorieGoalPerDay) {
        this.calorieGoalPerDay = calorieGoalPerDay;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }
    
    public Float getChangeInPoundsPerWeek() {
        return changeInPoundsPerWeek;
    }
    
    public void setChangeInPoundsPerWeek(Float changeInPoundsPerWeek) {
        this.changeInPoundsPerWeek = changeInPoundsPerWeek;
    }
    
    public String getGainOrLose() {
        return gainOrLose;
    }
    
    public void setGainOrLose(String gainOrLose) {
        this.gainOrLose = gainOrLose;
    }
    
    public String getGainOrLoss() {
        return gainOrLoss;
    }
    
    public void setGainOrLoss(String gainOrLoss) {
        this.gainOrLoss = gainOrLoss;
    }
    
    public Float getFiveWeekProjection() {
        return fiveWeekProjection;
    }
    
    public void setFiveWeekProjection(Float fiveWeekProjection) {
        this.fiveWeekProjection = fiveWeekProjection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userprofile)) {
            return false;
        }
        Userprofile other = (Userprofile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Userprofile[ id=" + id + " ]";
    }
    
}
