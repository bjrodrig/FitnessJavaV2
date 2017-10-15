/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Fooditem;
import entity.User;
import entity.Userdays;
import entity.Userprofile;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import junit.framework.TestCase;

/**
 *
 * @author barodriguez
 */
public class processDetailsTest extends TestCase {
    
    public processDetailsTest(String testName) {
        super(testName);
    }

    /**
     * Test of validateUsername method, of class processDetails.
     */
    public void testValidateUsername() throws Exception {
        System.out.println("validateUsername");
        String username = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        processDetails instance = (processDetails)container.getContext().lookup("java:global/classes/processDetails");
        List expResult = null;
        List result = instance.validateUsername("barbara");
        assertFalse(result.isEmpty());
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

  
    
}
