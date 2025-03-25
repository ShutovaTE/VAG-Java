<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Artwork Gallery</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body style="background-color: #e6e6ff;">

    <nav>
        <ul>
        <li><a href="${pageContext.request.contextPath}/homeContent">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/artworks">All Artworks</a></li>
            <li><a href="${pageContext.request.contextPath}/exhibitions">All Exhibitions</a></li>
            <li><a href="${pageContext.request.contextPath}/categories">All Categories</a></li>
            <li><a href="${pageContext.request.contextPath}/artistProfile">Artist Profile</a></li>
        </ul>
    </nav>

    <div class="content">
        <c:if test="${not empty body}">
            <jsp:include page="${body}" />
        </c:if>
    </div>

    <script src="scripts.js"></script>
</body>
</html>
