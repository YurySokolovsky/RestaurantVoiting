<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="restaurant_voiting" tagdir="/WEB-INF/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<sec:authorize access="isAuthenticated()">
    <script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
    <script type="text/javascript" src="resources/js/profile.js" defer></script>
</sec:authorize>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <div class="row">
            <div class="col-5 offset-3">
                <h3>${userTo.name} <spring:message code="${register ? 'app.register' : 'app.profile'}"/></h3>
                <form:form modelAttribute="userTo" class="form-horizontal" method="post" action="${register ? 'register' : 'profile'}"
                           charset="utf-8" accept-charset="UTF-8">

                    <restaurant_voiting:inputField labelCode="user.name" name="name"/>
                    <restaurant_voiting:inputField labelCode="user.email" name="email"/>
                    <restaurant_voiting:inputField labelCode="user.change.confirm" name="password" inputType="password"/>

                    <sec:authorize access="isAuthenticated()">
                    <div class="text-left">
                            <button type="reset" class="btn btn-link btn-sm pt-0 pb-3" onclick="changePassword()" style="color: grey">
                            <h6><spring:message code="password.change"/></h6>
                        </button>
                    </div>
                    </sec:authorize>

                    <div class="text-right">
                        <button type="submit" class="btn btn-primary">
                            <span class="fa fa-check"></span>
                            <spring:message code="common.save"/>
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<sec:authorize access="isAuthenticated()">
    <div class="modal fade" tabindex="-1" id="change">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="modalTitle"><spring:message code="password.edit"/></h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form id="detailsForm">
                        <div class="form-group">
                            <label for="oldPassword" class="col-form-label"><spring:message code="password.old"/></label>
                            <input type="password" class="form-control" id="oldPassword" name="oldPassword"/>
                        </div>

                        <div class="form-group">
                            <label for="newPassword" class="col-form-label"><spring:message code="password.new"/></label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword"/>
                        </div>

                        <div class="form-group">
                            <label for="repeatPassword" class="col-form-label"><spring:message code="password.repeat"/></label>
                            <input type="password" class="form-control" id="repeatPassword" name="repeatPassword"/>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">
                        <span class="fa fa-close"></span>
                        <spring:message code="common.cancel"/>
                    </button>
                    <button type="button" class="btn btn-primary" onclick="savePassword()">
                        <span class="fa fa-check"></span>
                        <spring:message code="common.save"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="password"/>
</jsp:include>
</html>