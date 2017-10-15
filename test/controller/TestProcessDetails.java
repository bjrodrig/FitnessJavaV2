/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author barodriguez
 */
import com.meterware.httpunit.WebLink;
import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import static entity.Fooddiary_.foodItem;
import entity.Fooditem;
import entity.User;
import helpers.PersistenceHelper;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import net.sourceforge.jwebunit.WebTester;
import static org.hamcrest.CoreMatchers.is;
// import org.eclipse.persistence.jpa.osgi.Activator;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import org.junit.Before;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import session.processDetails;

public class TestProcessDetails {

    public class ProcessDetails {

        private processDetails pd;
        private EntityTransaction transaction;
        private EntityManagerFactory emFactory;

        private EntityManager em;

        @Before
        public void initializeDependencies() {
            pd = new processDetails();
            pd.em = Persistence.createEntityManagerFactory("integration").createEntityManager();
            this.transaction = pd.em.getTransaction();

            // ClassLoader cL = Activator.class.getClassLoader();
            try {
                logger.info("Starting memory database for unit tests");
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                DriverManager.getConnection("jdbc:derby:memory:testDB;create=true").close();
            } catch (Exception ex) {
                ex.printStackTrace();
                fail("Exception during database startup.");
            }

        }

        @Test
        public void testValidateUsername() {
            // final Result expectedResult = Result.BRIGHT;
            // Prediction expected = new Prediction(expectedResult, true);
            transaction.begin();
            List testResult = this.pd.validateUsername("barbara");
            transaction.commit();

            assertThat(testResult.size(), is(1));
        }

    }
}
