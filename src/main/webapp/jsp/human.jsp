<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${movie.name}" /></title>
<script src="http://code.jquery.com/jquery-2.0.2.min.js"></script>
<script src="/js/userLike.js"></script>
<script src="/js/userVote.js"></script>
</head>
<body>
<div class="item2" style = "background-color: white;"></div>
 <div class="item4"></div>

	 <div class="item3">
	 <div class = "pageContentMovie" >
	 <div class = "imgPageDiv">
                        <img src = "${human.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'"  class = "imgPage"/>
                        </div>
                       <h1> ${human.name}</h1>
                       <h4><custom:formatDate value = "${human.birthDate}" pattern = "${dateFormat}"/></h4>
                        <p class = "text">${human.biography}</p>

                 <c:forEach items="${movies}" var="movie">
                                <div>
                                <img src = "${movie.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'" width="70" height="98" style = "float: left;"/>
                                <h3><a style = "margin-top: 0.3; margin-bottom: 0.3;" href = "/movie?movieId=${movie.id}">${movie.name}</a></h3>
                                </div>
                           </c:forEach>
            </div>
            </div>
</body>
</html>