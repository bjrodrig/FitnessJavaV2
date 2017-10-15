<%-- 
    Document   : userHomepage
    Created on : Sep 9, 2017, 10:16:00 PM
    Author     : barodriguez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/fitness.css?version=8">
        <title>Fitness Home Page</title>
    </head>
    <body style="text-align:left">
        <h1>Hello ${username}</h1>
        <a id="addFoodToDatabase" href="addFoodToDatabase?username=${username}">Add Food to Database</a> <br>
        <a id="updateFoodDiary" href="updateFoodDiary?param1=0&param2=real_today&username=${username}">Go to Food Diary</a> <br>
        <a id="updateUserSettings" href="updateUserSettings?username=${username}">Update User Settings</a>
    </body>
</html>
