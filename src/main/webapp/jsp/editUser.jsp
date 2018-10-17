<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${user.login}" /></title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="/js/uploadImage.js"></script>
</head>
<div class="item2" style = "background-color: white; "></div>
 <div class="item4"></div>

	 <div class="item3" >
         <div class = "pageContentMovie" style = "margin-left: 50px; margin-top: 50px; ">
	<c:if test = "${exception == 1001}">
        <fmt:message key = "key.incorrectData" bundle = "${lang}"/>
    </c:if>
				<form id = "uploadImageForm" name = "uploadImageForm" action="/uploadUserImage" method="post" enctype="multipart/form-data" onclick = "this.target = 'hiddenframe'" class = "imgUpload" style = "float: left;">
					<img id = "img_up"
					  <c:if test = "${imageURL eq null}">
					  src = "${user.imageURL}"
					  </c:if>
					  <c:if test = "${imageURL != null}">
					  src = "${imageURL}"
					   </c:if>
					    onerror="this.src = '/css/img/default-user-img.jpg'" width="300" height="400" />
					  <div style = "margin-bottom:10px;"></div>
					<input type="file" name="file" id="file" onchange="previewFile()" />
					<input type=hidden name=MAX_FILE_SIZE value=64000>
					 <noscript>
					 <input type ="submit"  value ="<fmt:message key = "key.apply" bundle = "${lang}"/>"  />
					 </noscript>
				</form>
                <form  accept-charset="UTF-8" name="editUser" method="post" action="editUser?id=<c:out value="${user.id}"/>" >
					<iframe id=hiddenframe name=hiddenframe src = "/jsp/image.jsp" style="width:0px; height:0px; border:0px"></iframe>
					<input id = imageURL name = "imageURL" hidden="false" type="text" size="25"
						 <c:if test = "${imageURL == null}">
                                         <c:if test = "${user == null}">
                                             value ="/css/img/default-user-img.jpg"/>
                                         </c:if>
                                         <c:if test = "${user != null}">
                                             <c:if test = "${user.id != 0}">
                                                  value ="${user.imageURL}"/>
                                             </c:if>
                                         </c:if>

                    				 </c:if>
                    				  <c:if test = "${imageURL != null}">
                                     		value ="${imageURL}"/>
                                     </c:if>

					<input name = "userId" hidden="false" type="text" size="25" value ="${user.id}"/>
					<h3><fmt:message key = "key.loginAut" bundle = "${lang}"/></h3>
					<input class = "textField" name = "login" type="text" size="25" value ="${user.login}" disabled>
					<h3><fmt:message key = "key.registrationDate" bundle = "${lang}"/>:</h3>
					<h3><custom:formatDate value = "${user.registrationDate}" pattern = "${dateFormat}"/></h3>
					<h3><fmt:message key = "key.mail" bundle = "${lang}"/></h3>
					<input class = "textField" name = "mail" type="text" maxlength="32" value ="${user.mail}" pattern = "^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$" required />
					<h3><fmt:message key = "key.birthDate" bundle = "${lang}"/></h3>
					<input class = "textField" name = "birthDate"  type="text" maxlength="10" pattern="<fmt:message key = "key.dateFormatPattern" bundle = "${lang}"/>"  value ="<custom:formatDate value = "${user.birthDate}" pattern = "${dateFormat}"/>" required />

                    <h3><fmt:message key = "key.role" bundle = "${lang}"/></h3>

					 <select name = "roleId"  class = "textField"  >
                     	<option value=1
                     	<c:if test = "${user.roleId == commonUser}">
                     	selected
                     	</c:if>
                     	>
                     	<fmt:message key = "key.roleUser" bundle = "${lang}"/></option>
                     	<c:if test = "${authorizedUser.roleId == admin}">
                     	<option value=2
                     	<c:if test = "${user.roleId == admin}">
                     	selected
                     	</c:if>
                     	>
                     	<fmt:message key = "key.roleAdmin" bundle = "${lang}"/></option>
                     	</c:if>
                     </select>
  <div style = "margin-bottom:10px;"></div>

					 <input type ="submit" onclick="getImageURL()" value ="<fmt:message key = "key.apply" bundle = "${lang}"/>"/>
                </form>
			    <form  accept-charset="UTF-8" name="editUserPassword" method="post" action="editUserPassword?id=${user.id}" >
						<input name = "userId" hidden="false" type="text" size="25" value ="${user.id}">
						<h3><fmt:message key = "key.oldPassword" bundle = "${lang}"/></h3>
                        <input class = "textField" name = "oldPassword" type="password" maxlength="24" value ="" required />
						<h3><fmt:message key = "key.newPassword" bundle = "${lang}"/></h3>
						<input class = "textField" name = "newPassword" type="password" maxlength="24" value ="" required />
						<h3><fmt:message key = "key.newPasswordRepeat" bundle = "${lang}"/></h3>
						<input class = "textField" name = "newPasswordRepeat" type="password" maxlength="24" value =""  required />
						<div style = "margin-bottom:10px;"></div>
						<input type ="submit" value ="<fmt:message key = "key.changePassword" bundle = "${lang}"/>"/>
				</form>
			</div>
		</div>
</body>
</html>