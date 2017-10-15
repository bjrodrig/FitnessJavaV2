<%-- 
    Document   : food_diary
    Created on : Aug 31, 2017, 11:43:24 PM
    Author     : barodriguez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Food Diary</title>
        <style>
            .button {
                font: 10px;
                font-family: "Times New Roman", Times, serif;
                text-decoration: none;
                background-color: #EEEEEE;
                color: black;
                padding: 2px 6px 2px 6px;
                border-top: 1px solid #CCCCCC;
                border-right: 1px solid #CCCCCC;
                border-bottom: 1px solid #CCCCCC;
                border-left: 1px solid #CCCCCC;
            }
        </style>
    </head>
    <body>
        <h1>Your Food Diary For: 
            <a href="updateFoodDiary?param1=-1&param2=${today}&username=${username}">&#8678;</a> 
            ${today} 
            <a href="updateFoodDiary?param1=1&param2=${today}&username=${username}">&#8680;</a></h1>
        <table>
            <th></th>
            <th></th>
            <th>Calories</th>
            <tr>
                <td></td>
                <td></td>
                <td style="text-align:center;"><i>kcal</i></td>
            </tr>

            <c:forEach var="item" items="${meals}" varStatus="loop">
                <tr><td>
                        <b><c:out value="${item}"/></b></td></tr>

                <c:forEach var="elem" items="${foodDiaryItems}">
                    <c:if test = "${elem.meal == item}" >
                        <tr><td>${elem.foodItem.foodName}</td><td>${elem.quantity} ${elem.foodItem.servingUnit}(s)</td><td style="text-align:center;">${elem.totalCalories}</td>
                            <td><a href="deleteFoodItem?param1=0&param2=${today}&username=${username}&fooddiaryId=${elem.fooddiaryId}">Delete</a></td></tr>
                        </c:if>
                    </c:forEach>



                <tr><td> <form action="addFoodToDiary" method="post">
                            <input type="submit" value="Add Food">
                            <input type="hidden" name="meal" value="<c:out value='${item}'/>" >
                            <input type="hidden" name="param" value="None">
                            <input type="hidden" name="meals" value="${meals}">
                            <input type="hidden" name="today" value="${today}">
                            <input type="hidden" name="username" value="${username}">
                        </form> </td><td></td><td style="text-align: center;"><b>${mealCalories[loop.index]}</b></td></tr>
                <tr><td></td>   </tr>
                <tr><td></td>   </tr>
                <tr><td></td>   </tr>
            </c:forEach>
            <tr><td><b>Total Calories:</td></td><td></td><td style="text-align: center;"><b>${mealCalories[4]}</b></td></tr> 
            <tr><td><b>Your Daily Goal:</td></td><td></td><td>${userprofile.calorieGoalPerDay}</td></tr>
<tr><td><b>Remaining:</b> </td></td><td></td></td><td>${userprofile.calorieGoalPerDay - mealCalories[4]}</td></tr>

             
        </table>

        <c:choose>
            <c:when test="${userDays.done != 1}">
                <a class="button" href="updateUserdays?param1=0&param2=${today}&username=${username}&userDaysDone=1">Complete This Entry</a>

            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${userprofile.gender == 'M'}">
                        <c:choose>
                            <c:when test="${(mealCalories[4] < 1200) || empty mealCalories[4]}">
                                Based on your total calories consumed for today, you are likely not eating enough.
                                
                                ${warning}

                            </c:when>
                            <c:otherwise>
                                If every day were like today, you would weight ${fiveWeekProjection} by ${fiveWeeksFromNow}.
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${(mealCalories[4] < 1000) || empty mealCalories[4]}">
                                Based on your total calories consumed for today, you are likely not eating enough.
                                
                                ${warning}

                            </c:when>
                            <c:otherwise>
                                If every day were like today, you would weight ${fiveWeekProjection} lbs by ${fiveWeeksFromNow}.
                                
                            </c:otherwise>
                        </c:choose>

                    </c:otherwise>

                </c:choose>
                <br>
                <a class="button" href="updateUserdays?param1=0&param2=${today}&username=${username}&userDaysDone=0">Make Additional Entries</a>
            </c:otherwise>

        </c:choose>

    </body>
</html>
