/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Fooditem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author barodriguez
 */
@Stateless
public class FooditemFacade extends AbstractFacade<Fooditem> {

    @PersistenceContext(unitName = "FitnessPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FooditemFacade() {
        super(Fooditem.class);
    }
    
}
