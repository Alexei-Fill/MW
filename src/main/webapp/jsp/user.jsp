<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${user.login}" /></title>
<script src="http://code.jquery.com/jquery-2.0.2.min.js"></script>
<script src="/js/userEditDialogWindow.js"></script>
<link href="/css/userEdit.css"  rel='stylesheet' type='text/css'/>
</head>
<body>
<div class="item2" style = "background-color: white; "></div>
 <div class="item4"></div>

	 <div class="item3" >
         <div class = "pageContentMovie" style = "margin-left: 50px; margin-top: 50px; ">
                                         <img src = "${user.imageURL}" onerror="this.src = '/css/img/default-user-img.jpg'"  class = "imgUser"/>
                                          <input name = "userId" hidden="false" type="text" size="25" value ="${user.id}">
                                         <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.loginAut" bundle = "${lang}"/></h3>
                                         <h3 style = "margin-top: 0.3; margin-bottom: 0.3; color:red;">${user.login}</h3>
                                         <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.mail" bundle = "${lang}"/></h3>
                                         <h3 style = "margin-top: 0.3; margin-bottom: 0.3; color:red;">${user.mail}</h3>
                                         <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.birthDate" bundle = "${lang}"/></h3>
                                         <h3 style = "margin-top: 0.3; margin-bottom: 0.3; color:red;"><custom:formatDate value = "${user.birthDate}" pattern = "${dateFormat}"/></h3>
                                         <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.registrationDate" bundle = "${lang}"/></h3>
                                         <h3 style = "margin-top: 0.3; margin-bottom: 0.3; color:red;"><custom:formatDate value = "${user.registrationDate}" pattern = "${dateFormat}"/></h3>
                                        <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.role" bundle = "${lang}"/></h3>
                                        <h3 style = "margin-top: 0.3; margin-bottom: 0.3; color:red;">
                                        <c:if test = "${user.roleId == admin}">
                                        <fmt:message key = "key.roleAdmin" bundle = "${lang}"/>
                                        </c:if>
                                         <c:if test = "${user.roleId == commonUser}">
                                         <fmt:message key = "key.roleUser" bundle = "${lang}"/>
                                         </c:if></h3>
                                        <a href="/showUserEdit?userId=${user.id}"><fmt:message key = "key.edit" bundle = "${lang}"/></a>
            <c:if test="${authorizedUser.id == user.id}">
              <div class="delete-container">
                  <a href="javascript:PopUpShow()"><fmt:message key = "key.delete" bundle = "${lang}"/></a>
              </div>
            </c:if>
           <c:if test="${authorizedUser.id != user.id}">
                <c:if test = "${authorizedUser.roleId == admin}">
                <a onclick = "return confirm('<fmt:message key = "key.doYouWantReplaceUser" bundle = "${lang}"/>');" href="/deleteUser?userId=${user.id}"><fmt:message key = "key.delete" bundle = "${lang}"/></a>
            </c:if>
            </c:if>

               </div>
            </div>
<c:if test="${authorizedUser.id == user.id}">
                          <div class="delete-popup" id="popup1">
                              <div class="b-popup-content">
                              <a  href="javascript:PopUpHide()">X</a>
                          	 <form  align = "center" accept-charset="UTF-8" name="deleteMyUser" method="post" action="deleteMyUser?userId=${user.id}">
                              <input name = "userId" hidden="false" type="text" size="25" value ="${user.id}">
                              <h3 style = "margin-top: 0.3; margin-bottom: 0.3; text-align:center;"><fmt:message key = "key.deleteMyAccountDialog" bundle = "${lang}"/></h3>
                              <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.password" bundle = "${lang}"/></h3>
                              <input  name = "password" type="password" size="25" value ="" style = "text-align: center" required>
                              <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.passwordRepeat" bundle = "${lang}"/></h3>
                              <input  name = "passwordRepeat" type="password" size="25%" value ="" style = "text-align: center" required>
                              <p></p>
                              <input type ="submit" value ="<fmt:message key = "key.delete" bundle = "${lang}"/>"/>
                              </form>
                              </div>
                          </div>
                           </c:if>
</body>
</html>