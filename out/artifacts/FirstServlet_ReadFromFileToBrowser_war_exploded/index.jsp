<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>My project</title>
  </head>
  <body>

  Show localization and reading from file:
  <form action="/ReadFromFileAndLocalization" method="POST">
    <input type="hidden" name="path" value="data/data.txt">
    <input type="hidden" name="local" value="en">
    <input type="submit" name="button" value="en"/>
  </form>

  <form action="/ReadFromFileAndLocalization" method="POST">
    <input type="hidden" name="path" value="data/data.txt">
    <input type="hidden" name="local" value="ru">
    <input type="submit" name="button" value="ru"/>
  </form>

  Upload image to server:
  <form action="/UploadImageServlet" method="POST" enctype="multipart/form-data">
    <input type="file" name="filecover"/> <br>
    <button type="submit" name="upload">Upload</button>
  </form>

  Parse XML file:
  <form action="${pageContext.request.contextPath}/XMLParsing" method="post">
    <input type="hidden" name="pathToXML" value="data/minerals.xml">
    <input type="hidden" name="pathToXSD" value="data/minerals.xsd">
    <button type="submit" name="button" value="dom">DOMParser</button>
    <button type="submit" name="button" value="sax">SAXParser</button>
    <button type="submit" name="button" value="stax">StAXParser</button>
  </form>

  </body>
</html>
