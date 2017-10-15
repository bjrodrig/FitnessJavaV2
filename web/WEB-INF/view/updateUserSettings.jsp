<%-- 
    Document   : updateUserSettings
    Created on : Sep 21, 2017, 12:12:03 AM
    Author     : barodriguez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update User Profile Information</title>
    </head>
    <body>
        <h1>Update User Profile Information</h1>
        <table>
            <form id="updateUserSettingsForm" action="displayGoals" method="post">


                <tr><td>Height: </td><td>ft: <select name="height_ft" required>
                            <c:forEach begin="1" end="8" var="val">
                                <c:choose>
                                    <c:when test="${val == userprofile.heightInFt}">
                                        <option value="${val}" selected="selected">${val}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${val}">${val}</option>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                        </select>


                        in: <select name="height_in" required>
                            <c:forEach begin="0" end="11" var="val">
                                <c:choose>
                                    <c:when test="${val == userprofile.heightInIn}">
                                        <option value="${val}" selected="selected">${val}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${val}">${val}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                        </select></td></tr>

                <tr><td>Weight: </td><td><input type="number" name="weight" value="${userprofile.weight}" required></td></tr>
                <tr><td>Birth date:</td><td></td></tr>
                
                <tr><td>Month:</td> <td><select name="birth_month" required>
                            <option value="Jan" ${birthMonth == 'Jan' ? 'selected' : ''}>Jan</option>
                            <option value="Feb" ${birthMonth == 'Feb' ? 'selected' : ''}>Feb</option>
                            <option value="Mar" ${birthMonth == 'Mar' ? 'selected' : ''}>Mar</option>
                            <option value="Apr" ${birthMonth == 'Apr' ? 'selected' : ''}>Apr</option>
                            <option value="May" ${birthMonth == 'May' ? 'selected' : ''}>May</option>
                            <option value="Jun" ${birthMonth == 'Jun' ? 'selected' : ''}>Jun</option>
                            <option value="Jul" ${birthMonth == 'Jul' ? 'selected' : ''}>Jul</option>
                            <option value="Aug" ${birthMonth == 'Aug' ? 'selected' : ''}>Aug</option>
                            <option value="Sep" ${birthMonth == 'Sep' ? 'selected' : ''}>Sep</option>
                            <option value="Oct" ${birthMonth == 'Oct' ? 'selected' : ''}>Oct</option>
                            <option value="Nov" ${birthMonth == 'Nov' ? 'selected' : ''}>Nov</option>
                            <option value="Dec" ${birthMonth == 'Dec' ? 'selected' : ''}>Dec</option>

                        </select></td></tr> 
                <tr><td>Day: </td><td><input type="number" name="birth_day" min="1" max="31" required value="${birthDay}"></td></tr>
                <tr><td> Year: </td><td><input type="number" name="birth_year" min="1900" max="2017"  value="${birthYear}"required></td></tr>
                <tr><td>Gender: </td><td><input type="radio" name="gender" value="M" ${userprofile.gender == 'M' ? 'checked' : ''} required>Male
                        <input type="radio" value="F" name="gender" ${userprofile.gender == 'F' ? 'checked' : ''} required>Female</td></tr>
                
                <tr><td>Lifestyle: </td><td><select name="lifestyle" required>
                            <option value ="S" ${userprofile.lifestyle == 'S' ? 'selected' : ''}>Sedentary</option>
                            <option value ="LA" ${userprofile.lifestyle == 'LA' ? 'selected' : ''}>Lightly Active</option>
                            <option value ="A" ${userprofile.lifestyle == 'A' ? 'selected' : ''}>Active</option>
                            <option value ="VA" ${userprofile.lifestyle == 'VA' ? 'selected' : ''}>Very Active</option>
                        </select></td></tr>
                <tr><td>Weight Goals: </td><td><select name="weight_goals" required>
                            <option value="L2" ${userprofile.weightGoal == 'L2' ? 'selected' : ''}>Lose 2 pounds per week</option>
                            <option value="L1" ${userprofile.weightGoal == 'L1' ? 'selected' : ''}>Lose 1 pound per week</option>
                            <option value="L.5" ${userprofile.weightGoal == 'L.5' ? 'selected' : ''}>Lose .5 pounds per week</option>
                            <option value="L0" ${userprofile.weightGoal == 'M' ? 'selected' : ''}>Lose 0 pounds per week</option>
                            <option value="G.5" ${userprofile.weightGoal == 'G.5' ? 'selected' : ''}>Gain .5 pounds per week</option>
                            <option value="G1" ${userprofile.weightGoal == 'G1' ? 'selected' : ''}>Gain 1 pound per week</option>
                        </select></td></tr>
                <tr><td><input type="hidden" name="updateOrSignUp" value="update"></td></tr>
                <tr><td><input type="hidden" name="username" value="${username}"></td></tr>
        </table>
        <p><input type="submit" name="submit"></form></p>
</body>
</html>
