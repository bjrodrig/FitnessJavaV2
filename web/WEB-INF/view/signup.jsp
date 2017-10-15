<%-- 
    Document   : signup
    Created on : Aug 31, 2017, 11:41:34 PM
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
        <title>Fitness</title>
    </head>
    <body id="body_signup">
        <h1>Create a New Account</h1>
        <table>
            <form action="displayGoals" method="post">
                <!-- TODO: figure out why css isn't working -->
                <tr class="errors1">${messages.username_duplicate}</tr>
                <tr style="width: 100px"><td>Username: </td><td><input type="text" name="username" value="${fn:escapeXml(param.username)}"required></td></tr>
                <tr><td class="errors1"> ${messages.password1}</td></tr>
                <tr><td>Password: </td><td><input type="password" name="password1" required></td></tr>
                <tr><td>Retype Password: </td><td><input type="password" name="password2" required></td></tr>
                <tr><td>Height: </td><td>ft: <select name="height_ft" required>
                            <option value="1" ${param.height_ft == '1' ? 'selected' : ''}>1</option>
                            <option value="2" ${param.height_ft == '2' ? 'selected' : ''}>2</option>
                            <option value="3" ${param.height_ft == '3' ? 'selected' : ''}>3</option>
                            <option value="4" ${param.height_ft == '4' ? 'selected' : ''}>4</option>
                            <option value="5" ${param.height_ft == '5' ? 'selected' : ''}>5</option>
                            <option value="6" ${param.height_ft == '6' ? 'selected' : ''}>6</option>
                            <option value="7" ${param.height_ft == '7' ? 'selected' : ''}>7</option>
                            <option value="8" ${param.height_ft == '8' ? 'selected' : ''}>8</option>
                        </select>
                        in: <select name="height_in" required>
                            <option value="1" ${param.height_in == '0' ? 'selected' : ''}>0</option>
                            <option value="1" ${param.height_in == '1' ? 'selected' : ''}>1</option>
                            <option value="2" ${param.height_in == '2' ? 'selected' : ''}>2</option>
                            <option value="3" ${param.height_in == '3' ? 'selected' : ''}>3</option>
                            <option value="4" ${param.height_in == '4' ? 'selected' : ''}>4</option>
                            <option value="5" ${param.height_in == '5' ? 'selected' : ''}>5</option>
                            <option value="6" ${param.height_in == '6' ? 'selected' : ''}>6</option>
                            <option value="7" ${param.height_in == '7' ? 'selected' : ''}>7</option>
                            <option value="8" ${param.height_in == '8' ? 'selected' : ''}>8</option>
                            <option value="9" ${param.height_in == '9' ? 'selected' : ''}>9</option>
                            <option value="10" ${param.height_in == '10' ? 'selected' : ''}>10</option>
                            <option value="11" ${param.height_in == '11' ? 'selected' : ''}>11</option>
                        </select></td>
                <tr><td>Weight: </td><td><input type="number" name="weight" value="${fn:escapeXml(param.weight)}" required></td></tr>
                <tr><td>Birth date:</td></tr>
                <tr><td>Month:</td> <td><select name="birth_month" required>
                            <option value="Jan" ${param.birth_month == 'Jan' ? 'selected' : ''}>Jan</option>
                            <option value="Feb" ${param.birth_month == 'Feb' ? 'selected' : ''}>Feb</option>
                            <option value="Mar" ${param.birth_month == 'Mar' ? 'selected' : ''}>Mar</option>
                            <option value="Apr" ${param.birth_month == 'Apr' ? 'selected' : ''}>Apr</option>
                            <option value="May" ${param.birth_month == 'May' ? 'selected' : ''}>May</option>
                            <option value="Jun" ${param.birth_month == 'Jun' ? 'selected' : ''}>Jun</option>
                            <option value="Jul" ${param.birth_month == 'Jul' ? 'selected' : ''}>Jul</option>
                            <option value="Aug" ${param.birth_month == 'Aug' ? 'selected' : ''}>Aug</option>
                            <option value="Sep" ${param.birth_month == 'Sep' ? 'selected' : ''}>Sep</option>
                            <option value="Oct" ${param.birth_month == 'Oct' ? 'selected' : ''}>Oct</option>
                            <option value="Nov" ${param.birth_month == 'Nov' ? 'selected' : ''}>Nov</option>
                            <option value="Dec" ${param.birth_month == 'Dec' ? 'selected' : ''}>Dec</option>
                        </select></td></tr>
                <tr><td>Day: </td><td><input type="number" name="birth_day" min="1" max="31" value="${fn:escapeXml(param.birth_day)}" required></td></tr>
                <tr><td> Year: </td><td><input type="number" name="birth_year" min="1900" max="2017"  value="${fn:escapeXml(param.birth_year)}"required></td></tr>
                <tr><td>Gender: </td><td><input type="radio" name="gender" value="M" ${param.gender == 'M' ? 'checked' : ''} required>Male
                        <input type="radio" value="F" name="gender" ${param.gender == 'F' ? 'checked' : ''} required>Female</td></tr>
                <tr><td>Lifestyle: </td><td><select name="lifestyle" required>
                            <option value ="S" ${param.lifestyle == 'S' ? 'selected' : ''}>Sedentary</option>
                            <option value ="LA" ${param.lifestyle == 'LA' ? 'selected' : ''}>Lightly Active</option>
                            <option value ="A" ${param.lifestyle == 'A' ? 'selected' : ''}>Active</option>
                            <option value ="VA" ${param.lifestyle == 'VA' ? 'selected' : ''}>Very Active</option>
                        </select></td></tr>
                <tr><td>Weight Goals: </td><td><select name="weight_goals" required>
                            <option value="L2" ${param.weight_goals == 'L2' ? 'selected' : ''}>Lose 2 pounds per week</option>
                            <option value="L1"${param.weight_goals == 'L1' ? 'selected' : ''}>Lose 1 pound per week</option>
                            <option value="L.5"${param.weight_goals == 'L.5' ? 'selected' : ''}>Lose .5 pounds per week</option>
                            <option value="L0"${param.weight_goals == 'M' ? 'selected' : ''}>Lose 0 pounds per week</option>
                            <option value="G.5"${param.weight_goals == 'G.5' ? 'selected' : ''}>Gain .5 pounds per week</option>
                            <option value="G1"${param.weight_goals == 'G1' ? 'selected' : ''}>Gain 1 pound per week</option>
                        </select></td></tr>
                <tr><td><input type="hidden" name="updateOrSignUp" value="signup"></td></tr>
        </table>
        <p><input type="submit" name="submit"></form>


</body>
</htm1>
