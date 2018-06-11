<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Result of reading and localization</title>
    <fmt:setLocale value="${requestScope.localValue}"/>
    <fmt:setBundle basename="property.text" var="local" scope="request"/>
    <fmt:message bundle="${local}" key="text.hello" var="text"/>
</head>
<body>
    Local text: ${text}
    <br>
    Symbol from file: ${res}
    <br>
    <a href="../index.jsp">[Come back]</a>
</body>
</html>
