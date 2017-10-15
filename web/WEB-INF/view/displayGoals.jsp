<%-- 
    Document   : displayGoals
    Created on : Aug 31, 2017, 11:43:05 PM
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
        <title>Fitness Goals</title>
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
            }
            td {
                text-align: left;
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${updateOrSignUp == 'update'}" > 
                    <h1>Your profile has been successfully updated.</h>
                    <h1>Here are your updated Fitness and Nutrition Goals</h1>
                    </c:when>
                    <c:otherwise>
                        <h1>Your Suggested Fitness and Nutrition Goals</h1>
                    </c:otherwise>
                    
        </c:choose>
        
        <table width="700px">
            <tr>
                <th width="35%">Nutritional Goals</th>
                <th width="65%">Target</th>
            </tr>
            <tr>
                <td>Net Calories Consumed / Day*</td>
                <td style="text-align:center;">${userprofile.calorieGoalPerDay} Calories / Day </td>
            </tr>
        </table>
        <br>
        <i style="font-size:12px">Net Calories Consumed = Total Calories Consumed - Exercise Calories burned.
            So the more you exercise, the more you can eat.</i>
        <br><br>

        <p>If you follow this plan, your projected weight ${userprofile.gainOrLoss} is
            ${userprofile.changeInPoundsPerWeek} pound(s) per week. <br>
            You should ${userprofile.gainOrLose} ${userprofile.fiveWeekProjection} pound(s) by ${fiveWeeks}.</p>
        
        <form id="displayGoalsToHomeForm" action="userHomepage" method="POST">
            <input type="hidden" name="username" value="${user.username}">
            <input type="hidden" name="priorPage" value="displayGoals">
            <input type="submit" name="submit" value="Get Started Now">
        </form>
    </body>
    
</html>
