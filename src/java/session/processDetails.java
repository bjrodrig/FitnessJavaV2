/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import controller.ControllerServlet;

import entity.Fooddiary;
import entity.Fooditem;
import entity.User;
import entity.Userdays;
import entity.Userprofile;
import static java.lang.Math.abs;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author barodriguez
 */
@Stateless
public class processDetails {

    @PersistenceContext(unitName = "FitnessPU")
    // @PersistenceContext(unitName = "integration")
    public EntityManager em;
    private User user;

    public processDetails() {
    }

    public processDetails(User user) {
        this.user = user;
    }

    // check if username is already in database, return result of usernames
    public List validateUsername(String username) {
        List query
                = (List) em.createNamedQuery("User.findByUsername").setParameter("username", username).getResultList();
        return query;
    }

    // check if food name user is entering is already in database. If so, they must select a different name.
    public List validateFoodName(String foodName) {
        List query
                = (List) em.createNamedQuery("Fooditem.findByFoodName").setParameter("foodName", foodName).getResultList();
        return query;

    }

    // delete fooditem based on fooddiaryId
    public void deleteFoodItem(Integer fooddiaryId) {
        int deletedCount = em.createNamedQuery("Fooddiary.deleteFoodItem").setParameter("fooddiaryId", fooddiaryId).executeUpdate();
    }

    // to check if username and password match, if not list is empty
    public List validateLogin(String username, String password) {
        List query = (List) em.createNamedQuery("User.findByUsernameAndPassword")
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        return query;

    }

    // returns all food database items
    public List getFoodDatabaseItems() {
        List query = (List) em.createNamedQuery("Fooditem.findAll").getResultList();
        List results = new ArrayList();

        /* for every food item in datbase we want to pull out the item Id,
        name, calories, servingSize, and serving Units to display on
        "add food to diary" page
         */
        for (int i = 0; i < query.size(); i++) {
            Fooditem item = (Fooditem) query.get(i);
            String foodName = item.getFoodName();
            Float calories = Float.valueOf(item.getCalories());
            Float servingSize = Float.valueOf(item.getServingSize());
            String servingUnit = item.getServingUnit();
            List foodItems = new ArrayList();
            foodItems.add(foodName);
            foodItems.add(calories);
            foodItems.add(servingSize);
            foodItems.add(servingUnit);
            results.add(foodItems);
        }
        return results;
    }

    // TODO: create tests then remove. this is never used.
    public Integer createUserProfileAutoId() {
        List query
                = (List) em.createNamedQuery("Userprofile.findAll").getResultList();
        Integer id;
        if (query.isEmpty()) {
            id = 1;
            return id;
        } else {
            id = 2;
            return id;
        }

    }

    // create new user
    public User updateUser(String username, String pw1) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(pw1);

        Date zdt = new Date();
        user.setCreateTime(zdt);
        em.persist(user);
        return user;
    }

    public User retrieveUser(String username) {
        User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
        return user;
    }

    public Userdays createOrUpdateUserDays(String username, String today) {
        Date dateToday = stringToDate(today);
        // first get User instance corresponding to username
        User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
        // if userdays does not already exist, will return empty result
        List userDaysList = checkIfUserdaysExists(user, dateToday);
        Userdays userDays;
        // if statement to create new userDays record based on parameters passed to method
        if (userDaysList.isEmpty()) {
            userDays = new Userdays();
            userDays.setUserDate(dateToday);
            Integer obj = new Integer(0);
            // Done should equal 0 (for false) or 1 (for true). Default is false.
            userDays.setDone(obj.shortValue());
            userDays.setUsername(user);
            em.persist(userDays);
        } else {
            userDays = (Userdays) userDaysList.get(0);
        }
        return userDays;
    }

    public Userdays updateUserDays(String username, String today, short userDaysDone) {
        Date dateToday = stringToDate(today);
        // first get User instance corresponding to username
        User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
        /* should always exist, because updateUserDays cannot be processed until "food diary" page is retrieved, which
        creates new userDays instance if it doesn't already exist' */
        List userDaysList = checkIfUserdaysExists(user, dateToday);
        // should only be one userDays instance that meets username and date passed to method.
        Userdays userDays = (Userdays) userDaysList.get(0);
        /* if this method was reached in Controller Servlet, it is because userDaysDone was switched
        from true to false or vice versa.
         */
        userDays.setDone(userDaysDone);
        em.persist(userDays);
        return userDays;
    }

    public List checkIfUserdaysExists(User user, Date dateToday) {
        // pull UserDays for today to check if already exists
        Userdays userDays;
        List userDaysList = (List) em.createNamedQuery("Userdays.findByUsernameAndDate")
                .setParameter("username", user).setParameter("userDate", dateToday)
                .getResultList();
        return userDaysList;

    }

    public Userprofile getUserProfile(String username) {
        // to get userprofile, need to pass in User instance that matches username
        User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username)
                .getSingleResult();
        // each profile has unique username, foreign key to User.username
        Userprofile userprofile = (Userprofile) em.createNamedQuery("Userprofile.findByUsername")
                .setParameter("username", user).getSingleResult();
        return userprofile;
    }

    public Float calculateBMR(Integer height_ft, Integer height_in, Float weight,
            String birth_month, Integer birth_day, Integer birth_year,
            String gender, String lifestyle) {
        //need to convert height to cm and weight to kg to calculate BMR
        Float height_cm = (float) ((height_ft * 12 + height_in) * 2.54);
        Float weight_kg = (float) (weight / 2.2);
        LocalDate birth_date = getBirthDate(birth_month, birth_day, birth_year);

        LocalDate today = LocalDate.now();
        // return age in seconds
        Float age = (float) Period.between(today, birth_date).getDays();
        // convert to age in years
        age = age / 365;
        // Resting BMR calculation
        Float BMR = (float) (10 * weight_kg + 6.25 * height_cm - 5 * age);
        if (gender.equals("M")) {
            BMR = BMR + 5;
        } else {
            BMR = BMR - 161;
        }
        // incorporate lifestyle into BMR calculation
        HashMap<String, Float> lifestyle_map = new HashMap<String, Float>() {
            {
                put("S", 1.2F);
                put("LA", 1.375F);
                put("A", 1.55F);
                put("VA", 1.725F);
            }
        };
        Float lifestyle_float = lifestyle_map.get(lifestyle);
        BMR = BMR * lifestyle_float;
        return BMR;
    }

    public Float calculateNetCalories(float BMR, String weight_goals, String gender) {
        /* HashMap shows how many calories you would have to cut in a week to meet
         your weight goal */
        HashMap<String, Float> weight_goals_map = new HashMap<String, Float>() {
            {
                put("L2", -1000F);
                put("L1", -500F);
                put("L.5", -250F);
                put("M", 0F);
                put("G.5", 250F);
                put("G1", 500F);
            }
        };
        /* net calorie intake a day to meet weight goals. BMR is calories to maintain 
        weight, thus, net calorie intake to meet goal is BMR + calories to cut/add a day
        to meet weight goal.
         */
        Float net_calories = BMR + weight_goals_map.get(weight_goals);
        /* for health reasons, men should not eat less than 1500 calories a day
        and women should not eat less than 1200 calories a day.
         */
        if (gender.equals("M")) {
            net_calories = java.lang.Math.max(net_calories, 1500F);
        } else {
            net_calories = java.lang.Math.max(net_calories, 1200F);
        }
        return net_calories;

    }

    /* TODO: see if this should be five weeks from user indicated date, not actual
    today's date.*/
    public String fiveWeeksFromNow() {

        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, 35);
        Date five_weeks = currentDate.getTime();
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        String five_weeks_from_now = df.format(five_weeks);
        return five_weeks_from_now;
    }

    // generic function to convert date to string, will be used frequently
    public String dateFormat(Date date) {
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        String string_date = df.format(date);
        return string_date;
    }

    public Userprofile createOrUpdateUserProfile(Integer height_ft, Integer height_in, float weight,
            String gender, float BMR, float net_calories, User user,
            String lifestyle, LocalDate birth_date, String weight_goal,
            float change_per_week, String gain_or_loss, String gain_or_lose,
            float five_week_projection) {
        Userprofile userprofile = new Userprofile();
        List userprofileList = (List) em.createNamedQuery("Userprofile.findByUsername")
                .setParameter("username", user).getResultList();
        // no userprofile for that username exists. userprofile variable should be set to new userprofile.
        if (userprofileList.isEmpty()) {

        } else {
            // if userprofile exists, should only be one profile with that username.
            userprofile = (Userprofile) userprofileList.get(0);
        }
        userprofile.setHeightInFt(height_ft);
        userprofile.setHeightInIn(height_in);
        userprofile.setWeight(Double.parseDouble(new Float(weight).toString()));
        userprofile.setGender(gender);
        userprofile.setBmr(Double.parseDouble(new Float(BMR).toString()));
        userprofile.setCalorieGoalPerDay((long) net_calories);
        userprofile.setUsername(user);
        userprofile.setLifestyle(lifestyle);
        Date birth = java.sql.Date.valueOf(birth_date);
        userprofile.setBirthDate(birth);
        userprofile.setWeightGoal(weight_goal);
        DecimalFormat df = new DecimalFormat("###.##");
        userprofile.setChangeInPoundsPerWeek((Float.valueOf(df.format(change_per_week))));
        userprofile.setGainOrLose(gain_or_lose);
        userprofile.setGainOrLoss(gain_or_loss);
        userprofile.setFiveWeekProjection(Float.valueOf(df.format(five_week_projection)));
        em.persist(userprofile);
        return userprofile;
    }

    // Takes string for birth month and integers for birth day and year and returns actual date.
    public LocalDate getBirthDate(String birth_month, Integer birth_day, Integer birth_year) {
        //will use HashMap to get value of each month. i.e. Jan=01
        HashMap<String, Integer> months = new HashMap<String, Integer>() {
            {
                put("Jan", 1);
                put("Feb", 2);
                put("Mar", 3);
                put("Apr", 4);
                put("May", 5);
                put("Jun", 6);
                put("Jul", 7);
                put("Aug", 8);
                put("Sep", 9);
                put("Oct", 10);
                put("Nov", 11);
                put("Dec", 12);
            }

        };
        Integer month = months.get(birth_month);
        LocalDate birth_date = LocalDate.of(birth_year, month, birth_day);
        return birth_date;
    }

    /* create new food item in foodItem table. users will then see this 
    food item when they click "Add Food" on their food diary.
     */
    public Fooditem updateFoodItem(String foodName, float servingSize, String servingUnit,
            float calories) {
        Fooditem foodItem = new Fooditem();
        foodItem.setFoodName(foodName);
        foodItem.setServingSize((long) servingSize);
        foodItem.setServingUnit(servingUnit);
        foodItem.setCalories((long) calories);
        em.persist(foodItem);
        return foodItem;
    }

    public Map<String, Object> processAddingFoodToDatabase(String foodName, float servingSize, String servingUnit, float calories, String userPath) {
        // messages HashMap will be used to display errors in form data.
        Map<String, String> messages = new HashMap<String, String>();
        Map<String, Object> results = new HashMap<String, Object>();
        List query = (List) em.createNamedQuery("Fooditem.findAll").getResultList();
        System.out.println("foods are " + query);
        // Food name must be unique. Checks if food name is already in database.
        List foodNameList = validateFoodName(foodName);
        // food name passed validation because no other food name in database.
        Fooditem foodItem = new Fooditem();
        System.out.println("foodName is " + foodNameList);
        if (foodNameList.isEmpty()) {
            //adds new food item to database
            foodItem = updateFoodItem(foodName, servingSize, servingUnit, calories);
            System.out.println("ran this");
            // userPath = "/successfullyAddedFoodDatabase";

        } else {
            messages.put("foodname", "That food name is taken. "
                    + "Check if food item is already in database. If not, use another name.");

            // go back to "Add a new Food Item" page
            userPath = "/addFoodToDatabase";

        }
        results.put("userPath", userPath);
        results.put("foodItem", foodItem);
        results.put("messages", messages);
        return results;
    }

    public void updateFoodDiary(String username, String today, String meal, float caloriesPerItem, String foodName,
            float unitsConsumed, float totalCalories) {
        Date dateToday = stringToDate(today);
        Fooddiary foodDiary = new Fooddiary();
        foodDiary.setDateAdded(dateToday);
        foodDiary.setMeal(meal);
        foodDiary.setCaloriesPerItem((long) caloriesPerItem);
        /* userprofile.username is foreign key which takes user table, username column. 
        Thus, first need to get the corresponding User instance. */
        User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
        foodDiary.setFooddiaryUsername(user);
        /* userprofile.foodItem is foreign key based on foodItem table. Thus obtain food item first, that matches
        foodName selected.
         */
        Fooditem foodItem = (Fooditem) em.createNamedQuery("Fooditem.findByFoodName").setParameter("foodName", foodName)
                .getSingleResult();
        foodDiary.setFoodItem(foodItem);
        foodDiary.setQuantity(unitsConsumed);
        foodDiary.setTotalCalories(totalCalories);
        em.persist(foodDiary);
    }

    public List retrieveFoodDiaryToday(String username, String today) {
        Date dateToday = stringToDate(today);
        // query to pull all foodDiaryItems based on username and date
        /* fooddiaryUsername is foreign key based on User. Thus need to get the correct user instance
         for the query */
        User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
        List foodDiaryItems = (List) em.createNamedQuery("Fooddiary.findByUsernameAndDateAdded")
                .setParameter("fooddiaryUsername", user).setParameter("dateAdded", dateToday)
                .getResultList();

        return foodDiaryItems;
    }

    public List getCalorieMealSums(String username, String today, List meals) {
        Date dateToday = stringToDate(today);
        User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
        List<Double> mealCalories = new ArrayList();
        // meals is list with 4 strings: Breakfast, Lunch, Dinner, Snacks.
        for (int i = 0; i < meals.size(); i++) {
            // e.g. sum of all calories for username: barbara, date=5/15/17, meal=breakfast
            Double caloriesOneMeal = (Double) em.createNamedQuery("Fooddiary.sumByUsernameDateAddedMeal")
                    .setParameter("fooddiaryUsername", user).setParameter("dateAdded", dateToday)
                    .setParameter("meal", meals.get(i)).getSingleResult();
            if (caloriesOneMeal == null) {
                mealCalories.add(0.0);
            } else {
                mealCalories.add(caloriesOneMeal);
            }
        }
        Double caloriesTotalMeals = (Double) em.createNamedQuery("Fooddiary.sumByUsernameDateAdded")
                .setParameter("fooddiaryUsername", user).setParameter("dateAdded", dateToday)
                .getSingleResult();
        if (caloriesTotalMeals == null) {
            mealCalories.add(0.0);
        } else {
            mealCalories.add((caloriesTotalMeals));
        }
        // will always have list of 5 items, total calories for: Breakfast, Lunch, Dinner, Snacks, and total for that day.
        return mealCalories;

    }

    public Date stringToDate(String stringDate) {
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        Date getDate = new Date();
        try {
            getDate = df.parse(stringDate);
        } catch (ParseException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getDate;
    }

    //when update food diary is called need to get today's date to display.
    public String getRequestedDate(Date diary_current_date, String string_date) {
        Integer int_date = Integer.valueOf(string_date);
        Calendar currentDate = new GregorianCalendar();
        /* diary_current_date is whatever date food diary was set to when a link on the
        page was clicked that led to the calling of this method */
        currentDate.setTime(diary_current_date);
        // int_date will be 0 for today, -1 for yesterday, 1 for tomorrow
        currentDate.add(Calendar.DATE, int_date);
        // date_date is now correct date for yesterday/today/tomorrow-based on current date
        Date date_date = currentDate.getTime();
        DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        String new_string_date = df.format(date_date);
        return new_string_date;
    }

    public Map<String, Object> processBusinessLogicUpdateUserSettings(String username) {
        // create a map of all variables to be passed to ControllerServlet
        Map<String, Object> results = new HashMap<String, Object>();
        Userprofile userprofile = getUserProfile(username);
        // TODO: see if it is good idea to reuse form from signup for change user profile
        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        List<String> lifestyles = Arrays.asList("S", "LA", "A", "VA");
        List<String> weightGoals = Arrays.asList("L2", "L1", "L.5", "M", "G.5", "G1");
        Date birthDate = userprofile.getBirthDate();
        // convert birthDate to string
        String longFormatBirthDate = dateFormat(birthDate);
        String[] values = longFormatBirthDate.split(" ");
        String birthMonth = values[0];
        String birthDay = values[1].replace(",", "");
        String birthYear = values[2];
        Integer height_ft = userprofile.getHeightInFt();
        /* Get first three letters of birth month so you can set that as "selected"
             when you go to updateUserSettings page. */
        birthMonth = birthMonth.substring(0, 3);
        results.put("birthMonth", birthMonth);
        results.put("birthDay", birthDay);
        results.put("birthYear", birthYear);
        results.put("userprofile", userprofile);
        results.put("months", months);
        results.put("lifestyles", lifestyles);
        results.put("weightGoals", weightGoals);
        results.put("username", username);
        return results;
    }

    public Map<String, Object> processBusinessLogicDisplayGoals(String updateOrSignUp, String pw1, String pw2, String username, Integer heightFt,
            Integer heightIn, Float weight, String birthMonth, Integer birthDay, Integer birthYear,
            String gender, String lifestyle, String weightGoals) {
        /* different processing for initial signup or updating user profile.
            updateOrSignUp are hidden fields in singup and update userprofile pages.
         */
        // create a map of all variables to be passed to ControllerServlet
        Map<String, Object> results = new HashMap<String, Object>();

        //HashMap used to display validation errors.
        Map<String, String> messages = new HashMap<String, String>();
        User user = new User();
        List usernamesList = new ArrayList();
        // gets query of all usernames in database that match username in method
        if (updateOrSignUp.equals("signup")) {
            usernamesList = validateUsername(username);
        }

        /* for initial sign up, should only create new user if passwords match and that username
             does not already exist in database. if not, resend user to signup page. 
            the form to update user settings always passes validation.*/
        if ((updateOrSignUp.equals("signup") && pw1.equals(pw2) && usernamesList.isEmpty())
                || updateOrSignUp.equals("update")) {
            // TODO: make password a hash
            if (updateOrSignUp.equals("signup")) {
                // method creates user record in database
                user = updateUser(username, pw1);
            } else {
                user = retrieveUser(username);
            }
            // calculates BMR based on variables passed to method
            Float BMR = calculateBMR(heightFt, heightIn, weight, birthMonth, birthDay, birthYear, gender, lifestyle);
            /* This calculates net calories you should eat in a day to meet your weight goal.
                Gender is needed because there is a minum 1500 calories for men, and 1200 for women.
             */
            Float netCalories = calculateNetCalories(BMR, weightGoals, gender);
            /* weight change = difference bewteen calories consumed and calories needed
                to maintain weight (BMR). Multiply by 7 to get variance in calories consumed and
                calories needed to maintain weight per week. Divide by 3500 to get change
                in pounds per week.
             */
            Float changeInPoundsPerWeek = ((netCalories - BMR) * 7) / 3500;
            Float fiveWeekProjection = changeInPoundsPerWeek * 5;
            /* displayGoals.jsp uses languages "if you follow this plan, you will 
                lose/gain XX pounds per week. In five weeks your weight loss/gain projection
                is XX. Therefore, we need to pass correct verbs to use.
             */
            String gainOrLoss;
            String gainOrLose;
            if (changeInPoundsPerWeek > 0) {
                gainOrLoss = "gain";
                gainOrLose = "gain";
            } else {
                gainOrLoss = "loss";
                gainOrLose = "lose";
            }
            // Don't want display goals to say you will lose -5 pounds, etc.
            changeInPoundsPerWeek = abs(changeInPoundsPerWeek);
            fiveWeekProjection = abs(fiveWeekProjection);
            /* get date five weeks from actual date user clicked food diary.
                TODO: should be five weeks from date on food diary, not actual date.
             */
            String fiveWeeksFromNow = fiveWeeksFromNow();
            LocalDate birthDate = getBirthDate(birthMonth, birthDay, birthYear);

            // add userprofile values to database
            Userprofile userprofile = createOrUpdateUserProfile(heightFt, heightIn, weight, gender,
                    BMR, netCalories, user, lifestyle, birthDate,
                    weightGoals, changeInPoundsPerWeek,
                    gainOrLoss, gainOrLose, fiveWeekProjection);
            results.put("userprofile", userprofile);
            results.put("fiveWeeks", fiveWeeksFromNow);
            results.put("user", user);
            results.put("userPath", "/displayGoals");
            // TODO: download and import inflector package to handle plurals

        } /* this section of the code creates messages to tell user if passwords 
            do not equal or if username is already taken. then sends signup form back to user
         */ else {
            if (!pw1.equals(pw2)) {
                messages.put("password1", "Passwords must be equal.");
            }
            if (!usernamesList.isEmpty()) {
                messages.put("username_duplicate", "Username already exists. Select another.");
                // TODO: consider letting user check if username is taken before submitting form
            }
            results.put("messages", messages);
            results.put("userPath", "/signup");
        }
        /* slightly different display goals page depending on if came from signup or update
            user profile. If update user profile, page says "here are your updated goals"
         */
        results.put("updateOrSignUp", updateOrSignUp);
        return results;

    }

}
