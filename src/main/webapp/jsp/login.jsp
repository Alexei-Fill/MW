<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key = "key.login" bundle = "${lang}"/></title>
<link href="/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="item2" style = "background-color: white; "></div>
 <div class="item4"></div>

	 <div class="item3" >
<div class = "menu1"  style = "position:absolute; top:17%; left:40%;  text-align: center; ">
<p style="width:250px; text-align:center;">
<c:if test = "${exception == 403}">
  <fmt:message key = "key.forbidden" bundle = "${lang}"/>
</c:if>
<c:if test = "${exception == 1001}">
<fmt:message key = "key.incorrectData" bundle = "${lang}"/>
</c:if>
<c:if test = "${exception == 1002}">
  <fmt:message key = "key.wrongLoginOrPass" bundle = "${lang}"/>
</c:if>
</p>

<br id = "tab2"></br>
<a href="#tab1"><fmt:message key = "key.login" bundle = "${lang}"/></a>
<a href="#tab2"><fmt:message key = "key.registration" bundle = "${lang}"/></a>
<div  >
<form name="authorization" accept-charset="UTF-8"  method="post" action="authorization" >
    <h3><fmt:message key = "key.loginAut" bundle = "${lang}"/></h3>
    <input  class = "textField" name = "login" type="text" maxlength="24" pattern="^[A-Za-z0-9_-]{4,16}$" required />
    <h3><fmt:message key = "key.password" bundle = "${lang}"/></h3>
    <input  class = "textField" name = "password" type="password" maxlength="24"  required />
	 <div style = "margin-bottom:10px;"></div>
       <input type ="submit" value ="<fmt:message key = "key.login" bundle = "${lang}"/>"/>
</form>
</div>
<div >
<form name="registration" accept-charset="UTF-8"  method="post" action="registration" >
    <h3><fmt:message key = "key.loginAut" bundle = "${lang}"/></h3>
    <input  class = "textField" name = "login" type="text" maxlength="24" pattern="^[A-Za-z0-9_-]{4,16}$" required />
    <h3><fmt:message key = "key.password" bundle = "${lang}"/></h3>
    <input  class = "textField" name = "password" type="password" maxlength="24"   required />
	 <h3><fmt:message key = "key.passwordRepeat" bundle = "${lang}"/></h3>
    <input  class = "textField" name = "passwordRepeat" type="password" maxlength="24"  required />
	<h3><fmt:message key = "key.mail" bundle = "${lang}"/></h3>
    <input  class = "textField" name = "mail" type="text" maxlength="32" pattern="^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$" required />
	<h3><fmt:message key = "key.birthDate" bundle = "${lang}"/></h3>
    <input class = "textField" name = "birthDate" type="text" maxlength="10" style = "text-align: center"  pattern="<fmt:message key = "key.dateFormatPattern" bundle = "${lang}"/>" required />
	 <div style = "margin-bottom:10px;"></div>
       <input type ="submit" value ="<fmt:message key = "key.registration" bundle = "${lang}"/>"/>
</form>
</div>
</div>
</body>
</html>