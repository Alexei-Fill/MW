<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${human.name} ${human.surname}</title>
<script src="http://code.jquery.com/jquery-2.0.2.min.js"></script>
<script src="/js/userLike.js"></script>
<script src="/js/userVote.js"></script>
</head>
<body>
<div class="item2" style = "background-color: white; "></div>
 <div class="item4"></div>

	 <div class="item3">
	 <div class = "pageContentMovie" style = "margin-left: 50px; " >
	 <div class = "imgPageDiv" >
                        <img src = "${human.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'"  class = "imgPage"/>
                        </div>
                       <h1> ${human.name} ${human.surname} ${human.patronymic}</h1>
                       <h4><custom:formatDate value = "${human.birthDate}" pattern = "${dateFormat}"/></h4>
                        <p class = "text"  style = "min-height: 510px; ">${human.biography}</p>
<div  style = "width: 100%;" >
                 <c:forEach items="${movies}" var="movie">
                                <div class = "movieLink">
                                <img src = "${movie.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'" width="200" height="280"/>
                               <p></p>
                                <a style = "width:100%; "  href = "/movie?movieId=${movie.id}">${movie.name}</a>
                                </div>
                           </c:forEach>
                           <div>
            </div>
            </div>
</body>
</html>