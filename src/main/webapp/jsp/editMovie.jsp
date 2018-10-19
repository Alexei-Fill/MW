<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${movie.name}</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="/js/uploadImage.js"></script>
</head>
<body>
  <div class="item2" style = " background-color: white;">
  			<form id = "uploadImageForm" name = "uploadImageForm" action="/uploadMovieImage" method="post" enctype="multipart/form-data" onclick = "this.target = 'hiddenframe'" >
  				<img id = "img_up" class = "imgUpload"
  				 <c:if test = "${imageURL eq null}">
  				 src = "${movie.imageURL}"
  				 </c:if>
  				  <c:if test = "${imageURL != null}">
  				  src = "${imageURL}"
  				  </c:if>
  				 onerror="this.src = '/css/img/thumb.png'" />
                  <div style = "margin-bottom:10px;"></div>
                  <input type="file" name="file" id="file"  accept="image/*" onchange="previewFile()" />
                  <input type=hidden name=MAX_FILE_SIZE value=64000>
                  <noscript>
                  <input type ="submit"  value ="<fmt:message key = "key.apply" bundle = "${lang}"/>"  />
                  </noscript>
  			</form></div>
<div class="item4" >

			</div>
	<div class="item3" >
	<c:if test = "${exception == 1001}">
        <fmt:message key = "key.incorrectData" bundle = "${lang}"/>
    </c:if>
			<form  accept-charset="UTF-8"  id = "editMovie" name="editMovie" method="post" action="editMovie" >
				<iframe id=hiddenframe name=hiddenframe src = "/jsp/image.jsp" style="width:0px; height:0px; border:0px"></iframe>
				 <input id = imageURL name = "imageURL" hidden="false" type="text" size="25"
				 <c:if test = "${imageURL == null}">
                     <c:if test = "${movie == null}">
                         value ="/css/img/thumb.png"/>
                     </c:if>
                     <c:if test = "${movie != null}">
                         <c:if test = "${movie.id != 0}">
                              value ="${movie.imageURL}"/>
                         </c:if>
                     </c:if>

				 </c:if>
				  <c:if test = "${imageURL != null}">
                 		value ="${imageURL}"/>
                 </c:if>

				<input name = "movieId" hidden="false" type="text" size="25" value ="${movie.id}"/>
				<h4><fmt:message key = "key.imdbOfMovie" bundle = "${lang}" /></h4>
				<input class = "textField" name = "imdbId" type="text" maxlength="16" value ="${movie.imdbID}" required />
				<h4><fmt:message key = "key.budgetOfMovie" bundle = "${lang}"/></h4>
				<input class = "textField" name = "budget" type="text" maxlength="25" pattern="[0-9]{0,}" value ="${movie.budget}" required />
				<h4><fmt:message key = "key.duesOfMovie" bundle = "${lang}"/></h4>
				<input class = "textField" name = "dues" type="text" maxlength="25" pattern="[0-9]{0,}" value ="${movie.dues}" required />
				<h4><fmt:message key = "key.ageLimitOfMovie" bundle = "${lang}"/></h4>
				<input class = "textField" name = "ageLimit" type="text" maxlength="2" pattern="[0-9]{0,2}" value ="${movie.ageLimit}"  required />
				<h4><fmt:message key = "key.durationOfMovie" bundle = "${lang}" /></h4>
				<input class = "textField" name = "duration" type="text" maxlength="8" placeholder= "<fmt:message key = "key.placeHoldTime" bundle = "${lang}"/>" pattern="[0-2]{0,1}[0-9]{0,1}:[0-5]{0,1}[0-9]{0,1}:[0-5]{0,1}[0-9]{0,1}" value ="${movie.duration}" required />
				<h4><fmt:message key = "key.editReleaseDateOfMovie" bundle = "${lang}"/></h4>
				<input class = "textField" name = "releaseDate"  type="text" maxlength="10" placeholder= "<fmt:message key = "key.placeHoldDate" bundle = "${lang}"/>" pattern="<fmt:message key = "key.dateFormatPattern" bundle = "${lang}"/>"  value ="<custom:formatDate value = "${movie.releaseDate}" pattern = "${dateFormat}"/>"  required />
				<h4><fmt:message key = "key.genreOfMovie" bundle = "${lang}"/></h4>
				<c:forEach items="${genres}" var="genre">
					<input type="checkbox" name="genre" value="${genre.id}"
					<c:forEach items="${movie.genres}" var="movieGenre">
						<c:if test = "${movieGenre.id eq genre.id}">
							checked="checked"
						</c:if>
					</c:forEach>
					> ${genre.name}
				</c:forEach>
				<h4><fmt:message key = "key.actorsOfMovie" bundle = "${lang}"/></h4>
				<c:forEach items="${humans}" var="human">
					<input type="checkbox" name="actor" value="${human.id}"
					<c:forEach items="${movie.movieCrew}" var="movieCrew">
						<c:if test = "${movieCrew.roleId eq actor}">
							<c:if test = "${movieCrew.id eq human.id}">
								checked="checked"
							</c:if>
						 </c:if>
					  </c:forEach>
					  > ${human.name} ${human.surname}
				</c:forEach>
				<h4><fmt:message key = "key.directorsOfMovie" bundle = "${lang}"/></h4>
				<c:forEach items="${humans}" var="human">
					<input type="checkbox" name="director" value="${human.id}"
						<c:forEach items="${movie.movieCrew}" var="movieCrew">
							<c:if test = "${movieCrew.roleId eq director}">
								<c:if test = "${movieCrew.id eq human.id}">
									checked="checked"
								</c:if>
							</c:if>
						</c:forEach>
						> ${human.name} ${human.surname}
					</c:forEach>
					<h4><fmt:message key = "key.screenwritersOfMovie" bundle = "${lang}"/></h4>
					<c:forEach items="${humans}" var="human">
						<input type="checkbox" name="screenwriter" value="${human.id}"
						<c:forEach items="${movie.movieCrew}" var="movieCrew">
							<c:if test = "${movieCrew.roleId eq screenwriter}">
								<c:if test = "${movieCrew.id eq human.id}">
									checked="checked"
								</c:if>
							</c:if>
						</c:forEach>
						> ${human.name} ${human.surname}
					</c:forEach>
					<p></p>
					<div >
						<c:forEach items="${languages}" var ="language">
						<div class = "languageBorder">
							<input name = "characteristicLanguageId" hidden="false" type="text" size="25" value ="${language.id}">
							<h3>${language.name}</h3>
							<h4><fmt:message key = "key.nameOfMovie" bundle = "${lang}"/></h4>
							<input class = "textField" name = "name${language.id}" type="text" maxlength="24" value ="${movie.name}" pattern="^[A-Za-zА-Яа-яЁё0-9_ -]{1,}$" required />
							<h4><fmt:message key = "key.countryOfMovie" bundle = "${lang}"/></h4>
							<input class = "textField" name = "country${language.id}" type="text" maxlength="24" value ="${movie.country}" pattern="^[A-Za-zА-Яа-яЁё0-9_ -]{1,}$" required />
							<h4><fmt:message key = "key.descriptionOfMovie" bundle = "${lang}"/><h4>
							<textarea class = "textArea" maxlength = "10240" rows="10" cols="45" name="description${language.id}" pattern="^[A-Za-zА-Яа-яЁё0-9_ -]{1,}$" required />${movie.description}</textarea>
							<h3> </h3>
							</div>
                        </c:forEach>
					</div>
				    <div style = "margin-bottom:10px;"></div>
					<input type ="submit" onclick="getImageURL()"  value ="<fmt:message key = "key.apply" bundle = "${lang}"/>"  />
			</form>
	</div>
</body>
</html>