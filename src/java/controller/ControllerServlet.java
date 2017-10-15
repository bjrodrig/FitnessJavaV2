/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.xml.internal.ws.util.StringUtils;
// java modules that update database tables are used in ControllerServlet module
import entity.Fooditem;
import entity.User;
import entity.Userdays;
import entity.Userprofile;

import java.io.IOException;
import static java.lang.Math.abs;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.time.Clock.system;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/* module used many times for things such as updating database tables
and checking that database restrictions are met before adding item to 
database
 */
import session.processDetails;

/**
 *
 * @author barodriguez
 */
@WebServlet(name = "ControllerServlet",
        loadOnStartup = 1,
        urlPatterns = {"/signup",
            "/login",
            "/deleteFoodItem",
            "/updateUserdays",
            "/updateFoodDiary",
            "/addFoodToDiary",
            "/addFoodToDatabase",
            "/successfullyAddedFoodDatabase",
            "/displayGoals",
            "/userHomepage",
            "/updateUserSettings",
            "/settingsChanged",})
public class ControllerServlet extends HttpServlet {

    @EJB
    private processDetails processDetails;
    private static List<String> meals = Arrays.asList("Breakfast", "Lunch", "Dinner", "Snack");
    private static Boolean redirect = false;
    protected List foodNameList;
    protected String count;
    protected String pw1;
    protected String pw2;
    protected String username;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();

        HttpSession session = request.getSession();
        String userDaysDone = "";
        Userprofile userprofile = new Userprofile();
        List<Double> mealCalories = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
        String warningMessage = "For safe weight loss, the National Institutes of Health recommends no less than 1000-1200 calories for \n"
                + "    women and 1200-1500 calories for men. Completing your diary with fewer than the minimum calories \n"
                + "    noted above will not generate a news feed post for that day, or show a five-week weight projection.\n"
                + "    Even during weight loss, it's important to meet your body's basic nutrient and energy needs. Over time, \n"
                + "    not eating enough can lead to nutrient deficiencies, unpleasant side effects & other serious health problems.";
        // if signup page is requested
        if (userPath.equals("/signup")) {
            // no processing. goes straight to signup page.
        } // if login page is requested
        else if (userPath.equals("/login")) {
            // no processing. goes straight to login page.
        } else if (userPath.equals("/deleteFoodItem")) {
            // go to processDetails to execute query to delete Food Item
            Integer fooddiaryId = Integer.valueOf(request.getParameter("fooddiaryId"));
            processDetails.deleteFoodItem(fooddiaryId);
            /* userDaysDone is set to either 0 or 1 to indicate whether the user has completed their entry 
            for that day. Since both "deleteFoodItem" and "updateUserdays" go to "updateFoodDiary", the 
            updating Userdays section of the code should only be processed if userDaysDone is not set
            to an empty string. Thus, for delete, we set userDaysDone to empty string.
             */
            userDaysDone = "";
            /* After deleting item, still have to retrive all food items for indicated date 
            before displaying food diary. Thus, call "updateFoodDiary". "updateFoodDiary" is
            also called when clicking "Completed Entry" or "Make Additional Entries". Thus, 
            the similar code should be processed in one section.
             */
            userPath = "/updateFoodDiary";
        } else if (userPath.equals("/updateUserdays")) {
            /* The parameter "userDaysDone" will either be 0 or 1, depending
            on if the user selected "Completed Entry" (which is 1) or "Make Additional
            Entries" which is 0. 0 or 1 pertain to True or False for whether today's
            food diary is complete. If complete, we display 5 week projection for how 
            much someone would weigh if their calorie intake every day for the next 
            5 weeks was like today.
             */
            userDaysDone = request.getParameter("userDaysDone");
            /* See comment for "else if (userPath.equals("/deleteFoodItem")" for why
            both deleteFoodItem and updateUserDays go to updateFoodDiary.
             */
            userPath = "/updateFoodDiary";
        }

        if (userPath.equals("/updateFoodDiary")) {
            /* params are 0 for today, -1 for yesterday, 1 for tomorrow.
            The updateFoodDiary jsp has back and forward arrows to 
            represent yesterday and tomorrow. 
             */
            String date_requested = request.getParameter("param1");
            /* Need to know what day we started with in order to get correct
            dates for "yesterday" or "tomorrow". If we got to the "updateUserDays"
            page by not clicking either yesterday arrow or tomorrow arrow, then
            today is set to "real_today", which will be whatever the actual date
            the user clicked the page is.
             */
            String get_date = request.getParameter("param2");
            /* Pass username as its used to retrive items from database.
            TODO: change username to session variable and stop passing it in
            every request.
             */
            String username = request.getParameter("username");
            /* Initalizing current_date variable since it's first used in
            an if/else statement.
             */
            Date current_date = new Date();

            if (get_date.equals("real_today")) {

            } else {
                DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
                try {
                    current_date = df.parse(get_date);
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // method calculates date based on param and then converts to string
            String real_today = processDetails.getRequestedDate(current_date, date_requested);
            // query to display all food items the user ate for date requested
            List foodDiaryItems = processDetails.retrieveFoodDiaryToday(username, real_today);
            // gets the sum of each food item eaten by breakfast, lunch, etc. also gets total calories of the day
            mealCalories = processDetails.getCalorieMealSums(username, real_today, meals);
            /* Need to get proper instance of userDays. updateFoodDiary.jsp uses the userDays.done
            attribute to determine whether user has completed entries for the day.
             */
            Userdays userDays = processDetails.createOrUpdateUserDays(username, real_today);
            /* get userprofile based on username. need to pull gender attribute
            to determine minimum calories allowed in a day. If the user tries to complete their
            profile for the day without eating the minimum calories, page displays warning message.*/
            userprofile = processDetails.getUserProfile(username);
            // calculate five week projection
            Double fiveWeekProjection = 0.0;
            /* mealCalories List(4) will be null if user hasn't eaten anything.
            Item with index 4 in list is total calories for day.
             */
            if (mealCalories.get(4) != null) {
                /* BMR attribute indicates how many calories user would have to eat each day to maintain 
                their weight. Thus, (difference between actual - calories to maintain) / 3500 calories per day 
                * 7 days per week = changeInPoundsPerWeek.
                 */
                Double changeInPoundsPerWeek = (((mealCalories.get(4)) - userprofile.getBmr()) * 7.0) / 3500.0;
                fiveWeekProjection = changeInPoundsPerWeek * 5.0;
                fiveWeekProjection = userprofile.getWeight() + fiveWeekProjection;
            }
            /* get date five weeks from actual date user updated food diary and conver to string.
            TODO: should be five weeks from date of diary clicked.
             */
            String fiveWeeksFromNow = processDetails.fiveWeeksFromNow();

            // only process if statement if "completed entry" or "make additional entries" was clicked.
            if (!userDaysDone.equals("")) {
                // entity.UserDays page only takes short. not integer. 
                Short shortUserDaysDone = Integer.valueOf(userDaysDone).shortValue();
                /* updates record for day selected based on whether user is done entering meals
                for that day. Sets userDays.done to true or false.
                 */
                userDays = processDetails.updateUserDays(username, real_today, shortUserDaysDone);
            } else {
                /* retrieve userDays record. If this is the first time user selected this date
                than a new userDays record will be created. */
                userDays = processDetails.createOrUpdateUserDays(username, real_today);
            }

            session.setAttribute("today", real_today);
            session.setAttribute("foodDiaryItems", foodDiaryItems);
            session.setAttribute("meals", meals);
            session.setAttribute("username", username);
            session.setAttribute("mealCalories", mealCalories);
            session.setAttribute("userDays", userDays);
            session.setAttribute("userprofile", userprofile);
            session.setAttribute("warning", warningMessage);
            session.setAttribute("fiveWeekProjection", fiveWeekProjection);
            session.setAttribute("fiveWeeksFromNow", fiveWeeksFromNow);

            /* reset userDaysDone to empty string. Should only have a value of 0 or 1
            if the "completed entry/make additional entries" button is clicked.
             */
            userDaysDone = "";
            // don't think this is necessary? TODO: figure out if should remove.
            userPath = "/updateFoodDiary";

        } else if (userPath.equals("/addFoodToDatabase")) {
            String username = request.getParameter("username");
            session.setAttribute("username", username);

        } else if (userPath.equals("/updateUserSettings")) {
            /* this "else if" statement gets values to display in form
            to update user profile. Profile values that can be changed are
            birth day, height, gender, lifestyle, weight, and weight gain/loss
            goals. There are three separate fields for birthday: Month, date, year.
            Thus, there is some code to retrive birthDate and parse to get month, date,
            and year. We get userprofile values from database to display as initial
            values in the form. After user submits form it goes to "displayGoals".
            "displayGoals" is used for both signup and updateUserSettings since code
            to process is similar.
             */
            String username = request.getParameter("username");

            Map<String, Object> results = processDetails.processBusinessLogicUpdateUserSettings(username);
            session.setAttribute("birthMonth", results.get("birthMonth"));
            session.setAttribute("birthDay", results.get("birthDay"));
            session.setAttribute("birthYear", results.get("birthYear"));
            session.setAttribute("userprofile", results.get("userprofile"));
            session.setAttribute("months", results.get("months"));
            session.setAttribute("lifestyles", results.get("lifestyles"));
            session.setAttribute("weightGoals", results.get("weightGoals"));
            session.setAttribute("username", results.get("username"));
        }

        String url = "/WEB-INF/view" + userPath + ".jsp";

        if (redirect == true) {
            url = "/WEB-INF/view" + userPath + ".jsp";
            try {
                response.sendRedirect(url);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            // use RequestDispatcher to forward request internally
            try {
                count = "0";
                request.setAttribute("count", count);
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        List<Double> mealCalories = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);

        // if addFoodToDiary action is called
        if (userPath.equals("/addFoodToDiary")) {
            /* This if statement displays all food in database.
            If user selects an item, then this if statement still displays
            all food in database, but displays a form at the bottom for user
            to indicate what quantity of the selected food they want to add
            to their diary. Then goes to updateFoodDiary to process adding
            food to diary.
             */
 /* in updateFoodDiary page, "Add Food" buttons show up
            under all 4 meals. We want to get meal to set the initial
            selected meal in the form. (User can change, if they like).
             */
            String meal = request.getParameter("meal");
            // need this to add food to correct date of food diary.
            String today = request.getParameter("today");
            String username = request.getParameter("username");

            List foodDatabaseItems = processDetails.getFoodDatabaseItems();
            session.setAttribute("foodDatabase", foodDatabaseItems);
            String param = request.getParameter("param");
            /* when you first click "Add Food to Diary", no food is
            selected. so param=None. Once you select a food item, the 
            param will be foodId of whatever food you selected.
             */
            if (param.equals("None")) {
                param = "0";
                session.setAttribute("foodItem", param);
            } else {
                List foodItem = (List) foodDatabaseItems.get(Integer.valueOf(param));
                session.setAttribute("foodItem", foodItem);
            }
            session.setAttribute("meal", meal);
            session.setAttribute("meals", meals);
            session.setAttribute("today", today);
            session.setAttribute("username", username);

        } else if (userPath.equals("/updateFoodDiary")) {
            Float unitsConsumed = Float.valueOf(request.getParameter("unitsConsumed"));
            String meal = request.getParameter("meal");
            String foodItem = request.getParameter("foodItem");
            String today = request.getParameter("today");
            String username = request.getParameter("username");
            List foodDatabaseItems = processDetails.getFoodDatabaseItems();
            /* When retrieved from "addFoodToDiary.jsp", foodItem will 
            be a string wtih all attributes. String begins with brackets.
            Thus, replace brackets with empty string.
             */
            foodItem = foodItem.replace("[", "").replace("]", "");
            // attributes are separated by comma. Thus, split on comma.
            String[] foodInstance = foodItem.split(",");
            String foodName = foodInstance[0];
            Float caloriesPerItem = Float.valueOf(foodInstance[1]);
            Float totalCalories = unitsConsumed * caloriesPerItem;
            // create a new record in user's FoodDiary for date, meal, and food eaten
            processDetails.updateFoodDiary(username, today, meal, caloriesPerItem, foodName,
                    unitsConsumed, totalCalories);

            // pull all foodDiary items for user and date
            List foodDiaryItems = processDetails.retrieveFoodDiaryToday(username, today);
            mealCalories = processDetails.getCalorieMealSums(username, today, meals);
            Userprofile userprofile = processDetails.getUserProfile(username);
            /* Retrieve userDays instance applicable to this food diary page. Need 
            userDays.done attribute to determine whether to display information for completed entry
            or non-completed entry.
             */
            Userdays userDays = processDetails.createOrUpdateUserDays(username, today);
            Double fiveWeekProjection = 0.0;
            /* This if statement is used to get how much someone would weigh in 5 weeks if their
            calorie intake for every day over the next 5 weeks was the same as today. This information
            is displayed when someone clicks "completed entry" on food diary page.
             */
            if (mealCalories.get(4) != null) {
                /*mealCalories.get(4) is the total calories eaten that day. 0-3 are calories for breakfast, lunch,
                dinner and snack.
                 */
                Double change_in_pounds_per_week = (((mealCalories.get(4)) - userprofile.getBmr()) * 7.0) / 3500.0;
                fiveWeekProjection = change_in_pounds_per_week * 5.0;
                fiveWeekProjection = userprofile.getWeight() + fiveWeekProjection;
            }
            // get string of the date five weeks from now to display on food diary.
            String fiveWeeksFromNow = processDetails.fiveWeeksFromNow();

            session.setAttribute("foodDiaryItems", foodDiaryItems);
            session.setAttribute("today", today);
            session.setAttribute("meals", meals);
            session.setAttribute("username", username);
            session.setAttribute("mealCalories", mealCalories);
            session.setAttribute("userprofile", userprofile);
            session.setAttribute("fiveWeekProjection", fiveWeekProjection);
            session.setAttribute("fiveWeeksFromNow", fiveWeeksFromNow);
            session.setAttribute("userDays", userDays);

        } // if successfullyAddedFoodToDatabase action is called
        else if (userPath.equals("/successfullyAddedFoodDatabase")) {

            String username = request.getParameter("username");
            String foodName = request.getParameter("foodName");
            Float servingSize = Float.valueOf(request.getParameter("servingSize"));
            String servingUnit = request.getParameter("servingUnit");
            Float calories = Float.valueOf(request.getParameter("calories"));
            String count = (String) session.getAttribute("count");
            if (!count.equals("1")) {
                Map<String, Object> results = processDetails.processAddingFoodToDatabase(foodName, servingSize, servingUnit, calories, userPath);

                Fooditem foodItem = (Fooditem) results.get("foodItem");
                foodName = foodItem.getFoodName();
                request.setAttribute("foodName", foodName);

                request.setAttribute("messages", results.get("messages"));
                userPath = (String) results.get("userPath");
                System.out.println("user path is " + userPath);
            } else {
                foodName = foodName;
                System.out.println(foodName);
                request.setAttribute("username", username);
                request.setAttribute("messages", "");
                request.setAttribute("foodName", foodName);
            }
            count = "1";

        } // if displayGoals action is called
        else if (userPath.equals("/displayGoals")) {
            /* different processing for initial signup or updating user profile.
            updateOrSignUp are hidden fields in singup and update userprofile pages.
             */
            String updateOrSignUp = request.getParameter("updateOrSignUp");
            //HashMap used to display validation errors.
            if (updateOrSignUp.equals("signup")) {
                pw1 = request.getParameter("password1");
                pw2 = request.getParameter("password2");
            } else {
                pw1 = "";
                pw2 = "";
            }
            username = request.getParameter("username");
            Integer heightFt = Integer.parseInt(request.getParameter("height_ft"));
            Integer heightIn = Integer.parseInt(request.getParameter("height_in"));
            Float weight = Float.valueOf(request.getParameter("weight"));
            String birthMonth = request.getParameter("birth_month");
            Integer birthDay = Integer.parseInt(request.getParameter("birth_day"));
            Integer birthYear = Integer.parseInt(request.getParameter("birth_year"));
            String gender = request.getParameter("gender");
            String lifestyle = request.getParameter("lifestyle");
            String weightGoals = request.getParameter("weight_goals");
            // process business logic

            Map<String, Object> results = processDetails.processBusinessLogicDisplayGoals(updateOrSignUp, pw1, pw2, username,
                    heightFt, heightIn, weight, birthMonth, birthDay, birthYear, gender, lifestyle, weightGoals);

            // set session attributes
            session.setAttribute("userprofile", results.get("userprofile"));
            session.setAttribute("fiveWeeks", results.get("fiveWeeks"));
            session.setAttribute("user", results.get("user"));
            session.setAttribute("messages", results.get("messages"));
            /* slightly different display goals page depending on if came from signup or update
            user profile. If update user profile, page says "here are your updated goals"
             */
            session.setAttribute("updateOrSignUp", results.get("updateOrSignUp"));
            userPath = (String) results.get("userPath");
            // TODO: download and import inflector package to handle plurals
        } else if (userPath.equals("/settingsChanged")) {
            /* don't think this is used.
            TODO: find out if used. if not, remove.
             */

        } else if (userPath.equals("/userHomepage")) {
            // need to know if came from display goals or login
            String priorPage = request.getParameter("priorPage");
            String username = request.getParameter("username");
            if (priorPage.equals("displayGoals")) {
                session.setAttribute("username", username);
            } else if (priorPage.equals("login")) {
                // HashMap to put error messages
                Map<String, String> messages = new HashMap<String, String>();

                String password = request.getParameter("password");
                // check if username is in database.
                List query = processDetails.validateUsername(username);
                // if username is not in database, query is empty
                if (query.isEmpty()) {
                    messages.put("username", "Your username was not found in the database.");
                    session.setAttribute("messages", messages);
                    // go back to login, if username not in database
                    userPath = "/login";
                } else {
                    // check if username and password match
                    query = processDetails.validateLogin(username, password);
                    if (query.isEmpty()) {
                        messages.put("userPassNoMatch", "The username and password do not match.");
                        session.setAttribute("messages", messages);
                        userPath = "/login";
                    } else {
                        session.setAttribute("username", username);
                        userPath = "/userHomepage";
                    }
                }
            }
        }

        String url = "/WEB-INF/view" + userPath + ".jsp";

        if (redirect == true) {
            /* TODO: figure out how to make redirects to WEB-INF work. Possible?
            Come back after learning javascript. */
            url = "/WEB-INF/view" + userPath + ".jsp";
            try {
                response.sendRedirect(url);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            // use RequestDispatcher to forward request internally
            try {
                System.out.println("url is " + url);
                if (!userPath.equals("/successfullyAddedFoodDatabase")) {
                    count = "0";
                } else {
                    count = "1";
                }
                session.setAttribute("count", count);
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
