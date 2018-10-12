<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<nav class="navbar navbar-expand-md navbar-dark py-0">
    <div class="container">
        <a href="votes" class="navbar-brand"><img src="resources/images/icon-voiting.png"> <spring:message code="app.title"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <form:form class="form-inline my-2" action="logout" method="post">
                            <sec:authorize access="isAuthenticated()">
                                <a class="btn btn-info mr-1" href="votes"><spring:message code="vote.titleBtn"/></a>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a class="btn btn-info mr-1" href="restaurants"><spring:message code="restaurant.title"/></a>
                                    <a class="btn btn-info mr-1" href="users"><spring:message code="user.title"/></a>
                                </sec:authorize>
                                <a class="btn btn-info mr-1" href="profile"><spring:message code="app.profile"/></a>
                                <button class="btn btn-primary" type="submit">
                                    <span class="fa fa-sign-out"></span>
                                    <spring:message code="app.signout"/>
                                </button>
                            </sec:authorize>
                        </form:form>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                <li class="nav-item">
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle my-1 ml-2" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="fa fa-sign-in"></span>
                            <spring:message code="app.signin"/>
                        </button>
                        <div class="dropdown-menu">
                            <form:form class="form-group" id="login_form" action="spring_security_check" method="post">
                                <input class="form-control mr-1" type="text" style = "margin-bottom : 10px!important" placeholder="<spring:message code="user.email"/>" name="username">
                                <input class="form-control mr-1" type="password" style = "margin-bottom : 10px!important" placeholder=<spring:message code="user.password"/> name="password">
                                 <button class="btn btn-success" type="submit" style="width: 100%">
                                        <spring:message code="app.signin"/>
                                 </button>
                                <div class="dropdown-divider"></div>
                                <a class="btn btn-primary" href="register" style="width: 100%"><spring:message code="app.register"/></a>
                            </form:form>
                        </div>
                    </div>
                </li>
                </sec:authorize>
                <li class="nav-item dropdown">
                    <a class="dropdown-toggle nav-link my-1 ml-2" data-toggle="dropdown">${pageContext.response.locale}</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a>
                        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">Русский</a>
                    </div>
                </li>
            </ul>
       </div>
    </div>
</nav>
<script type="text/javascript">
    var localeCode = "${pageContext.response.locale}";
</script>