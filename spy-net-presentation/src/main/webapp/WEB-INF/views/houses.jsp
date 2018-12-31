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
            <div class="form-inline col-sm-3">
                <button type="button" class="btn btn-dark my-centered">
                    <a href="<c:url value="/createHouse"/>">Create new house</a>
                </button>
            </div>
        </div>
    </div>
    <table class="table eighty-width my-centered">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th class="my-picture-column" scope="col">Crest</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${houseDataList}" var="houseData" varStatus="counter">
                <tr>
                    <th rowspan="2" scope="row">${counter.count}</th>
                    <th>House:</th>
                    <td>${houseData.name}</td>
                    <td rowspan="2">
                        <c:if test="${houseData.hasCrest}">
                            <figure class="my-figure">
                                <img src="<c:url value='${houseData.crestUrl}'/>">
                            </figure>
                        </c:if>
                        <c:if test="${not houseData.hasCrest}">
                            <figure class="my-figure">
                                <img src="../assets/images/crest.png">
                            </figure>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Slogan:</td>
                    <td>${houseData.slogan}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>
<jsp:include page="footer.jsp"/>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
