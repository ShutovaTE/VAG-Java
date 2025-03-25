<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category Detail</title>
    <style>
        body {
            background-color: #E0D7F0; /* Нежно-фиолетовый фон */
            font-family: Arial, sans-serif;
            color: #4E2A84; /* Тёмно-фиолетовый цвет текста */
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

        .btn-like {
            background-color: #4E2A84;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
        }

        .btn-unlike {
            background-color: #C2185B;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h2>Category Details</h2>

    <table>
        <tr>
            <td><strong>Name:</strong></td>
            <td>${category.name}</td>
        </tr>
        <tr>
            <td><strong>Description:</strong></td>
            <td>${category.description}</td>
        </tr>
    </table>

    <a href="${pageContext.request.contextPath}/categories">Back to Categories</a>
</body>
</html>