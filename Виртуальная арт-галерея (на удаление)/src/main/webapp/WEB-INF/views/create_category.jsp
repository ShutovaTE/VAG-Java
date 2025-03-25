<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Category</title>
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
            margin-top: 20px;
        }

        form {
            width: 50%;
            margin: 20px auto;
            background-color: #FFFFFF;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #4E2A84;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #D8B2F1;
            border-radius: 4px;
        }

        .button-container {
            text-align: center;
        }

        button {
            padding: 10px 20px;
            background-color: #4E2A84;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #6B3F9E;
        }
    </style>
</head>
<body>
    <h2>Create Category</h2>
    <form>
        <label for="name">Category Name</label>
        <input type="text" id="name" name="name" placeholder="Enter category name">

        <label for="description">Description</label>
        <textarea id="description" name="description" placeholder="Enter category description"></textarea>

        <div class="button-container">
            <button type="submit">Submit</button>
        </div>
    </form>
</body>
</html>
