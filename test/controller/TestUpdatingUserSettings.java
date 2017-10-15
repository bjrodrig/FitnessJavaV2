/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.gargoylesoftware.htmlunit.javascript.host.file.File;
import com.meterware.httpunit.WebLink;
import static entity.Fooddiary_.foodItem;
import entity.Fooditem;
import entity.User;
import entity.Userprofile;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import net.sourceforge.jwebunit.WebTester;
// import org.eclipse.persistence.jpa.osgi.Activator;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import org.junit.Before;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.xml.sax.SAXException;
import session.processDetails;

 

public class TestUpdatingUserSettings {
    
    
    

    private static final String WEBSITE_URL = "http://localhost:8080/Fitness";
    private static Logger logger = Logger.getLogger(TestClassTest.class.getName());
    private Fooditem foodItem;
    // private static final processDetails instanceProcessDetails = mock(processDetails.class);
    ControllerServlet controllerServlet;

    private EntityManagerFactory emFactory;

    private EntityManager em;  

    @Before
    public void setUp() throws Exception {
        String PERSISTENCE_UNIT_NAME = "integration";

        // ClassLoader cL = Activator.class.getClassLoader();
        try {
            logger.info("Starting memory database for unit tests");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            DriverManager.getConnection("jdbc:derby:memory:testDB;create=true").close();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during database startup.");
        }
        try {
            logger.info("BuildingEntityManager for unit tests");
            emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            em = emFactory.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during JPA EntityManager instanciation.");
        } 
        webTester = new WebTester();
        // webTester.setTestingEngineKey((TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
        webTester.getTestContext().setBaseUrl(WEBSITE_URL);
        // em.getTransaction().begin();
        /* User user = new User();
        user.setPassword("hello");
        user.setUsername("Linda");
        user.setCreateTime(new Date());
        em.persist(user);
        Fooditem foodBanana = new Fooditem("Banana", 110, 1, "banana");
        em.persist(foodBanana); 
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = sdf.parse("15/02/1990");
        Userprofile userprofile = new Userprofile((short) 1, 5, 1, 110.0, "F", "L2", 1596.09, 1496, "S", birthDate);
        processDetails pd = new processDetails();
       
        userprofile.setUsername(user);
        userprofile.setChangeInPoundsPerWeek(Float.valueOf(-2));
        userprofile.setGainOrLose("lose");
        userprofile.setGainOrLoss("loss");
        userprofile.setFiveWeekProjection(Float.valueOf(110));
        em.persist(userprofile);
        // MockitoAnnotations.initMocks(this);
        // controllerServlet = new ControllerServlet(); */
        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.setWorkingForm("loginForm");
        webTester.setFormElement("username", "barbara");
        webTester.setFormElement("password", "hello");
        webTester.submit();
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "updateUserSettings").click();

    }

    // @After
   /*  public void tearDown() throws Exception {

        logger.info("Shuting down JPA layer.");
        if (em != null) {
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
        logger.info("Stopping memory database.");
        try {
            DriverManager.getConnection("jdbc:derby:memory:testDB;shutdown=true").close();
        } catch (SQLNonTransientConnectionException ex) {
            if (ex.getErrorCode() != 45000) {
                throw ex;
            }
            // Shutdown success
        }
        try {
       //  VFMemoryStorageFactory.purgeDatabase(new File("testDB").getCanonicalPath());
        } catch (Exception ex) {
            logger.info("Could not purge in-memory Derby database");
        }
    } */

    /* originally wanted to split these tests up, but can't 
    until figure out how to set to test database, not production, database.
    
    */
    @Test
    public void clickUpdateUserSettingsLink() throws Exception {
        // TODO: pulling data from production database, need to fix
        // clicking "Add Food to Database" link should take you to addFoodToDatabase.jsp
       
        webTester.assertTitleEquals("Update User Profile Information"); // confirm link goes to correct page
        // assert form includes options 1-8 for height in ft
        webTester.assertTextPresent("1");
        webTester.assertTextPresent("2");
        webTester.assertTextPresent("3");
        webTester.assertTextPresent("4");
        webTester.assertTextPresent("5");
        webTester.assertTextPresent("6");
        webTester.assertTextPresent("7");
        webTester.assertTextPresent("8");
        // assert all months included
        webTester.assertTextPresent("Jan");
        webTester.assertTextPresent("Feb");
        webTester.assertTextPresent("Mar");
        webTester.assertTextPresent("Apr");
        webTester.assertTextPresent("May");
        webTester.assertTextPresent("Jun");
        webTester.assertTextPresent("Jul");
        webTester.assertTextPresent("Aug");
        webTester.assertTextPresent("Sep");
        webTester.assertTextPresent("Oct");
        webTester.assertTextPresent("Nov");
        webTester.assertTextPresent("Dec");
        // includes 4 lifestyle options
        webTester.assertTextPresent("Sedentary");
        webTester.assertTextPresent("Lightly Active");
        webTester.assertTextPresent("Active");
        webTester.assertTextPresent("Very Active");
        // passes username as hidden value
        webTester.assertTextPresent("barbara");
        webTester.assertFormPresent("updateUserSettingsForm");
        // assert default form values are properly set
        webTester.assertFormElementEquals("height_ft", "5");
        webTester.assertFormElementEquals("height_in", "0");
        webTester.assertFormElementEquals("weight", "110.0");
        webTester.assertFormElementEquals("birth_month", "Apr");
        webTester.assertFormElementEquals("birth_day", "13");
        webTester.assertFormElementEquals("birth_year", "1991");
        webTester.assertFormElementEquals("gender", "F");
        webTester.assertFormElementEquals("lifestyle", "LA");
        webTester.assertFormElementEquals("weight_goals", "L.5");
        webTester.assertFormElementEquals("updateOrSignUp", "update");
        webTester.assertFormElementEquals("username", "barbara");
        // submit form, assert it goes to display goals
        webTester.setWorkingForm("updateUserSettingsForm");
        webTester.setFormElement("height_in", "3");
        webTester.setFormElement("weight", "105");
        webTester.setFormElement("birth_month", "Jun");
        webTester.setFormElement("lifestyle", "VA");
        webTester.submit();
        webTester.assertTitleEquals("Fitness Goals");
        // since came from update, not signup, should say your profile has been updating
        webTester.assertTextPresent("Your profile has been successfully updated.");
        webTester.assertTextPresent("Here are your updated Fitness and Nutrition Goals");
        // calculations correct and properly pulled userprofile attribute from ControllerServlet
        webTester.assertTextPresent("2021 Calories / Day"); 
        webTester.assertTextPresent("If you follow this plan, your projected weight loss is\n" +
"            0.5 pound(s) per week.");
        webTester.assertTextPresent("You should lose 2.5 pound(s) by");
    }
    
    @Test
    public void checkUserHomepageFormDisplayGoals() {
        webTester.setWorkingForm("updateUserSettingsForm");
        webTester.submit();
        webTester.assertFormPresent("displayGoalsToHomeForm");
        webTester.assertFormElementEquals("username", "barbara");
        webTester.assertFormElementEquals("priorPage", "displayGoals");
        
        
    }
    
    @Test
    public void clickGetStartedNow() {
        // assert clicking "Get Started Now" goes to home page
        webTester.setWorkingForm("updateUserSettingsForm");
        webTester.submit();
       webTester.setWorkingForm("displayGoalsToHomeForm");
       webTester.submit();
       webTester.assertTitleEquals("Fitness Home Page");
    }
    
    
    
    private WebTester webTester;
}
