<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/voteDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h4><spring:message code="vote.title"/></h4>
        <br/>
        <div class="row">
            <div class="col-4">
                <div class="card">
                    <div class="card-header"><h5><spring:message code="vote.dateSearch"/></h5></div>
                    <div class="card-body py-0">
                        <form id="getVotesByDate">
                            <div class="row">
                                <div class="col-10">
                                    <div class="form-inline my-2">
                                        <label class="col-form-label" for="date" style="margin-right: 10px"><spring:message code="vote.date"/></label>
                                        <input class="form-control col-6" name="date" id="date">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-right">
                        <button class="btn btn-danger" onclick="clearSearch()">
                            <span class="fa fa-remove" style="color:white"></span>
                            <spring:message code="common.cancel"/>
                        </button>
                        <button class="btn btn-primary" onclick="updateTable()">
                            <span class="fa fa-filter"></span>
                            <spring:message code="common.find"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
            <br/>
            <h5 align="center"><spring:message code="vote.information"/></h5>
            <br/>
        <table class="table table-striped" id="datatable">
            <thead>
                <tr>
                    <th><spring:message code="vote.name"/></th>
                    <th><spring:message code="vote.address"/></th>
                    <th><spring:message code="vote.rating"/></th>
                    <th><spring:message code="vote.menu"/></th>
                    <th><spring:message code="vote.vote"/></th>
                </tr>
            </thead>
        </table>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="vote"/>
</jsp:include>
</html>
