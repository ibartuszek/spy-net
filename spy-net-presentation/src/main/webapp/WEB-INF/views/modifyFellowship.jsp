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
    <title>Modify Fellowship</title>
</head>
<body class="eighty-width my-centered">
<jsp:include page="header.jsp"/>
<main>
    <c:if test="${not empty saveMessage}">
        <div class="input-group">
            <div class="form-control">${saveMessage}</div>
        </div>
    </c:if>
    <form:form class="eighty-width my-centered" modelAttribute="fellowshipRequest" action="postModifyFellowshipData.html">
        <div class="row my-centered my-button-row">
            <div class="form-inline col-sm-2">
                <button type="submit" class="btn btn-dark my-centered">Modify fellowship</button>
            </div>
            <div class="form-inline col-sm-2">
                <button type="button" class="btn btn-dark my-centered">
                    <a href="<c:url value="/fellowships"/>">Cancel</a>
                </button>
            </div>
        </div>
        <div class="input-group mb-2">
            <span class="input-group-addon input-group-text">House's name</span>
            <form:select path="house1" items="${houseNameList}"
                         name="house1" class="form-control" disabled="true"/>
        </div>
        <div class="input-group mb-2">
            <span class="input-group-addon input-group-text">House's name</span>
            <form:select path="house2" items="${houseNameList}"
                         name="house2" class="form-control" disabled="true"/>
        </div>
        <div class="input-group mb-2">
            <span class="input-group-addon input-group-text">Fellowship's begin</span>
            <form:input path="begin" type="text" name="name" class="form-control" disabled="true"/>
        </div>
        <div class="input-group mb-2">
            <span class="input-group-addon input-group-text">Fellowship's end</span>
            <form:input path="end" type="text" name="name" class="form-control"/>
        </div>
        <form:errors path="end" element="div" cssClass="alert" class="my-alert"/>
        <form:hidden path="house1" />
        <form:hidden path="house2" />
        <form:hidden path="begin" />
    </form:form>
</main>
<footer></footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
