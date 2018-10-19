<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/roof.css"  rel='stylesheet' type='text/css'/>
<link href="/css/page.css"  rel='stylesheet' type='text/css'/>
<link href="/css/genres.css"  rel='stylesheet' type='text/css'/>
<link href="/css/grade.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
</head>
<body>
<div class="grid-container" align="center">
 		<div class="item1" >
		<fmt:setLocale value="${local}"/>
		<fmt:setBundle basename = "local" var = "lang"/>
		<ul  class = "ulroof" align = "center">
		<li class = "liroof listLiHover">
        			    <a class = "aroof" href="#"><fmt:message key = "key.lang" bundle = "${lang}"/></a>
        			    <div></div>
        			    <ul class="ulList" >
        			    <c:forEach items="${siteLanguages}" var="siteLanguage">
                        		           			        <li class = "ulListLi">
                        		    <a class = "aroof" href = "/setLocal?localId=${siteLanguage.id}"> ${siteLanguage.name}</a>
                        		        			        </li>

                        		</c:forEach>
        			    </ul>
        			 </li>
        		<li class = "liroof">
            				<form class="form-search"  id = "listMovieByName" accept-charset="UTF-8"  name="listMovieByName" method="post" action="listMovieByName"  >
            					<input  name = "searchString"  type="search"  size="25"  pattern="^[A-Za-zА-Яа-яЁё0-9_ -]{1,}$" value = "${searchString}"/>
            					<input type="image" src="/css/img/search.png"/>
            				</form>
            			</li>
			<li class = "liroof">
				<a class = "aroof" href="/listMovies"><fmt:message key = "key.main" bundle = "${lang}"/></a>
			</li>
			<li class = "liroof">
				<a class = "aroof" href="/listHuman"><fmt:message key = "key.movieCrew" bundle = "${lang}"/></a>
			</li>

			<c:if test = "${authorizedUser.roleId == admin}">
			<li class = "liroof listLiHover">
			    <a class = "aroof" href="#"><fmt:message key = "key.adminPanel" bundle = "${lang}"/></a>
			    <div></div>
			    <ul class="ulList" >
			        <li class = "ulListLi">
			            <a class = "aroof" href="/listMoviesAdmin"><fmt:message key = "key.adminMovies" bundle = "${lang}"/></a>
			        </li>
			        <li class = "ulListLi">
			            <a class = "aroof" href="/listUsers"><fmt:message key = "key.adminUsers" bundle = "${lang}"/></a>
			        </li>
			        <li class = "ulListLi">
                    	<a class = "aroof" href="/listEditGenre"><fmt:message key = "key.adminGenres" bundle = "${lang}"/></a>
                    </li>
                    <li class = "ulListLi">
                    	<a class = "aroof" href="/listHumanAdmin"><fmt:message key = "key.movieCrew" bundle = "${lang}"/></a>
                    </li>
			    </ul>
			 </li>

			</c:if>

				<c:if test="${authorizedUser == null}">
							<li class = "liroof ">
            	    <a class = "aroof" href="/login"><fmt:message key = "key.login" bundle = "${lang}"/></a>
									</li>
				</c:if>
		<c:if test="${authorizedUser != null}">
                    	    <c:if test="${authorizedUser.id != 0}">
				<li class = "liroof listLiHover" >
                        			    <a class = "aroof" href="#">${authorizedUser.login}</a>
                        			    <div></div>
                        			    <ul class="ulList" >
                        			    <li class = "ulListLi">
                                         <a class = "aroof" href = "/showMyUser"><fmt:message key = "key.myCab" bundle = "${lang}"/></a>
                                         </li>
                                         <li class = "ulListLi">
                                         <a class = "aroof" href = "/logOut"><fmt:message key = "key.logout" bundle = "${lang}"/></a>
                                        </li>
                        			    </ul>

			</li>

				</c:if>
			</c:if>
		</ul>
		<noscript>
		<p style="position:absolute; background-color:red; top:60%; left:35%;" >
		Некоторые функцие отключены. Пожалуйста включите JavaScript.
		</p>
		</noscript>
	</div>
