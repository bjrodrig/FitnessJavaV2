/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.meterware.httpunit.WebLink;
import static entity.Fooddiary_.foodItem;
import entity.Fooditem;
import entity.User;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
import session.processDetails;

public class TestClassTest {

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
        em.getTransaction().begin();
        User user = new User();
        user.setPassword("hello");
        user.setUsername("Barbara");
        user.setCreateTime(new Date());
        em.persist(user);
        Fooditem foodBanana = new Fooditem("Banana", 110, 1, "banana");
        em.persist(foodBanana);
        // MockitoAnnotations.initMocks(this);
        // controllerServlet = new ControllerServlet();
        
    }

    @After
    public void tearDown() throws Exception {

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
        // VFMemoryStorageFactory.purgeDatabase(new File("testDB").getCanonicalPath());
    }

    @Test
    public void loginPageAndTitle() throws Exception {
        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.assertTitleEquals("Fitness Login");

    }

    @Test
    public void loginFormAndAttributes() throws Exception {
        // Test jsp page has form and correct attributes
        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.assertTitleEquals("Fitness Login");
        webTester.assertFormPresent("loginForm");
        webTester.assertFormElementPresent("username");
        webTester.assertFormElementPresent("password");
        webTester.assertFormElementPresent("priorPage");
        webTester.assertFormElementEquals("priorPage", "login");
    }

    @Test
    public void loginBadUsername() throws Exception {
        // if username not in database, should return to login.jsp and display error message
        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.setWorkingForm("loginForm");
        webTester.setFormElement("username", "barry");
        webTester.setFormElement("password", "hello");
        webTester.submit();
        webTester.assertTitleEquals("Fitness Login"); //a username not in database should take you back to login page
        webTester.assertTextPresent("Your username was not found in the database."); // should say username not found
    }

    @Test
    public void goodUsernameAndPassword() throws Exception {
        // username and password in database should return user homepage

        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.setWorkingForm("loginForm");
        webTester.setFormElement("username", "Barbara");
        webTester.setFormElement("password", "hello");
        webTester.submit();
        webTester.assertTitleEquals("Fitness Home Page"); // if username and password correct, should take user to homepage
        webTester.assertTextPresent("Hello Barbara");
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Test
    public void goodUsernameBadPassword() throws Exception {
        // if username and password don't match, should get error message

        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.setWorkingForm("loginForm");
        webTester.setFormElement("username", "Barbara");
        webTester.setFormElement("password", "hello2"); // wrong password
        webTester.submit();
        webTester.assertTitleEquals("Fitness Login"); // if username correct, but password not-return to login, 
        webTester.assertTextPresent("The username and password do not match."); // display error message
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Test
    public void testUserHomepageFeatures() throws Exception {
        // make sure standard links are present
        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.setWorkingForm("loginForm");
        webTester.setFormElement("username", "Barbara");
        webTester.setFormElement("password", "hello");
        webTester.submit();
        webTester.assertTitleEquals("Fitness Home Page");
        // javaserver page should contain links to other pages, username should match username parameter from login
        webTester.assertTextPresent("addFoodToDatabase?username=Barbara");
        webTester.assertTextPresent("updateFoodDiary?param1=0&param2=real_today&username=Barbara");
        webTester.assertTextPresent("updateUserSettings?username=Barbara");

    }

    @Test
    public void clickAddFoodToDatabaseLink() throws Exception {
        // clicking "Add Food to Database" link should take you to addFoodToDatabase.jsp
        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.setWorkingForm("loginForm");
        webTester.setFormElement("username", "Barbara");
        webTester.setFormElement("password", "hello");
        webTester.submit();
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "addFoodToDatabase").click();
        webTester.assertTitleEquals("Add Food to Database");
        webTester.assertTextPresent("Barbara"); // should pass username to jsp
    }

    @Test
    public void testAddFoodToDatabaseJSPFeatures() throws Exception {
        // make sure form and appropriate form fields are present
        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        webTester.setWorkingForm("loginForm");
        webTester.setFormElement("username", "Barbara");
        webTester.setFormElement("password", "hello");
        webTester.submit();
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "addFoodToDatabase").click();
        webTester.assertTitleEquals("Add Food to Database");  // make sure title is as expected
        webTester.assertFormPresent("addFoodToDatabaseForm");
        // all form elements should be there
        webTester.assertFormElementPresent("foodName");
        webTester.assertFormElementPresent("servingSize");
        webTester.assertFormElementPresent("servingUnit");
        webTester.assertFormElementPresent("calories");
        webTester.assertFormElementEquals("username", "Barbara"); // hidden field, passes username from request parameter
        // names of form elements should be there
        webTester.assertTextPresent("Name of Food Item");
        webTester.assertTextPresent("Serving Size");
        webTester.assertTextPresent("Serving unit");
        webTester.assertTextPresent("Calories per serving size");
        
    }
    
   
    /* Hold off on testing this. Tests are submitting forms twice/refreshing the page.
    Created a workaround using session data to prevent refreshing the page from processing
    the form twice. However, test code does not recognize session attribute. Creates
    null pointer exception. Will test business logic but skip testing that servlet
    passes correct values to JSP page.
    */
    // @Test
    public void testPostSuccessfullyAddedFoodDatabaseGoodForm() throws Exception {
       // processDetails mockProcessDetails = new processDetails();
       // processDetails aSpy = Mockito.spy(mockProcessDetails);
       
        webTester = new WebTester();
        // webTester.setTestingEngineKey((TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
        webTester.getTestContext().setBaseUrl(WEBSITE_URL);
       List foodNameList = new ArrayList();
       // Mockito.doReturn(foodNameList).when(aSpy).validateFoodName("Babook");
       Fooditem foodItem = new Fooditem("Orange", 45, 1, "orange");
       // Mockito.doReturn(foodItem).when(aSpy).updateFoodItem("Babook", 1, "babook", 100);
       
       // Mockito.doReturn(results).when(aSpy).processAddingFoodToDatabase("Babook", 1, "babook", 100);
       
       
       // Mockito.when(aSpy.validateFoodName("Babook")).thenReturn(foodNameList);
       
        Map<String, Object> results = new HashMap<String, Object>();
       Map<String, String> messages = new HashMap<String, String>();
       results.put("userPath", "successfullyAddedFoodDatabase");
        results.put("foodItem", foodItem);
        results.put("messages", messages);
        // processDetails getProcessDetails = Mockito.mock(processDetails.class);
        // controllerServlet.setprocessDetails(getProcessDetails);
        // when(getProcessDetails.processAddingFoodToDatabase("Babook", 1, "babook", 4)).thenReturn(results);
        
        // when(instanceProcessDetails.processAddingFoodToDatabase("Babook", 1, "babook", 100)).thenReturn(results);
        
        webTester.beginAt("index.jsp");
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "login").click();
        // fill in login form to get to user homepage
        webTester.setWorkingForm("loginForm");
        webTester.setFormElement("username", "barbara");
        webTester.setFormElement("password", "hello");
        webTester.submit();
        // click add food to database link
        webTester.getDialog().getResponse().getFirstMatchingLink(WebLink.MATCH_URL_STRING, "addFoodToDatabase").click();
        // fill out add food to database form
        webTester.setWorkingForm("addFoodToDatabaseForm");
        webTester.setFormElement("foodName", "NewStuff");
        webTester.setFormElement("servingSize", "1");
        webTester.setFormElement("servingUnit", "shot");
        webTester.setFormElement("calories", "4");
        // webTester.setFormElement("username", "barbara");
        webTester.submit();
         /* test what happens if food name is not already in database.
        Food name shouldn't be in database. Per set up, only banana in database.
        Test ControllerServlet line [if (foodNameList.isEmpty()) {]
        If list is empty, goes to successfullyAddedFoodDatabase.jsp
        */
        // ControllerServlet controllerServlet = new ControllerServlet();
        // assertTrue(controllerServlet.foodNameList.isEmpty());
        // assertNotNull(foodItem);
        System.out.println("how many times did this run? ");
        
       webTester.assertTitleEquals("Added Food to Database");
        // jstl for "you have succesfully added XX to the database" is foodItem.foodName
        // thus, if foodItem processed correctly, food name will be orange
        // test ControllerServlet line [request.setAttribute("foodItem", foodItem);]
        // webTester.assertTextPresent("You have successfully added Babook to the database.");
        // test ControllerServlet line [String username = request.getParameter("username");
        // test ControllerServlet line [request.setAttribute("username", username);]
        // webTester.assertTextPresent("userHomepage?username=Barbara"); // string has username parameter
       // if (em.getTransaction().isActive()) {
        //    em.getTransaction().rollback();
        
        

    }

    private WebTester webTester;

}
