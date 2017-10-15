<%-- 
    Document   : settingsChanged
    Created on : Sep 21, 2017, 12:41:27 AM
    Author     : barodriguez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile Updated</title>
    </head>
    <body>
        <h1>Your user profile has been successfully changed.</h1>
        <form action="userHomepage" method="POST">
            <input type="hidden" name="username" value="${user.username}">
            <input type="hidden" name="prior_page" value="displayGoals">
            <input type="submit" name="submit" value="Back to Home Page">
        </form>
    </body>
</html>
