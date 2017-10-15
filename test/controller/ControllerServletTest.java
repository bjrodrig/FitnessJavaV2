/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.meterware.httpunit.*;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.*;
import javax.servlet.http.HttpServletResponse;
import static javax.ws.rs.client.Entity.form;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author barodriguez
 */

    
    public class ControllerServletTest {
    
    
    

    /**
     * Test of doGet method, of class ControllerServlet.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoginLinkOnIndexPage() throws Exception {
        try {
            WebConversation conversation = new WebConversation();
            
            WebResponse response = conversation.getResponse("http://localhost:8080/Fitness/");
            System.out.println( response );
            WebLink link = response.getLinkWith( "login");
            link.click();
            WebResponse loginPage = conversation.getCurrentPage();
            assertEquals("Login String: ", link.getURLString(), "login");
            // Test that clicking login link goes to the correct page (only page where title is Fitness Login)
            assertEquals("Login page title: ", loginPage.getTitle(), "Fitness Login");
            
            
        } catch (Exception e) {
            System.err.println( "Exception: " + e);
        }
    }
    
    @Test
    public void testLoginForm() throws Exception {
        try {
            WebConversation wc = new WebConversation();
            WebResponse response = wc.getResponse("http://localhost:8080/Fitness/");
            response.getLinkWith("login").click();
            WebResponse loginPage = wc.getCurrentPage();
            WebForm form = loginPage.getFormWithID("loginForm");
            assertNotNull("No form found with ID 'loginForm'", form);
            assertEquals("Form method", "POST", form.getMethod());
            assertEquals("Form action", "user_homepage", form.getAction() );
            assertEquals("Form hidden value prior_page: ", "login", form.getParameterValue("prior_page"));
            
            
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
       
    }
    
    
    }

    
    
    
