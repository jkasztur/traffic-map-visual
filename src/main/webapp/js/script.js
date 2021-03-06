var map;
var infoWindow = null;
var updated;
var placesService;

document.addEventListener('DOMContentLoaded', function () {
    if (document.querySelectorAll('#map').length > 0) {
        if (document.querySelector('html').lang)
            lang = document.querySelector('html').lang;
        else
            lang = 'en';

        var google_js = document.createElement('script');
        google_js.type = 'text/javascript';
        google_js.src = 'https://maps.googleapis.com/maps/api/js?callback=initMap&key=AIzaSyDKKo1oPPy6FcBJXMaPhd_iIkIzrzRtCu8&libraries=places&language=' + lang;

        document.getElementsByTagName('head')[0].appendChild(google_js);

        updated = moment().valueOf();

        // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
        $('.modal').modal();

        createStatisticsTable();
    }
});

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 49.8175, lng: 15.4730},
        zoom: 8
    });

    google.maps.event.addListenerOnce(map, 'idle', function () {
        infoWindow = new google.maps.InfoWindow({
            maxWidth: 350
        });

        addMarkers(reports);
    });
    placesService = new google.maps.places.PlacesService(map);
}

// Centers map to given location
function centerMap(location) {
    infoWindow.setPosition(location);
    infoWindow.setContent('Location found.');
    infoWindow.open(map);
    map.setCenter(location);
}

// Function called on submission of search form
function doSearch() {
    var stringRequest = document.getElementById("search-field").value;
    var request = {
        location: map.getCenter(),
        query: stringRequest
    };

    placesService.textSearch(request, centerCallback);
}

// Function called by google.maps.places.PlacesService service
function centerCallback(results, status) {
    if (status !== google.maps.places.PlacesServiceStatus.OK) {
        Materialize.toast('Search not successful.', 2000)
        return;
    }

    if (results.length === 0) {
        Materialize.toast('Nothing found.', 2000)
        return;
    }
    centerMap(results[0].geometry.location)
}

function addMarkers(reports) {
    for (var i = 0; i < reports.length; ++i) {
        addMarker(reports[i]);
    }
}

function addMarker(item) {
    var marker = new google.maps.Marker({
        position: item.start,
        map: map,
        title: "Traffic report"
    });

    var content = createInfoWindowContent(item);

    marker.addListener('click', function () {
        infoWindow.close();
        infoWindow.setContent(content);
        infoWindow.open(map, marker);
    });
}

function createInfoWindowContent(item) {
    var content = document.createElement("div");
    content.setAttribute("id", "info-window");

    content.innerHTML =
        "<div id='info-header'>" +
        "<div id='info-header-title'>" +
        "<h5><b>Info</b></h5>" +
        "</div>" +
        "<div id='info-header-temp'>" +
        "<div id='info-header-temp-icon'>" +
        "<img src='" + item.localWeather.weatherIcon + "' alt='Icon depicting current weather.'/>" +
        "</div><div id='info-header-current-temp'>" +
        "<p>" + Math.round(item.localWeather.currentTemp) + " &deg;C</p>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "<div class='divider'></div>" +
        "<table id='info-table'>" +
        "<tr>" +
        "<th>From</th>" +
        "<td>" + formatDate(item.activeFrom) + "</td>" +
        "</tr>" +
        "<tr>" +
        "<th>To</th>" +
        "<td>" + formatDate(item.activeTo) + "</td>" +
        "</tr>" +
        "<tr>" +
        "<th>Where</th>" +
        "<td>" + parseText(item.primaryLocalization) + "</td>" +
        "</tr>" +
        "<tr>" +
        "<th>What</th>" +
        "<td>" + parseText(item.infoText.split(";")[0]) + "</td>" +
        "</tr>" +
        "</table>" +
        "<div class='divider'></div>";

    var sidePanelBlock = document.createElement("div");
    sidePanelBlock.setAttribute("id", "info-footer");

    var sidePanelButton = document.createElement("a");
    sidePanelButton.href = "#";
    sidePanelButton.textContent = "Show Details";
    sidePanelButton.addEventListener('click', function () {
        openSidePanel(item);
    });

    sidePanelBlock.appendChild(sidePanelButton);
    content.appendChild(sidePanelBlock);

    return content;
}

function formatDate(date) {
    return moment(date).format("DD.MM.YYYY HH:mm");
}

function openSidePanel(item) {
    var $sidePanel = $('#sidePanelButton');

    $sidePanel.sideNav({
        menuWidth: 450,
        draggable: false
    });

    createDescriptionContent(item);
    createLocationContent(item);
    createTimeContent(item);
    createWeatherContent(item);
    createStatisticsContent(item)

    $sidePanel.sideNav('show');
}

function createDescriptionContent(item) {
    $('#description').text(parseText(item.infoText));
}

function createLocationContent(item) {
    $('#prim-localization').text(parseText(item.primaryLocalization));
    $('#district').text(item.district);
    $('#region').text(item.region);
}

function createTimeContent(item) {
    $('#time-from').text(formatDate(item.activeFrom));
    $('#time-to').text(formatDate(item.activeTo));
    $('#time-updated').text(formatDate(updated));
}

function createWeatherContent(item) {
    $('#weather-icon').attr("src", item.localWeather.weatherIcon);
    $('#current-temp').html("<b>" + Math.round(item.localWeather.currentTemp) + " &deg;C</b>");
    $('#max-min-temp').html(Math.round(item.localWeather.maxTemp) + "&deg;/" +
        Math.round(item.localWeather.minTemp) + "&deg;");
    $('#humidity').text(item.localWeather.humidity + "%");
    $('#wind').html(item.localWeather.windSpeed +
        " m/s at " + item.localWeather.windDirection);
    $('#pressure').text(item.localWeather.pressure + " hPa");
}

function createStatisticsContent(item) {
    var regionStatistics = getStatisticsForRegion(item.region);
    var districtStatistics = getStatisticsForDistrict(item.district);

    if (regionStatistics !== null) {
        $('#stats-property-damage-reg').text(Math.round(regionStatistics.propertyDamage));
        $('#stats-drunk-driving-reg').text(regionStatistics.drunkDriving);
        $('#stats-slightly-injured-reg').text(regionStatistics.slightlyInjured);
        $('#stats-seriously-injured-reg').text(regionStatistics.seriouslyInjured);
        $('#stats-killed-persons-reg').text(regionStatistics.killedPersons);
        $('#stats-total-accidents-reg').text(regionStatistics.totalAccidents);
    } else {
        $('#stats-property-damage-reg').text('-');
        $('#stats-drunk-driving-reg').text('-');
        $('#stats-slightly-injured-reg').text('-');
        $('#stats-seriously-injured-reg').text('-');
        $('#stats-killed-persons-reg').text('-');
        $('#stats-total-accidents-reg').text('-');
    }

    if (districtStatistics !== null) {
        $('#stats-property-damage-dis').text(Math.round(districtStatistics.propertyDamage));
        $('#stats-drunk-driving-dis').text(districtStatistics.drunkDriving);
        $('#stats-slightly-injured-dis').text(districtStatistics.slightlyInjured);
        $('#stats-seriously-injured-dis').text(districtStatistics.seriouslyInjured);
        $('#stats-killed-persons-dis').text(districtStatistics.killedPersons);
        $('#stats-total-accidents-dis').text(districtStatistics.totalAccidents);
    } else {
        $('#stats-property-damage-dis').text('-');
        $('#stats-drunk-driving-dis').text('-');
        $('#stats-slightly-injured-dis').text('-');
        $('#stats-seriously-injured-dis').text('-');
        $('#stats-killed-persons-dis').text('-');
        $('#stats-total-accidents-dis').text('-');
    }
}

function parseText(text) {
    var splitText = text.split(";");
    var parsedText = "";

    for (var i = 0; i < splitText.length; ++i) {
        splitText[i] = splitText[i].trim();
        splitText[i] = splitText[i].charAt(0).toUpperCase() + splitText[i].substr(1);

        parsedText += splitText[i];
        if (parsedText.charAt(parsedText.length - 1) !== '.') {
            parsedText += ". ";
        }
    }

    return parsedText;
}

function getStatisticsForRegion(region) {
    if ("undefined" !== typeof regions) {
        for (var i = 0; i < regions.length; ++i) {
            if (regions[i].name === region) {
                return regions[i];
            }
        }

        return null;
    }
}

function getStatisticsForDistrict(district) {
    if ("undefined" !== typeof districts) {
        for (var i = 0; i < districts.length; ++i) {
            if (districts[i].name === district) {
                return districts[i];
            }
        }

        return null;
    }
}

function createStatisticsTable() {
    var $table = $('#stats-table');

    $table.append(createHeaderTableRow("Country"));
    $table.append(createDataTableRow(regions[regions.length - 1]));

    $table.append(createHeaderTableRow("Regions"));
    for (var i = 0; i < regions.length - 1; ++i) {
        $table.append(createDataTableRow(regions[i]));
    }

    $table.append(createHeaderTableRow("Districts"));
    for (var i = 0; i < districts.length; ++i) {
        $table.append(createDataTableRow(districts[i]));
    }
}

function createDataTableRow(data) {
    if (data !== null) {
        var tr = document.createElement("tr");
        tr.innerHTML = "<td>" + data.name + "</td>" +
            "<td>" + data.totalAccidents + "</td>" +
            "<td>" + data.drunkDriving + "</td>" +
            "<td>" + data.slightlyInjured + "</td>" +
            "<td>" + data.seriouslyInjured + "</td>" +
            "<td>" + data.killedPersons + "</td>" +
            "<td>" + Math.round(data.propertyDamage) + "</td>";

        return tr;
    }

    return null;
}

function createHeaderTableRow(name) {
    var tr = document.createElement("tr");
    tr.innerHTML = "<th>" + name + "</th>" +
        "<th>Total Accidents</th>" +
        "<th>Drunk Driving</th>" +
        "<th>Slightly Injured Persons</th>" +
        "<th>Seriously Injured Persons</th>" +
        "<th>Killed Persons</th>" +
        "<th>Material Damage in 1K of CZK</th>";

    return tr;
}
