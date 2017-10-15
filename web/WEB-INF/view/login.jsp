<%-- 
    Document   : login
    Created on : Aug 31, 2017, 11:41:53 PM
    Author     : barodriguez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="css/fitness.css?version=8">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fitness Login</title>
    </head>
    <body style="text-align:left" class="body_style">
        <h1>Login</h1>
        <form id="loginForm" action="userHomepage" method="POST">
            <table>
                <tr div="errors1">${messages.username}</tr>
                <tr div="errors1">${messages.userPassNoMatch}</tr>
                <tr><td>Username: </td><td><input  type="text" name="username" 
                                                   value="${fn:escapeXml(param.username)}" required></td></tr>
                <tr><td>Password: </td><td><input type="password" name="password" required></td></tr>
                
                <tr><input type="hidden" name="priorPage" value="login"></tr>
                <tr><td><input type="submit" name="submit"></td><td></td></tr>
            </table>
        </form>
    </body>
</html>
