<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="layout.jsp" %>

<c:set var="body" value="/WEB-INF/jsp/exhibitions.jsp"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exhibitions</title>
    <style>
        body {
            background-color: #E8F4F8;
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            color: #4E2A84;
            margin-top: 20px;
        }

        .exhibition {
            margin: 20px auto;
            padding: 15px;
            width: 80%;
            border: 1px solid #D8B2F1;
            border-radius: 8px;
            background: #fff;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        .exhibition h3 {
            margin: 0;
            color: #4E2A84;
        }

        .exhibition p {
            margin: 10px 0;
            color: #666;
        }

        .view-link {
            text-decoration: none;
            color: #4E2A84;
            font-weight: bold;
        }

        .view-link:hover {
            color: #6B3F9E;
        }

        .create-btn {
            display: block;
            width: 200px;
            margin: 40px auto;
            padding: 10px;
            background-color: #4E2A84;
            color: white;
            text-align: center;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .create-btn:hover {
            background-color: #6B3F9E;
        }
    </style>
</head>
<body>
    <h2>All Exhibitions</h2>

    <c:forEach var="exhibition" items="${exhibitions}">
        <div class="exhibition">
            <h3>${exhibition.title}</h3>
            <p>${exhibition.description}</p>
            <a class="view-link" href="${pageContext.request.contextPath}/exhibitions/${exhibition.id}">View Details</a>
        </div>
    </c:forEach>

    <a href="${pageContext.request.contextPath}/exhibitions/create" class="create-btn">Create New Exhibition</a>
</body>
</html>
