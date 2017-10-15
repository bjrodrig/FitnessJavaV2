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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author barodriguez
 */
@Entity
@Table(name = "userdays")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userdays.findAll", query = "SELECT u FROM Userdays u")
    , @NamedQuery(name = "Userdays.findByUserDaysId", query = "SELECT u FROM Userdays u WHERE u.userDaysId = :userDaysId")
    , @NamedQuery(name = "Userdays.findByUserDate", query = "SELECT u FROM Userdays u WHERE u.userDate = :userDate")
    , @NamedQuery(name = "Userdays.findByDone", query = "SELECT u FROM Userdays u WHERE u.done = :done")
    , @NamedQuery(name = "Userdays.findByUsernameAndDate", query = "SELECT u FROM Userdays u WHERE u.username = :username"
                                                           + " and u.userDate = :userDate")})
public class Userdays implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userDaysId")
    private Integer userDaysId;
    @Column(name = "userDate")
    @Temporal(TemporalType.DATE)
    private Date userDate;
    @Column(name = "done")
    private Short done;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private User username;

    public Userdays() {
    }

    public Userdays(Integer userDaysId) {
        this.userDaysId = userDaysId;
    }

    public Integer getUserDaysId() {
        return userDaysId;
    }

    public void setUserDaysId(Integer userDaysId) {
        this.userDaysId = userDaysId;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    public Short getDone() {
        return done;
    }

    public void setDone(Short done) {
        this.done = done;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userDaysId != null ? userDaysId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userdays)) {
            return false;
        }
        Userdays other = (Userdays) object;
        if ((this.userDaysId == null && other.userDaysId != null) || (this.userDaysId != null && !this.userDaysId.equals(other.userDaysId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Userdays[ userDaysId=" + userDaysId + " ]";
    }
    
}
