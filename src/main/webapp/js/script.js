document.addEventListener('DOMContentLoaded', function () {
    if (document.querySelectorAll('#map').length > 0) {
        if (document.querySelector('html').lang)
            lang = document.querySelector('html').lang;
        else
            lang = 'en';

        var google_js = document.createElement('script');
        google_js.type = 'text/javascript';
        google_js.src = 'https://maps.googleapis.com/maps/api/js?callback=initMap&key=AIzaSyDKKo1oPPy6FcBJXMaPhd_iIkIzrzRtCu8&language=' + lang;

        document.getElementsByTagName('head')[0].appendChild(google_js);
    }
});

var map;
var infoWindow = null;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 49.8175, lng: 15.4730},
        zoom: 8
    });

    google.maps.event.addListenerOnce(map, 'idle', function () {
        infoWindow = new google.maps.InfoWindow({
            maxWidth: 350
        });

        addMarkers(items);
    });
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
    // TODO create statistics content
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

function addMarkers(items) {
    for (var i = 0; i < items.length; ++i) {
        addMarker(items[i]);
    }
}
