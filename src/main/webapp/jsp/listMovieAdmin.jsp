<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key = "key.allGenres" bundle = "${lang}"/></title>
<script src="http://code.jquery.com/jquery-2.0.2.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

</head>
<body >
<div class="item2">
	   <ul class = "ulgenres">
			<li class = "ligenres">
				<a  class = "agenres" href ="/listMovies"><fmt:message key = "key.allGenres" bundle = "${lang}"/></a>
			</li>
			<c:forEach items="${genres}" var="genre">
				<li class = "ligenres">
					<a class = "agenres" href ="/listMovieByGenre?genreId=${genre.id}">${genre.name}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
        <c:if test="${hiddenMessage == 1}">
            <h1><fmt:message key = "key.notFound" bundle = "${lang}"/></h1>
         </c:if>
		   <div class="item4">
            <a href="/addMovie"><fmt:message key = "key.addMovie" bundle = "${lang}"/></a>
			 </div>
           <c:forEach items="${movies}" var="movie">
		      <div class="item3">
                          <div class = "pageContent" >
                           <img src = "${movie.imageURL}" onerror="this.src = '/css/img/thumb.png'" class = "imgList"/>
                           <h hidden="false" >${movie.id}</h>

                           <h1><a style = "margin-top: 0.3; margin-bottom: 0.3;" href = "/movie?movieId=${movie.id}">${movie.name}</a></h1>
                           <h4 ><fmt:message key = "key.imdbOfMovie" bundle = "${lang}"/>: ${movie.imdbID}
                           <fmt:message key = "key.countryOfMovie" bundle = "${lang}"/>: ${movie.country}</h4>

                           <h4 ><fmt:message key = "key.ageLimitOfMovie" bundle = "${lang}"/>: ${movie.ageLimit}</h4>
                           <p></p>
                           <h><fmt:message key = "key.genreOfMovie" bundle = "${lang}"/>: </h>
                        <c:forEach items="${movie.genres}" var="mGenre">
                                      <a href ="/listMovieByGenre?genreId=${mGenre.id}">${mGenre.name}</a>
                                        </c:forEach>
                                        <p></p>
                                         <h><fmt:message key = "key.actorsOfMovie" bundle = "${lang}"/>: </h>
                                        <c:forEach items="${movie.movieCrew}" var="human">
                                        <c:if test="${human.roleId eq actor}">
                                        <a href = "/human?humanId=${human.id}">${human.name} ${human.surname}</a>
                                        </c:if>
                                        </c:forEach>
                                         <p></p>
                                         <h><fmt:message key = "key.directorsOfMovie" bundle = "${lang}"/>: </h>
                                        <c:forEach items="${movie.movieCrew}" var="human">
                                        <c:if test="${human.roleId eq director}">
                                        <a href = "/human?humanId=${human.id}">${human.name} ${human.surname}</a>
                                        </c:if>
                                        </c:forEach>
                                        <p></p>
                                        <h><fmt:message key = "key.screenwritersOfMovie" bundle = "${lang}"/>: </h>
                                        <c:forEach items="${movie.movieCrew}" var="human">
                                        <c:if test="${human.roleId eq screenwriter}">
                                         <a href = "/human?humanId=${human.id}">${human.name} ${human.surname}</a>
                                        </c:if>
                                        </c:forEach>
                                        <p></p>
           <h4 >${movie.countOfLikes}<img height= "10px" width= "10px" src= "/css/img/like.png" />
                           <fmt:message key = "key.ratingOfMovie" bundle = "${lang}"/>: ${movie.rating}</h4>
                    <a href="/showEditMovie?movieId=${movie.id}"><fmt:message key = "key.edit" bundle = "${lang}"/></a>
                    <a onclick = "return confirm('<fmt:message key = "key.doYouWantReplaceMovie" bundle = "${lang}"/>');" href="/deleteMovie?movieId=${movie.id}"><fmt:message key = "key.delete" bundle = "${lang}"/></a>
                </div>
            </c:forEach>
           </div>
           </div>
           </div>
</body>
</html>