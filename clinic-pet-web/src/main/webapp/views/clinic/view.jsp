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


            <h2>Clients Manager</h2>
            <br>
            <br>
            <form action="${pageContext.servletContext.contextPath}/client/search" id="searchForm" method="POST">
                <div class="clientsTable">

                    <div class="clientCol">
                        <label for="findClientName">Client name:</label>
                        <input type="text" id="findClientName" placeholder="Ivan Petrov" name="findClientName" value="${findClientName}"/>
                    </div>

                    <div class="checkCol">
                        <label for="andCheck">&:</label>
                        <input id="andCheck" type="checkbox" name="isAnd" id="isAnd" ${andChecked} />
                    </div>

                    <div class="petCol">
                        <label for="findPetName">Pet name:</label>
                        <input type="text" id="findPetName" placeholder="Boris" name="findPetName" value="${findPetName}"/>
                    </div>

                    <div class="buttonCol">
                        <br />
                        <button type="submit" class="submit" id="srchBtn" />Apply filter</button>
                    </div>
                </div>
            </form>

            <br />

            <div class="clientsTable">

                <div class="clientRow">
                    <div class="clientCol">
                        <label>Client</label>
                    </div>
                    <div class="petCol">
                        <label>Pets</label>
                    </div>

                    <div class="buttonCol">
                        <a href="${pageContext.servletContext.contextPath}/views/client/create.jsp">+ Add Client</a>
                    </div>
                </div>


                <c:forEach items="${clients}" var="client" varStatus="status">




                    <div class="clientRow">
                        <div class="clientCol">
                                ${client.getFullName()}
                        </div>
                        <div class="petCol">
                            <ul>
                                <c:forEach items="${client.getPets()}" var="pet" varStatus="status">
                                    <li>${pet.petType.name} / ${pet.getName()}</li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="buttonCol">
                            <a href="${pageContext.servletContext.contextPath}/client/edit?id=${client.id}">Изменить</a>
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
