/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Userprofile;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author barodriguez
 */
@Stateless
public class UserprofileFacade extends AbstractFacade<Userprofile> {

    @PersistenceContext(unitName = "FitnessPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserprofileFacade() {
        super(Userprofile.class);
    }
    
}
