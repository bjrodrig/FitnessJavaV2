<%-- 
    Document   : successfully_added_food_database
    Created on : Aug 31, 2017, 11:44:47 PM
    Author     : barodriguez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Added Food to Database</title>
    </head>
    <body>
        <h1>You have successfully added ${foodName} to the database.</h1>
        <br>
        <p><a href="userHomepage?username=${username}">Go back to home page</a></p>
        <p><a href="updateFoodDiary?param1=0&param2=real_today&username=${username}">Go to Food Diary</a></p>
        <p><a href="addFoodToDatabase?username=${username}">Add another food item to the database</a></p>
        <input type="hidden" name="count" value="${sessionScope.count}">
        
        
    </body>
</html>
