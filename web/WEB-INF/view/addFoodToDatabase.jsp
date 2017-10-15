<%-- 
    Document   : add_food_to_database
    Created on : Aug 31, 2017, 11:44:30 PM
    Author     : barodriguez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Food to Database</title>
    </head>
    <body>
        <h1>Add Food to Database</h1>
        <form id="addFoodToDatabaseForm" action="successfullyAddedFoodDatabase" method="POST">
            <table>
                <tr class="errors1">${messages.foodname}</tr>
                <tr><td>Name of Food Item </td><td><input type="text" name="foodName" required value="${fn:escapeXml(param.foodName)}"></td></tr>
                <tr><td>Serving Size      </td><td><input type="number" step="0.01" min=".01" name="servingSize" value="${fn:escapeXml(param.servingSize)}"</td></tr>
                <tr><td>Serving unit</td><td><input type="text" name="servingUnit" value="${fn:escapeXml(param.servingUnit)}" required></td><td><i>i.e. grams, ounces, etc.</i></td></tr>
                <tr><td>Calories per serving size</td><td><input type="number" min=".01" step=".01" name="calories" value="${fn:escapeXml(param.calories)}" required></td></tr>
                <tr><td><input type="hidden" name="username" value="${username}">
                <tr><td>       <input type="hidden" name="count" value="${sessionScope.count}">
                <tr><td><input type="submit" name="submit"></td></tr>
            </table>
               
        </form>
    </body>
</html>
