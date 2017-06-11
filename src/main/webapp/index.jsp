<%@ page import="cz.muni.fi.pb138.trafficmap.utils.TrafficReportsBuilder" %>
<%@ page import="cz.muni.fi.pb138.trafficmap.utils.StatisticsBuilder" %>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8"/>

    <link type="text/css" rel="stylesheet" href="css/style.css"/>

    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>

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
                    <form onsubmit="doSearch();return false">
                        <div class="input-field">
                            <input id="search_field" type="search" placeholder="Search on map..." required>
                            <label class="label-icon search-icons" for="search_field">
                                <button id="submit-button" type="submit">
                                    <i class="material-icons">search</i>
                                </button>
                            </label>
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
            <div id="side-head" class="light-blue side-head">
                <h4>Traffic Report</h4>
            </div>
        </li>
        <li>
            <div class="side-body">
                <h5>Description</h5>
                <div id="side-panel-desc">
                    <p id="description"></p>
                </div>
            </div>
        </li>
        <li>
            <div class="divider"></div>
        </li>
        <li>
            <div class="side-body">
                <h5>Location</h5>
                <div id="side-panel-location">
                    <div class="side-panel-block">
                        <h6>Primary Localization</h6>
                        <p id="prim-localization"></p>
                    </div>
                    <div class="side-panel-block  side-panel-location-inline-block">
                        <h6>District</h6>
                        <p id="district"></p>
                    </div>
                    <div class="side-panel-block side-panel-location-inline-block">
                        <h6>Region</h6>
                        <p id="region"></p>
                    </div>
                </div>
            </div>
        </li>
        <li>
            <div class="divider"></div>
        </li>
        <li>
            <div class="side-body">
                <h5>Time</h5>
                <div id="side-panel-time">
                    <div class="side-panel-block">
                        <div id="side-panel-time-from">
                            <h6>From</h6>
                            <p id="time-from"></p>
                        </div>
                        <div id="side-panel-time-to">
                            <h6>To</h6>
                            <p id="time-to"></p>
                        </div>
                    </div>
                    <div id="side-panel-time-updated" class="side-panel-block">
                        <h6>Updated</h6>
                        <p id="time-updated"></p>
                    </div>
                </div>
            </div>
        </li>
        <li>
            <div class="divider"></div>
        </li>
        <li>
            <div class="side-body">
                <h5>Weather</h5>
                <div id="side-panel-weather">
                    <div id="weather-prim-info">
                        <img src="" id="weather-icon" alt="Icon depicting current weather." width="64">
                        <div id="temperature">
                            <h5 id="current-temp"></h5>
                            <p id="max-min-temp"></p>
                        </div>
                    </div>
                    <div id="weather-sec-info">
                        <ul>
                            <li>
                                <span class="icon-humidity"></span>
                                <span id="humidity"></span>
                            </li>
                            <li>
                                <span class="icon-wind"></span>
                                <span id="wind"></span>
                            </li>
                            <li>
                                <span class="icon-pressure"></span>
                                <span id="pressure"></span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </li>
        <li>
            <div class="divider"></div>
        </li>
        <li>
            <div class="side-body">
                <h5>Statistics</h5>
                <div id="side-panel-stats"></div>
            </div>
        </li>
    </ul>
    <%-- Button for activating the side panel --%>
    <button data-activates="slide-out" id="sidePanelButton">sidePanelButton</button>

    <div id="map"></div>

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
<script type="text/javascript" src="js/moment.js"></script>
<script type="text/javascript" src="js/script.js" defer></script>

<% String items = TrafficReportsBuilder.getJSONReports();
String regions = StatisticsBuilder.getJSONRegions();
String districts = StatisticsBuilder.getJSONDistricts();
%>
<script type="text/javascript">
    var items = (<%= items %>);
</script>

<%--TODO no internet connection--%>
</body>
</html>
