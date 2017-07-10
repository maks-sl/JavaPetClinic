<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            <img src="${pageContext.servletContext.contextPath}/images/head_animals.png" alt="Clinic Pet Web" />
            <div id="AUTH_INF">
                <form id="logoutBtn" action="${pageContext.servletContext.contextPath}/logout">
                    <button class="submit" type="submit">Logout</button>
                </form>
                <p>Login as: </p>
                <p><sec:authentication property="name" /> <sec:authentication property="authorities" /></p>
            </div>
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


            <h2>Client editor</h2>
            <br>
            <form class="deleteBtn"
            <c:if test = "${clientPets.size() > 0}">
                onsubmit="alert('Сначала удалите всех животных клиента');return false"
            </c:if>
                  action="${pageContext.servletContext.contextPath}/client/delete">
                <input type="hidden" name="id"   value="${client.id}" />
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <button class="submit" type="submit">- Удалить клиента</button>
                </sec:authorize>
            </form>



            <form class="filled_form" action="${pageContext.servletContext.contextPath}/client/edit" method="post" name="contact_form">
                <input type="hidden" name="id" value="${client.id}">
                <ul>
                    <li>
                        <h2>Edit client</h2>
                        <%--<span class="required_notification">* Не оставляйте пустыми эти поля</span>--%>
                    </li>
                    <li>
                        <label for="name">Name:</label>
                        <input type="text" id="name" name="name" placeholder="Ivan" value="${client.name}" required />
                    </li>
                    <li>
                        <label for="surname">Surame:</label>
                        <input type="text" id="surname" name="surname" placeholder="Ivanov" value="${client.surname}" required />
                    </li>
                    <li>
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" placeholder="iivanov@example.com" value="${client.email}" required />
                        <span class="form_hint">Proper format "name@something.com"</span>
                    </li>
                    <li>
                        <input type="radio" name="gender" value="1" <c:if test="${client.gender eq 1}">checked="checked"</c:if> required /> Male<br />
                        <input type="radio" name="gender" value="2" <c:if test="${client.gender eq 2}">checked="checked"</c:if> /> Female<br />
                    </li>
                    <li>
                        <button class="submit" type="submit">Применить</button>
                    </li>
                </ul>
            </form>
            <br>
            <br>


                <div class="clientsTable">

                    <div class="clientRow">
                        <div class="clientCol">
                            <label>Pet</label>
                        </div>
                        <div class="petCol">
                            <label>Actions</label>
                        </div>

                        <div class="buttonCol">
                            <a href="${pageContext.servletContext.contextPath}/pet/create?clientId=${client.getId()}">+ Add Pet</a>
                        </div>
                    </div>

                    <c:forEach items="${clientPets}" var="pet" varStatus="status">
                        <div class="clientRow">
                            <div class="clientCol">
                                    ${pet.petType.getName()} / ${pet.name}
                            </div>
                            <div style="margin-left: 0px;" class="buttonCol">
                                <a href="${pageContext.servletContext.contextPath}/pet/edit?clientId=${client.id}&petId=${pet.getId()}">Изменить</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>


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