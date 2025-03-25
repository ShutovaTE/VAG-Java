<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Artwork</title>
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

        form {
            width: 50%;
            margin: 0 auto;
            background-color: #FFFFFF;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input, textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            border: 1px solid #D8B2F1;
            border-radius: 4px;
        }

        button {
            background-color: #4E2A84;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        button:hover {
            background-color: #6B3F9E;
        }
    </style>
</head>
<body>
    <h2>Create New Artwork</h2>
    <form action="#" method="post">
        <label for="title">Title</label>
        <input type="text" id="title" name="title" placeholder="Enter artwork title" disabled>

        <label for="description">Description</label>
        <textarea id="description" name="description" rows="5" placeholder="Enter artwork description" disabled></textarea>

        <label for="image">Image URL</label>
        <input type="text" id="image" name="image" placeholder="Enter image URL" disabled>

        <button type="button">Create</button>
    </form>
</body>
</html>
