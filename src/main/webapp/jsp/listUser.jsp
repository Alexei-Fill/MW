<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key = "key.listUser" bundle = "${lang}"/></title>
</head>
<body>
<div class="item2"  style = "background-color: white; ">	</div>
<div class="item4"></div>
                              <div class="item3">
                <c:forEach items="${users}" var="user">
                <div class = "pageContentUser" >
                    <img src = "${user.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'"  class = "imgListUser"/>
                    <h hidden="false" >${user.id}</h>
                    <h3><a  href = "/showUser?userId=${user.id}">${user.login}</a></h3>
                    <h4><fmt:message key = "key.mail" bundle = "${lang}"/>: ${user.mail}</h4>
                   <h4><fmt:message key = "key.birthDate" bundle = "${lang}"/>: <custom:formatDate value = "${user.birthDate}" pattern = "${dateFormat}"/></h4>
                    <h4><fmt:message key = "key.registrationDate" bundle = "${lang}"/>: <custom:formatDate value = "${user.registrationDate}" pattern = "${dateFormat}"/></h4>
                    <p></p>
                    <a href="/showUserEdit?userId=${user.id}"><fmt:message key = "key.edit" bundle = "${lang}"/></a>
                    <a  onclick = "return confirm('<fmt:message key = "key.doYouWantReplaceUser" bundle = "${lang}"/>');" href="/deleteUser?userId=${user.id}"><fmt:message key = "key.delete" bundle = "${lang}"/></a>
                </div>
            </c:forEach>
           </div>
</body>
</html>