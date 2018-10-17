<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key = "key.login" bundle = "${lang}"/></title>
  <style type="text/css">
#tab2 {position: fixed; }
.menu1 > a,
.menu1 #tab2:target ~ a:nth-of-type(1),
.menu1 #tab3:target ~ a:nth-of-type(1),
.menu1 > div { padding: 5px; border: 1px solid #aaa; }

.menu1 > a { line-height: 28px; background: #fff; text-decoration: none; }

#tab2,
#tab3,
.menu1 > div,
.menu1 #tab2:target ~ div:nth-of-type(1),
.menu1 #tab3:target ~ div:nth-of-type(1) {display: none; }

.menu1 > div:nth-of-type(1),
.menu1 #tab2:target ~ div:nth-of-type(2),
.menu1 #tab3:target ~ div:nth-of-type(3) { display: block; }

.menu1 > a:nth-of-type(1),
.menu1 #tab2:target ~ a:nth-of-type(2),
.menu1 #tab3:target ~ a:nth-of-type(3) { border-bottom: 2px solid #fff; }
  </style>
</head>
<body>
<div class = "menu1"  style = "position:absolute; top:17%; left:40%;  text-align: center; ">
<p style="width:230px">
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
    <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.loginAut" bundle = "${lang}"/></h3>
    <input name = "login" type="text" size="25" style = "text-align: center" required>
    <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.password" bundle = "${lang}"/></h3>
    <input name = "password" type="password" size="25"  style = "text-align: center" required>
	<p></p>
       <input type ="submit" value ="<fmt:message key = "key.login" bundle = "${lang}"/>"/>
</form>
</div>
<div >
<form name="registration" accept-charset="UTF-8"  method="post" action="registration" >
    <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.loginAut" bundle = "${lang}"/></h3>
    <input name = "login" type="text" size="25" style = "text-align: center" required>
    <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.password" bundle = "${lang}"/></h3>
    <input name = "password" type="password" size="25"  style = "text-align: center" required>
	 <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.passwordRepeat" bundle = "${lang}"/></h3>
    <input name = "passwordRepeat" type="password" size="25"  style = "text-align: center" required>
	<h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.mail" bundle = "${lang}"/></h3>
    <input name = "mail" type="text" size="25" style = "text-align: center" required>
	<h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.birthDate" bundle = "${lang}"/></h3>
    <input name = "birthDate" type="text" size="25" style = "text-align: center"  pattern="<fmt:message key = "key.dateFormatPattern" bundle = "${lang}"/>" required>
	<p></p>
       <input type ="submit" value ="<fmt:message key = "key.registration" bundle = "${lang}"/>"/>
</form>
</div>
</div>
</body>
</html>