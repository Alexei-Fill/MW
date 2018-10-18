<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key = "key.movieCrew" bundle = "${lang}"/></title>
</head>
<body>
<div class="item2">
	   <ul class = "ulgenres">
			<li class = "ligenres">
				<a class = "agenres" href ="/listMovies"><fmt:message key = "key.allGenres" bundle = "${lang}"/></a>
			</li>
			<c:forEach items="${genres}" var="genre">
				<li class = "ligenres">
					<a class = "agenres" href ="/listMovieByGenre?genreId=${genre.id}">${genre.name}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
<div class="item4">
            <a href="/addHuman"><fmt:message key = "key.add" bundle = "${lang}"/></a>
</div>
                              <div class="item3">

           <c:forEach items="${humans}" var="human">
                          <div class="pageContentHum" >
                           <img src = "${human.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'" width="225" height="298" style = "float: left;"/>
                           <h1><a  href = "/human?humanId=${human.id}">${human.name} ${human.surname} ${human.patronymic}</a></h1>
                           <h4><fmt:message key = "key.birthDate" bundle = "${lang}"/>:  <custom:formatDate value = "${human.birthDate}" pattern = "${dateFormat}"/></h4>
                            <h4><fmt:message key = "key.biography" bundle = "${lang}"/></h4>
                           <p class = "textBioList">${human.biography}</p>
                          <p></p>
             <a href="/showEditHuman?humanId=${human.id}"><fmt:message key = "key.edit" bundle = "${lang}"/></a>
             <a onclick = "return confirm('<fmt:message key = "key.doYouWantReplaceHuman" bundle = "${lang}"/>');" href="/deleteHuman?humanId=${human.id}"><fmt:message key = "key.delete" bundle = "${lang}"/></a>
             </div>
            </c:forEach>
           </div>
</body>
</html>