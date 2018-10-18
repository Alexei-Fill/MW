<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${movie.name}</title>
<script src="http://code.jquery.com/jquery-2.0.2.min.js"></script>
<script src="/js/userLike.js"></script>
<script src="/js/userVote.js"></script>
</head>
<body>
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
	 <div class="item4"></div>

	 <div class="item3">
	 <div class = "pageContentMovie" >
	 <div class = "imgPageDiv">
<img src = "${movie.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'"  class = "imgPage"/>
<div id = "movie_id" hidden="false">${movie.id}</div>
                      <b id =  "vote_count" style = "font-size:24px;">${movie.countOfLikes}</b>
                      <input id =  "vote_status" height= "20px" width= "20px" src=
                         <c:if test="${liked == notLike}">"/css/img/notLike.png" value = "notLike" </c:if>
                        <c:if test="${liked == like}">"/css/img/like.png"   value = "like"</c:if>
                        onclick="vote()" type="image" />


                    <input id ="grade_but" name ="grade_but" type="radio" onclick="putGrade()" value="1" <c:if test="${grade eq 1}"> checked</c:if>
                        <c:if test="${authorizedUser == null}">
                                         disabled
                                         </c:if>
                                         />1
                    <input id ="grade_but" name ="grade_but" type="radio" onclick="putGrade()" value="2" <c:if test="${grade eq 2}"> checked</c:if>
                        <c:if test="${authorizedUser == null}">
                                         disabled
                                         </c:if>
                                         />2
                    <input id ="grade_but" name ="grade_but" type="radio" onclick="putGrade()" value="3" <c:if test="${grade eq 3}"> checked</c:if>
                        <c:if test="${authorizedUser == null}">
                                         disabled
                                         </c:if>
                                         />3
                    <input id ="grade_but" name ="grade_but" type="radio" onclick="putGrade()" value="4" <c:if test="${grade eq 4}"> checked</c:if>
                        <c:if test="${authorizedUser == null}">
                                         disabled
                                         </c:if>
                                         />4
                    <input  id ="grade_but" name ="grade_but" type="radio" onclick="putGrade()" value="5" <c:if test="${grade eq 5}"> checked</c:if>
                        <c:if test="${authorizedUser == null}">
                                         disabled
                                         </c:if>
                                         />5
                   <p ><b><fmt:message key = "key.ratingOfMovie" bundle = "${lang}"/>:</b> ${movie.rating}</p>

</div>
                        <h1 class = "name">${movie.name}</h1>
                        <p class= "titles"><b><fmt:message key = "key.imdbOfMovie" bundle = "${lang}"/>:</b> ${movie.imdbID}</p>
                         <p class= "titles"><b><fmt:message key = "key.countryOfMovie" bundle = "${lang}"/>:</b> ${movie.country}</p>
                         <p class= "titles"><b><fmt:message key = "key.budgetOfMovie" bundle = "${lang}"/>:</b> ${movie.budget}</p>

                        <p class ="titles"><b><fmt:message key = "key.duesOfMovie" bundle = "${lang}"/>:</b> ${movie.dues}</p>
                        <p class ="titles"><b><fmt:message key = "key.ageLimitOfMovie" bundle = "${lang}"/>:</b> ${movie.ageLimit}</p>
                         <p class= "titles"><b><fmt:message key = "key.durationOfMovie" bundle = "${lang}"/>:</b> ${movie.duration}</p>
                         <p class= "titles"><b><fmt:message key = "key.uploadDateOfMovie" bundle = "${lang}"/>: </b> <custom:formatDate value = "${movie.uploadDate}" pattern = "${dateFormat}"/></p>
                        <p class ="titles"><b><fmt:message key = "key.releaseDateOfMovie" bundle = "${lang}"/>:</b> <custom:formatDate value = "${movie.releaseDate}" pattern = "${dateFormat}"/></p>
                          <p class ="titles"><b><fmt:message key = "key.genreOfMovie" bundle = "${lang}"/>:</b>
						  <c:forEach items="${movie.genres}" var="mGenre">
                                <a href ="/listMovieByGenre?genreId=${mGenre.id}">${mGenre.name}</a>
                                </c:forEach>
                                </p>
                                 <p class= "titles"><b><fmt:message key = "key.actorsOfMovie" bundle = "${lang}"/>: </b>
                                <c:forEach items="${movie.movieCrew}" var="human">
                                <c:if test="${human.roleId eq actor}">
                               <a href = "/human?humanId=${human.id}">${human.name} ${human.surname}</a>
                                </c:if>
                                </c:forEach>
                                 </p>
                                 <p class="titles"><b><fmt:message key = "key.directorsOfMovie" bundle = "${lang}"/>: </b>
                                <c:forEach items="${movie.movieCrew}" var="human">
                                <c:if test="${human.roleId eq director}">
                                <a href = "/human?humanId=${human.id}">${human.name} ${human.surname}</a>
                                </c:if>
                                </c:forEach>
                                 </p>
                                 <p class ="titles"><b><fmt:message key = "key.screenwritersOfMovie" bundle = "${lang}"/>:  </b>
                                <c:forEach items="${movie.movieCrew}" var="human">
                                <c:if test="${human.roleId eq screenwriter}">
                               <a href = "/human?humanId=${human.id}">${human.name} ${human.surname}</a>
                                </c:if>
                                </c:forEach>
                                 </p>
                                <p><b><fmt:message key = "key.descriptionOfMovie" bundle = "${lang}"/>:</b></p>
                        <p class = "text">${movie.description}</p>

                    </div>
                    </div>
               </div>
</body>
</html>