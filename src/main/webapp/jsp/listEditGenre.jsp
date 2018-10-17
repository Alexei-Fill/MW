<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Genre</title>
</head>
<body>
<div   class = "pageBody" align = "center">
<c:if test = "${exception == 1001}">
    <fmt:message key = "key.incorrectData" bundle = "${lang}"/>
</c:if>
                <div style = "width:70%; height:50;  border: 1px solid black;" >
                             <form name="addGenre" accept-charset="UTF-8"  method="post" action="addGenre" >
                              <input name = "genreId"  hidden="false"  type="text" size="25"  style = "text-align: center" value = "0" required>
                              <c:forEach items="${languages}" var ="language">
                             <input name = "characteristicLanguageId" hidden="false" type="text" size="25" value ="${language.id}">${language.name}

                                 <input name = "name${language.id}" type="text" size="25" style = "text-align: center" value = "${genre.name}" required>
                                </c:forEach>
                                <input type ="submit" value ="<fmt:message key = "key.apply" bundle = "${lang}"/>"/>
                             </form>
                          </div>
           <c:forEach items="${genres}" var="genre">
                <div style = "width:70%; height:50;  border: 1px solid black;" >
                   <form name="editGenre" accept-charset="UTF-8"  method="post" action="editGenre" >
                       <input name = "genreId"  hidden="false"  type="text" size="25"  style = "text-align: center" value = "${genre.id}" required>
                       <input name = "name" type="text" size="25" style = "text-align: center" value = "${genre.name}" required>
                       <input type ="submit" value ="<fmt:message key = "key.edit" bundle = "${lang}"/>"/>
                       <a onclick = "return confirm('<fmt:message key = "key.doYouWantReplaceGenre" bundle = "${lang}"/>');" href="/deleteGenre?genreId=${genre.id}"><fmt:message key = "key.delete" bundle = "${lang}"/></a>
                   </form>
                </div>
            </c:forEach>
</div>
</body>
</html>