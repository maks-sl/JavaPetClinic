<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

    <title>Clinic Pet Web - Login page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/pet.css" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="content-language" content="ru"/>

</head>

<body>
<div id="MAIN_SITE">

    <div id="HEADER">

        <div id="HEAD_SPACE">
            <img src="${pageContext.servletContext.contextPath}/images/head_animals.png" alt="Clinic Pet Web" style="right: 60px;"/>
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


            <h2>Login page</h2>
            <br>

            <form class="filled_form login_form" name="f" action="${pageContext.servletContext.contextPath}/login" method="POST">
                <ul>
                    <li>
                        <br />
                        <h2>Please, enter you identity</h2>
                        <!-- <span class="required_notification">* Заполните обязательные поля</span> -->
                    </li>
                    <li>
                        <label for="username">Login:</label>
                        <input type="text" id="username" name="username" value="" type="text" />
                    </li>
                    <li>
                        <label for="password">Password:</label>
                        <input id="password" name="password" type="password"/>
                    </li>
                    <li>
                        <button class="submit" value="Login">Login</button>
                        <font color="red">
                            <p>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
                        </font>
                    </li>
                </ul>
            </form>





            <div id='sidebar'></div><!-- sidebar -->
        </div><!-- end CONTENT -->
        <div id="FOOTER">
            <div id="FOOT_DOM"></div>
            <div id="FOOT_SPACE"></div>
            <div id="COPY_R"><p><a href="/cpw">Clinic Pet Web </a>&copy 2017</p></div>
        </div>
    </div>
    <div id="DROP_DOWN_FOOTER">

        <div id="FOOT_BG-LINES"></div>
        <!-- <div id="FOOT_DOM"></div> -->
    </div>
</div>

</body>
</html>
