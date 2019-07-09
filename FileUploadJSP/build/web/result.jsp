<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.io.File"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Upload Example in JSP and Servlet - Java web application</title>
    </head>
    <body> 
        <div id="result">
            <h3>${requestScope["message"]}</h3>
            <p> file location is </p><br>
            <h3>${requestScope["message1"]}</h3>
        </div>
        <form action="db" method="POST">
            <input type="text" value="${requestScope["message1"]}" name="pathname">
            <input type="submit" value="Add to db" />
        </form>
    </body>
</html>