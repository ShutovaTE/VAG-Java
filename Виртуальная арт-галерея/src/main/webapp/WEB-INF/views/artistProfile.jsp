<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="layout.jsp" %>

<c:set var="body" value="/WEB-INF/jsp/artistProfile.jsp"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Artist Profile</title>
    <style>
        body {
            background-color: #F9F9F9;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            width: 80%;
            margin: 20px auto;
            background-color: #FFFFFF;
            border-radius: 8px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        h1, h2 {
            color: #4E2A84;
            text-align: center;
            margin-bottom: 20px;
        }

        .profile-info {
            text-align: center;
            margin-bottom: 30px;
        }

        .profile-info p {
            font-size: 18px;
            margin: 5px 0;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        ul li {
            background-color: #F6F1F9;
            margin: 10px 0;
            padding: 12px 16px;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .section-title {
            border-bottom: 2px solid #D8B2F1;
            padding-bottom: 10px;
            margin-bottom: 20px;
            text-transform: uppercase;
            font-size: 20px;
        }

        a.back-button {
            display: block;
            margin: 20px auto;
            width: 200px;
            text-align: center;
            padding: 10px 20px;
            background-color: #4E2A84;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        a.back-button:hover {
            background-color: #6B3F9E;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Artist Profile</h1>

        <div class="profile-info">
            <p><strong>Username:</strong> ${artist.username}</p>
            <p><strong>Email:</strong> ${artist.email}</p>
        </div>

        <h2 class="section-title">Publications</h2>
        <ul>
            <c:forEach var="publication" items="${publications}">
                <li>${publication}</li>
            </c:forEach>
        </ul>

        <h2 class="section-title">Exhibitions</h2>
        <ul>
            <c:forEach var="exhibition" items="${exhibitions}">
                <li>${exhibition}</li>
            </c:forEach>
        </ul>

    </div>
</body>
</html>
