<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show All Users</title>
</head>
<body>
<div   class = "pageBody" align = "center">
           <c:forEach items="${users}" var="user">
                <div style = "width:70%; height:100;  border: 1px solid black;" >
                    <img src = "${user.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'"  height="98" style = "float: left;"/>
                    <h hidden="false" >${user.id}</h>
                    <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><a  href = "/showUser?userId=${user.id}">${user.login}</a></h3>
                    <h style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.mail" bundle = "${lang}"/>: ${user.mail}</h>
                    <h style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.birthDate" bundle = "${lang}"/>: <custom:formatDate value = "${user.birthDate}" pattern = "${dateFormat}"/></h>
                    <h style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.registrationDate" bundle = "${lang}"/>: <custom:formatDate value = "${user.registrationDate}" pattern = "${dateFormat}"/></h>
                    <p></p>
                    <a href="/showUserEdit?userId=${user.id}"><fmt:message key = "key.edit" bundle = "${lang}"/></a>
                    <a href="/deleteUser?userId=${user.id}"><fmt:message key = "key.delete" bundle = "${lang}"/></a>
                </div>
            </c:forEach>
           </div>
</body>
</html>