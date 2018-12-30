<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="forom" uri="http://www.springframework.org/tags/form" %>
        <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<c:url value='/assets/reset.css'/>" type="text/css" media="all">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value='/assets/style.css'/>" type="text/css" media="all">
    <title>Houses</title>
</head>
<body class="eighty-width my-centered">
<jsp:include page="header.jsp"/>
<main>
    <div class="eighty-width my-centered">
        <div class="row my-centered my-button-row">
            <div class="form-inline col-sm-3 mr-auto">
                <button type="button" class="btn btn-dark my-centered">
                    <a href="<c:url value="/createFellowship"/>">Create new fellowship</a>
                </button>
            </div>
        </div>
    </div>
    <table class="table eighty-width my-centered">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col">House</th>
            <th scope="col">House</th>
            <th scope="col">Begin of fellowship</th>
            <th scope="col">End of fellowship</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${fellowshipDataList}" var="fellowshipData" varStatus="counter">
                <tr>
                    <c:if test="${not empty fellowshipData.fellowshipUrl}">
                        <th scope="col"><a href="<c:url value='${fellowshipData.fellowshipUrl}'/>">${counter.count}</a></th>
                    </c:if>
                    <c:if test="${empty fellowshipData.fellowshipUrl}">
                        <th scope="col">${counter.count}</th>
                    </c:if>
                    <td scope="col">${fellowshipData.house1}</td>
                    <td scope="col">${fellowshipData.house2}</td>
                    <td scope="col">${fellowshipData.begin}</td>
                    <td scope="col">${fellowshipData.end}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>
<footer></footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
