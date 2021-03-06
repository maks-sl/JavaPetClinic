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
            <h2>Pets Editor</h2>
            <br>
            <form class="filled_form" action="${pageContext.servletContext.contextPath}/pet/create" method="post" name="contact_form">
            <input type="hidden" name="clientId" value="${clientId}">
            <ul>
                <li>
                    <h2>Add pet</h2>
                    <span class="required_notification">* Заполните обязательные поля</span>
                </li>
                <li>
                    <label for="petName">Name:</label>
                    <input type="text" id="petName" name="petName"  placeholder="Бобик"required />
                </li>
                <%--<li>--%>
                <%--<label for="age">Age:</label>--%>
                <%--<input id="age" name="age" value="${pet.age}" required />--%>
                <%--</li>--%>
                <li>
                    <label for="petTypeName">Type:</label>
                    <select size = "1" size = "1" id="petTypeName" name="petTypeName" style="padding: 5px; width: 260px" required>
                        <c:forEach items="${petTypes}" var="petType" varStatus="status">
                            <option value = "${petType.toString()}">${petType.toString()}</option>
                        </c:forEach>
                    </select>
                </li>
                <li>
                    <button class="submit" type="submit">Создать</button>
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
