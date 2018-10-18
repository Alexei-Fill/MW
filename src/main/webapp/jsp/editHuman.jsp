<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${human.name} ${human.surname}</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="/js/uploadImage.js"></script>
</head>
<body>
<div class="item2" style = " background-color: white;">
  			<form id = "uploadImageForm" name = "uploadImageForm" action="/uploadMovieImage" method="post" enctype="multipart/form-data" onclick = "this.target = 'hiddenframe'" >
  				<img id = "img_up" class = "imgUpload"
  				 <c:if test = "${imageURL eq null}">
  				 src = "${human.imageURL}"
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

			<form  accept-charset="UTF-8"  id = "editHuman" name="editHuman" method="post" action="editHuman" >
				<iframe id=hiddenframe name=hiddenframe src = "/jsp/image.jsp" style="width:0px; height:0px; border:0px"></iframe>
				 <input id = imageURL name = "imageURL" hidden="false" type="text" size="25"
				 <c:if test = "${imageURL == null}">
                     <c:if test = "${human == null}">
                         value ="/css/img/thumb.png"/>
                     </c:if>
                     <c:if test = "${human != null}">
                         <c:if test = "${human.id != 0}">
                              value ="${human.imageURL}"/>
                         </c:if>
                     </c:if>

				 </c:if>
				  <c:if test = "${imageURL != null}">
                 		value ="${imageURL}"/>
                 </c:if>
				<input name = "humanId" hidden="false" type="text" size="25"
				  <c:if test = "${human != null}">
				  value ="${human.id}"/>
				  </c:if>
				    <c:if test = "${human == null}">
				    value ="0"/>
				    </c:if>


				<h4><fmt:message key = "key.birthDate" bundle = "${lang}"/></h4>
				<input  class = "textField" name = "birthDate"  type="text" maxlength="10"  placeholder= "<fmt:message key = "key.placeHoldDate" bundle = "${lang}"/>" pattern="<fmt:message key = "key.dateFormatPattern" bundle = "${lang}"/>"  value ="<custom:formatDate value = "${human.birthDate}" pattern = "${dateFormat}"/>"  required />
						<c:forEach items="${languages}" var ="language">
							<div class = "languageBorder">
							<input  class = "textField" name = "characteristicLanguageId" hidden="false" type="text" size="25" value ="${language.id}" />
							<h3>${language.name}</h3>
							<h4><fmt:message key = "key.name" bundle = "${lang}"/></h4>
							<input  class = "textField" name = "name${language.id}" type="text" maxlength="24" value ="${human.name}"  pattern="^[A-Za-zА-Яа-яЁё0-9_ -]{1,}$"required/>
							<h4><fmt:message key = "key.surname" bundle = "${lang}"/></h4>
                            <input  class = "textField" name = "surname${language.id}" type="text" maxlength="45" value ="${human.surname}" pattern="^[A-Za-zА-Яа-яЁё0-9_ -]{1,}$" />
                            <h4><fmt:message key = "key.patronymic" bundle = "${lang}"/></h4>
     						<input  class = "textField" name = "patronymic${language.id}" type="text" maxlength="32" value ="${human.patronymic}" pattern="^[A-Za-zА-Яа-яЁё0-9_ -]{1,}$" />
							<h4><fmt:message key = "key.biography" bundle = "${lang}"/></h4>
							<textarea class = "textArea" maxlength = "10240" rows="10" cols="45" name="biography${language.id}" pattern="^[A-Za-zА-Яа-яЁё0-9_ -]{1,}$" required >${human.biography}</textarea>
																	<h3> </h3>
                            </div>
						</c:forEach>
 <div style = "margin-bottom:10px;"></div>
					<input type ="submit" onclick="getImageURL()"  value ="<fmt:message key = "key.apply" bundle = "${lang}"/>"  />

				</div>
			</form>
	</div>
</body>
</html>