<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body onload="getDishes(${restaurantId}">
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <script type="text/javascript" src="resources/js/dishAdminDatatables.js" defer></script>
</sec:authorize>
<sec:authorize access="!hasRole('ROLE_ADMIN')">
    <script type="text/javascript" src="resources/js/dishDatatables.js" defer></script>
</sec:authorize>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h4><p><spring:message code="dish.title"/></p></h4>

        <h5 id="restaurantInfo">${restaurant.restaurantName}, ${restaurant.address}</h5>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
        <div class="row">
            <div class="col-4">
                <div class="card">
                    <div class="card-header"><h5><spring:message code="dish.dateSearch"/></h5></div>
                    <div class="card-body py-0">
                        <form id="getDishesByDate">
                            <div class="row">
                                <div class="col-10">
                                    <div class="form-inline my-2">
                                        <label class="col-form-label" for="date" style="margin-right: 10px"><spring:message code="dish.date"/></label>
                                        <input class="form-control col-6" name="date" id="date">
                                        <input type="hidden" id="restaurantId" name="restaurantId" value="${restaurantId}">
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

            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="common.add"/>
            </button>
        </sec:authorize>

        <table class="table table-striped" id="datatable">
            <thead>
                <tr>
                    <th><spring:message code="dish.description"/></th>
                    <th><spring:message code="dish.cost"/></th>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <th></th>
                    <th></th>
                    </sec:authorize>
                </tr>
            </thead>
        </table>
    </div>
</div>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    <div class="modal fade" tabindex="-1" id="editRow">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="modalTitle"></h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form id="detailsForm">
                        <input type="hidden" id="id" name="id">

                        <div class="form-group">
                            <label for="description" class="col-form-label"><spring:message code="dish.description"/></label>
                            <input type="text" class="form-control" id="description" name="description" list="dishname"
                                   placeholder="<spring:message code="dish.description"/>">
                            <datalist id="dishname">
                                <c:forEach items="${dishesDescriptions}" var="dishDescription">
                                    <option value="${dishDescription}"></option>
                                </c:forEach>
                            </datalist>
                        </div>

                        <div class="form-group">
                            <label for="cost" class="col-form-label"><spring:message code="dish.cost"/></label>
                            <input type="number" class="form-control" id="cost" name="cost" step="0.01"
                                   placeholder="<spring:message code="dish.cost"/>">
                        </div>

                        <div class="form-group">
                            <label for="menuDate" class="col-form-label"><spring:message code="dish.date"/></label>
                            <input class="form-control" id="menuDate" name="menuDate"
                                   placeholder="<spring:message code="dish.date"/>">
                        </div>

                        <input type="hidden" id="dishRestaurantId" name="restaurantId" value="${restaurantId}">
                   </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">
                        <span class="fa fa-close"></span>
                        <spring:message code="common.cancel"/>
                    </button>
                    <button type="button" class="btn btn-primary" onclick="save()">
                        <span class="fa fa-check"></span>
                        <spring:message code="common.save"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>
<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    $(document).ready(function() {
        getDishes(${restaurantId});
    });
</script>

</body>
<jsp:include page="fragments/i18n.jsp">
        <jsp:param name="page" value="dish"/>
</jsp:include>
</html>