/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Fooddiary;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author barodriguez
 */
@Stateless
public class FooddiaryFacade extends AbstractFacade<Fooddiary> {

    @PersistenceContext(unitName = "FitnessPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FooddiaryFacade() {
        super(Fooddiary.class);
    }
    
}
