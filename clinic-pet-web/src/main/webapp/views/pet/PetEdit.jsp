<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

    <title>Clinic Pet Web - Client Manager</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/pet.css" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="content-language" content="ru"/>

</head>

<body>
<div id="MAIN_SITE">

    <div id="HEADER">

        <div id="HEAD_SPACE">
            <img src="${pageContext.servletContext.contextPath}/images/head_bg.png" alt="Clinic Pet Web" />
        </div>
        <!-- <div id="HEAD_TOP"></div> -->
        <!-- <div id="HEAD_BOTTOM"></div> -->
        <a href="${pageContext.servletContext.contextPath}" title="Clinic Pet Web" alt="Clinic Pet Web">
            <div id="LOGO">
                <img src="${pageContext.servletContext.contextPath}/images/logo.png" alt="Clinic Pet Web" />
                <div id="LOGO_LINES"></div>
            </div>
        </a>
    </div>

    <div id="CENTRAL_FRAME">


        <div id="CONTENT">


            <h2>Pet Editor</h2>
            <br>



            <form class="deleteBtn" action="${pageContext.servletContext.contextPath}/pet/delete">
                <%--<input type="hidden" name="clientId"   value="${client.id}" />--%>
                <input type="hidden" name="petId" value="${pet.getId()}" />
                <button class="submit" type="submit">- Удалить питомца</button>
            </form>

            <form class="filled_form" action="${pageContext.servletContext.contextPath}/pet/edit" method="post" name="contact_form">
            <%--<input type="hidden" name="clientId" value="${client.id}">--%>
            <input type="hidden" name="petId" value="${pet.id}">
            <ul>
                <li>
                    <h2>Edit pet</h2>
                    <%--<span class="required_notification">* Не оставляйте пустыми эти поля</span>--%>
                </li>
                <li>
                    <label for="petName">Name:</label>
                    <input type="text" id="petName" name="petName"  value="${pet.name}" required />
                </li>
                <%--<li>--%>
                <%--<label for="age">Age:</label>--%>
                <%--<input id="age" name="age" value="${pet.age}" required />--%>
                <%--</li>--%>
                <li>
                    <label for="petTypeId">Type:</label>
                    <select size = "1" size = "1" id="petTypeId" name="petTypeId" style="padding: 5px; width: 260px" required>
                        <c:forEach items="${petTypes}" var="petType" varStatus="status">
                            <option value = "${petType.id}"  <c:if test="${petType.id eq pet.petType.id}">selected="selected"</c:if>  >${petType.name}</option>
                        </c:forEach>
                    </select>
                </li>
                <li>
                    <button class="submit" type="submit">Применить</button>
                </li>
            </ul>
        </form>



            <div id='sidebar'></div><!-- sidebar -->
        </div><!-- end CONTENT -->
        <div id="FOOTER">
            <div id="FOOT_DOM"></div>
            <div id="FOOT_SPACE"></div>
            <div id="COPY_R"><p><a href="${pageContext.servletContext.contextPath}">Clinic Pet Web </a>&copy 2017</p></div>
        </div>
    </div>
    <div id="DROP_DOWN_FOOTER">

        <div id="FOOT_BG-LINES"></div>
        <!-- <div id="FOOT_DOM"></div> -->
    </div>
</div>

</body>
</html>
