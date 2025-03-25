<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="layout.jsp" %>

<c:set var="body" value="/WEB-INF/jsp/categories.jsp"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Categories List</title>
    <style>
        body {
            background-color: #E0D7F0;
            font-family: Arial, sans-serif;
            color: #4E2A84;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            color: #4E2A84;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #FFFFFF;
            border-radius: 8px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #D8B2F1;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #F6F1F9;
        }

        a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4E2A84;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 20px;
            text-align: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        a:hover {
            background-color: #6B3F9E;
        }
    </style>
</head>
<body>
    <h2>Categories</h2>

    <ul>
        <c:forEach var="category" items="${categories}">
            <li>
                <a href="${pageContext.request.contextPath}/categories/${category.id}">
                    ${category.name} - ${category.description}
                </a>
                <a href="#" class="delete-btn">Delete</a>
            </li>
        </c:forEach>
    </ul>
     <div class="button-container">
            <a href="${pageContext.request.contextPath}/categories/create" class="create-btn">Create Category</a>
        </div>
</body>
</html>