<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="refresh" content="30;/listMovies">
<title><fmt:message key = "key.exceptionTitle" bundle = "${lang}"/></title>
</head>
<body>
<div class="item2" style = "background-color: white; "></div>
 <div class="item4"></div>

	 <div class="item3" >
         <div class = "pageContentMovie" style = "margin-left: 50px; margin-top: 20px; ">

<h1 style="color:red;">
<c:if test = "${exception == 404}">
   <fmt:message key = "key.notFoundExc" bundle = "${lang}"/>
</c:if>
<c:if test = "${exception == 1003}">
 <fmt:message key = "key.youHaveLinks" bundle = "${lang}"/>
</c:if>
<c:if test = "${exception == null}">
     <fmt:message key = "key.exception" bundle = "${lang}"/>
</c:if>
 </h1>
 <h1><fmt:message key = "key.returnMsg" bundle = "${lang}"/></h1>
 <h1><a href = "/listMovies"><fmt:message key = "key.returnNow" bundle = "${lang}"/></a></h1>
 <img src = "/css/img/error.jpg"/>
	</div>
</body>
</html>