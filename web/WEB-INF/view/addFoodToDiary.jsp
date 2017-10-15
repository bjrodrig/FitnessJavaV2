<%-- 
    Document   : add_food_to_diary
    Created on : Aug 31, 2017, 11:44:15 PM
    Author     : barodriguez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Food to Diary</title>
    </head>
    <body>
        <h1>Add Food to Food Diary</h1>
        <table>
        <c:forEach var="item" items="${foodDatabase}" varStatus="loop">
            <tr>
               <c:forEach var="elem" items="${item}" >
                    <td><c:out value="${elem}" /></td>
               </c:forEach>
                    <td><form action="addFoodToDiary" method="post">
                        <input type="hidden" name="param" value="<c:out value='${loop.index}'/>">
                        <input type="hidden" name="meal" value="${meal}">
                        <input type="hidden" name="today" value="${today}">
                        <input type="hidden" name="username" value="${username}">
                        <input type="submit" value="Select">
                               
                        </form></td>
            </tr>
        </c:forEach>
           </table> 
        
        <c:choose>
            <c:when test="${foodItem=='0'}">
                
            </c:when>
            <c:otherwise>
                <form action="updateFoodDiary" method="post">
                    <b>${foodItem[0]} </b>
                    <br>
                    <input type="number" min=".01" step="0.01" name="unitsConsumed" required>  servings of ${foodItem[2]} ${foodItem[3]}(s)
                    for <select name="meal">
                        <c:forEach var="mealItem" items="${meals}">
                            <c:choose>
                            <c:when test="${mealItem == meal}">
                                <option value="${mealItem}" selected="selected">${mealItem}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${mealItem}">${mealItem}</option>
                            </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select><br>
                    <input type="hidden" name="foodItem" value="${foodItem}">
                    <input type="hidden" name="today" value="${today}">
                    <input type="hidden" name="username" value="${username}">
                    <input type="submit">
                </form>
               
                
            </c:otherwise>
        </c:choose>
        Can't find what you're looking for? <a href="addFoodToDatabase?username=${username}">Add Food to the database</a>
    </body>
</html>
