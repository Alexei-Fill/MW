<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="refresh" content="30;/listMovies">
<title>Error</title>
</head>
<body>
	<div   class = "pageBody" align = "center" >
<h1>Вы вернетесь на главную страницу через 30 секунд!</h1>
<h1><a href = "/listMovies">Вернуться сейчас</a></h1>
<h1>
<c:if test = "${exception == 404}">
                   ${exception} Not found
</c:if>
<c:if test = "${exception == null}">
                   ${exception} BAAAD 502 krch

</c:if>

 </h1>
	</div>
</body>
</html>