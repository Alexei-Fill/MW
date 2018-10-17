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
<body>
	<div   class = "pageBody" align = "center">
	<c:if test = "${exception == 1001}">
        <fmt:message key = "key.incorrectData" bundle = "${lang}"/>
    </c:if>
             <div style = "width:70%; height:600;" >
				<form id = "uploadImageForm" name = "uploadImageForm" action="/uploadUserImage" method="post" enctype="multipart/form-data" onclick = "this.target = 'hiddenframe'" style = "float: left;">
					<img id = "img_up"
					  <c:if test = "${imageURL eq null}">
					  src = "${user.imageURL}"
					  </c:if>
					  <c:if test = "${imageURL != null}">
					  src = "${imageURL}"
					   </c:if>
					    onerror="this.src = '/css/img/default-user-img.jpg'" width="300" height="400" />
					<p></p>
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
					<h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.loginAut" bundle = "${lang}"/></h3>
					<input name = "login" type="text" size="25" value ="${user.login}" style = "text-align: center" required>
					<h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.mail" bundle = "${lang}"/></h3>
					<input  name = "mail" type="text" size="25" value ="${user.mail}" style = "text-align: center" >
					<h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.birthDate" bundle = "${lang}"/></h3>
					<input name = "birthDate"  type="text" size="25" pattern="<fmt:message key = "key.dateFormatPattern" bundle = "${lang}"/>"  value ="<custom:formatDate value = "${user.birthDate}" pattern = "${dateFormat}"/>" style = "text-align: center" required>
                    <h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.role" bundle = "${lang}"/></h3>


					 <select name = "roleId" style = "width:200px;">
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
					 <h3><fmt:message key = "key.registrationDate" bundle = "${lang}"/>:   <custom:formatDate value = "${user.registrationDate}" pattern = "${dateFormat}"/></h3>
                     <p></p>
					 <input type ="submit" onclick="getImageURL()" value ="<fmt:message key = "key.apply" bundle = "${lang}"/>"/>
                </form>
			    <form  accept-charset="UTF-8" name="editUserPassword" method="post" action="editUserPassword?id=${user.id}" >
						<input name = "userId" hidden="false" type="text" size="25" value ="${user.id}">
						<h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.oldPassword" bundle = "${lang}"/></h3>
                        <input name = "oldPassword" type="password" size="25" value ="" style = "text-align: center" required>
						<h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.newPassword" bundle = "${lang}"/></h3>
						<input  name = "newPassword" type="password" size="25" value ="" style = "text-align: center" required>
						<h3 style = "margin-top: 0.3; margin-bottom: 0.3;"><fmt:message key = "key.newPasswordRepeat" bundle = "${lang}"/></h3>
						<input  name = "newPasswordRepeat" type="password" size="25" value ="" style = "text-align: center" required>
						<p></p>
						<input type ="submit" value ="<fmt:message key = "key.changePassword" bundle = "${lang}"/>"/>
				</form>
			</div>
		</div>
</body>
</html>