<html>
<head>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>

    <link type="text/css" rel="stylesheet" href="css/style.css"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <title>Traffic Map Visual</title>
</head>
<body>

<header>
    <nav>
        <div class="nav-wrapper light-blue">
            <div class="row">
                <div class="col l3 hide-on-med-and-down">
                    <a href="#" class="brand-logo">Traffic Map Visual</a>
                </div>
                <div class="col s12 l5">
                    <form>
                        <div class="input-field">
                            <input id="search" type="search" placeholder="Search on map..." required>
                            <label class="label-icon search-icons" for="search"><i
                                    class="material-icons">search</i></label>
                            <i class="material-icons search-icons">close</i>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </nav>
</header>

<main>
    <ul id="slide-out" class="side-nav ">
        <li>
            <div id="side-head" class="light-blue side-head" style="padding: 16px; color: white;">
                <h4>Traffic Report</h4>
            </div>
        </li>
        <li>
            <div class="side-body" style="padding-left: 32px;">
                <h5>Description</h5>
            </div>
        </li>
        <li>
            <div class="divider"></div>
        </li>
        <li>
            <div class="side-body" style="padding-left: 32px;">
                <h5>Weather</h5>
            </div>
        </li>
        <li>
            <div class="divider"></div>
        </li>
        <li>
            <div class="side-body" style="padding-left: 32px;">
                <h5>Statistics</h5>
            </div>
        </li>
    </ul>

    <div id="map">

</main>

<footer class="page-footer light-blue">
    <div class="row">
        <div class="footer-copyright">
            <div class="col s11">
                <p>&#x24B8; 2017 Copyright</p>
            </div>
        </div>
    </div>
</footer>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>

</body>
</html>
