<%--
  Created by IntelliJ IDEA.
  User: andrewshakirov
  Date: 2019-06-11
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>

    <table border="1" cellspacing="0" cellpadding="3">
        <tr>
            <td>Date</td>
            <td>Meal</td>
            <td>Calories</td>
        </tr>
        <c:forEach var="meal" items="${mealList}" >
            <tr style="background-color:${meal.excess ? 'green' : 'red'}">
                <td>
                    <c:out value="${meal.parsedDateTime()}"/>
                </td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
