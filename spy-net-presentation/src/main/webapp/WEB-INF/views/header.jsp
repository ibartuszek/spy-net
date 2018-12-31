<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spy-net-Header</title>
</head>
<body>
<header>
    <div class="jumbotron">
        <h1 class="display-4 text-right"><kbd>Spy-net</kbd></h1>
        <hr class="my-4">
    </div>
    <nav id="navBar" class="navbar navbar-expand-lg navbar-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse navbar-dark" id="navbarText">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Menu
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="<c:url value='/home' />">Home</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="<c:url value='/houses' />">List of the houses</a>
                        <a class="dropdown-item" href="<c:url value='/createHouse' />">Create new house</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="<c:url value='/characters' />">List of characters</a>
                        <a class="dropdown-item" href="<c:url value='/createCharacter' />">Create new character</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="<c:url value='/fellowships' />">List of fellowships</a>
                        <a class="dropdown-item" href="<c:url value='/createFellowship' />">Create fellowship</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownHelpLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Help
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item disabled" href="#">Help</a>
                        <a class="dropdown-item disabled" href="#">Contact</a>
                    </div>
                </li>
            </ul>
            <span class="navbar-text"></span>
        </div>
    </nav>
</header>
</body>
</html>
