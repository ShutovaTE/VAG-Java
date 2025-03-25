<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exhibition Details</title>
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

        .details {
            margin: 40px auto;
            padding: 20px;
            width: 80%;
            border: 1px solid #D8B2F1;
            border-radius: 8px;
            background: #fff;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        .details h3 {
            margin: 0;
            color: #4E2A84;
        }

        .details p {
            margin: 10px 0;
            color: #666;
        }

        .publication-list {
            margin-top: 20px;
        }

        .publication-list li {
            margin: 5px 0;
        }

        .back-btn {
            display: block;
            width: 200px;
            margin: 30px auto;
            padding: 10px;
            background-color: #4E2A84;
            color: white;
            text-align: center;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .back-btn:hover {
            background-color: #6B3F9E;
        }

        .add-publication-btn {
            display: block;
            width: 200px;
            margin: 30px auto;
            padding: 10px;
            background-color: #D8B2F1;
            color: white;
            text-align: center;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .add-publication-btn:hover {
            background-color: #B597D1;
        }
    </style>
</head>
<body>
    <div class="details">
        <h2>${exhibition.title}</h2>
        <p><strong>Description:</strong> ${exhibition.description}</p>
        <p><strong>Artist ID:</strong> ${exhibition.artistId}</p>

        <h3>Publications</h3>
        <c:choose>
            <c:when test="${not empty exhibition.publications}">
                <ul class="publication-list">
                    <c:forEach var="publication" items="${exhibition.publications}">
                        <li>${publication}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p>No publications available.</p>
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/exhibitions" class="back-btn">Back to Exhibitions</a>

        <a href="${pageContext.request.contextPath}/exhibitions/${exhibition.id}/add-publication" class="add-publication-btn">Add Publication</a>
    </div>
</body>
</html>
