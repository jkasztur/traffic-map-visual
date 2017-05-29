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
        "<h5>Description</h5>" +
        createDescriptionContent(item) +
        "<div class='divider'></div>" +
        "<img src='" + item.localWeather.weatherIcon + "' alt='Icon depicting current weather.'/>" +
        "<p>" + item.localWeather.currentTemp + " &deg;C</p>";

    var sidePanelButton = document.createElement("a");
    sidePanelButton.href = "#";
    sidePanelButton.textContent = "Detail";
    sidePanelButton.addEventListener('click', function () {
        openSidePanel(item);
    });

    content.appendChild(sidePanelButton);

    return content;
}

function openSidePanel(item) {
    var $sidePanel = $('#sidePanelButton');
    var $description = $('#side-panel-desc');
    var $statistics = $('#side-panel-stats')

    $sidePanel.sideNav({
        menuWidth: 450,
        draggable: false
    });

    createWeatherContent(item);

    $description.empty();
    $statistics.empty();

    $description.append(createDescriptionContent(item));
    $statistics.append(createStatisticsContent(item));

    $sidePanel.sideNav('show');
}

function createDescriptionContent(item) {
    var message = item.message.split(";");

    var description = "<p>";
    for (var i = 0; i < message.length; ++i) {
        message[i] = message[i].trim();
        message[i] = message[i].charAt(0).toUpperCase() + message[i].substr(1);
        if (message[i].charAt(message[i].length - 1) !== '.') {
            description += message[i] + ". ";
        }

    }
    description += "</p>";

    return description;
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
    var statistics = "";
    // TODO create statistics content
    return statistics;
}

function addMarkers(items) {
    for (var i = 0; i < items.length; ++i) {
        addMarker(items[i]);
    }
}
