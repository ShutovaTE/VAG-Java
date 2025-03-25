<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="layout.jsp" %>

<c:set var="body" value="/WEB-INF/jsp/homeContent.jsp"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Virtual Gallery - Home</title>
    <style>
        body {
            background-color: #E0D7F0;
            font-family: Arial, sans-serif;
            color: #4E2A84;
            margin: 0;
            padding: 0;
        }
        h1, h2 {
            text-align: center;
            color: #4E2A84;
            margin-top: 20px;
        }
        .section {
            width: 90%;
            margin: 20px auto;
            background-color: #FFFFFF;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #DDD;
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
            color: #4E2A84;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            color: #6B3F9E;
        }
    </style>
</head>
<body>
    <h1>Welcome to the Virtual Gallery</h1>

    <div class="section">
        <h2>Publications</h2>
        <table>
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Details</th>
            </tr>
            <tr>
                <td>Exploring Modern Art</td>
                <td>An insight into modern art movements</td>
                <td><a href="#">View Details</a></td>
            </tr>
            <tr>
                <td>Art in the 21st Century</td>
                <td>How art is evolving in the digital age</td>
                <td><a href="#">View Details</a></td>
            </tr>
        </table>
    </div>

    <div class="section">
        <h2>Exhibitions</h2>
        <table>
            <tr>
                <th>Title</th>
                <th>Date</th>
                <th>Details</th>
            </tr>
            <tr>
                <td>Spring Art Expo</td>
                <td>March 15, 2024</td>
                <td><a href="#">View Details</a></td>
            </tr>
            <tr>
                <td>Abstract Wonders</td>
                <td>April 5, 2024</td>
                <td><a href="#">View Details</a></td>
            </tr>
        </table>
    </div>

    <div class="section">
        <h2>Artist Profiles</h2>
        <table>
            <tr>
                <th>Name</th>
                <th>Profile</th>
            </tr>
            <tr>
                <td>Jane Doe</td>
                <td><a href="#">View Profile</a></td>
            </tr>
            <tr>
                <td>John Smith</td>
                <td><a href="#">View Profile</a></td>
            </tr>
        </table>
    </div>
</body>
</html>