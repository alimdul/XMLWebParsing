<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of minerals</title>
</head>
<body>

    <table>
        <c:forEach items="${mineralList}" var="item">
            <tr>
                <td><c:out value="${item}" /></td>
            </tr>
        </c:forEach>
    </table>

    <a href="../index.jsp">[Come back]</a>

</body>
</html>
