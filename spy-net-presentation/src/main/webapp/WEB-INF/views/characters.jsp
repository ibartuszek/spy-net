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
    <title>Characters</title>
</head>
<body class="eighty-width my-centered">
<jsp:include page="header.jsp"/>
<main>
    <div class="eighty-width my-centered">
        <div class="row my-centered my-button-row">
            <div class="form-inline col-sm-3 mr-auto">
                <button type="button" class="btn btn-dark my-centered">
                    <a href="<c:url value="/createCharacter"/>">Create new character</a>
                </button>
            </div>
            <form class="form-inline col-sm-4" action="<c:url value='/characters'/>"  method="get">
                <input type="text" name="name" value="${listCharacterRequest.name}"/>
                <button class="btn btn-dark" type="submit">Search</button>
            </form>
        </div>
    </div>
    <table class="table eighty-width my-centered">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Number of army</th>
            <th scope="col">Status</th>
            <th scope="col">House</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${characterDataList}" var="characterData" varStatus="counter">
                <tr>
                    <c:if test="${not empty characterData.characterUrl}">
                        <th scope="col"><a href="<c:url value='${characterData.characterUrl}'/>">${counter.count}</a></th>
                    </c:if>
                    <c:if test="${empty characterData.characterUrl}">
                        <th scope="col">${counter.count}</th>
                    </c:if>
                    <td scope="col">${characterData.name}</td>
                    <td scope="col">${characterData.armySize}</td>
                    <td scope="col">${characterData.status}</td>
                    <td scope="col">${characterData.house}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>


        </tbody>
    </table>
</main>
<footer></footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
