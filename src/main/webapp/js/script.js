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
        center: {lat: 49.210087, lng: 16.599346},
        zoom: 8
    });

    google.maps.event.addListenerOnce(map, 'idle', function () {
        infoWindow = new google.maps.InfoWindow({
            maxWidth: 300
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

    var content = document.createElement("div");
    var p = document.createElement("p");
    p.textContent = item.message;

    content.appendChild(p);

    var sidePanelButton = document.createElement("a");
    sidePanelButton.href = "#";
    sidePanelButton.textContent = "Detail";
    sidePanelButton.addEventListener('click', function () {
        openSidePanel(item);
    });

    content.appendChild(sidePanelButton);

    marker.addListener('click', function () {
        infoWindow.close();
        infoWindow.setContent(content);
        infoWindow.open(map, marker);
    });
}

function openSidePanel(item) {
    var $sidePanel = $('#sidePanelButton');
    var $description = $('#side-panel-desc');
    var $weather = $('#side-panel-weather');
    var $statistics = $('#side-panel-stats')

    $sidePanel.sideNav({
        menuWidth: 450,
        draggable: false
    });

    $description.empty();
    $weather.empty();
    $statistics.empty();

    $description.append("<p style='line-height: 126%;'>" + item.message + "</p>");
    $weather.append("<p>Test weather</p>");
    $statistics.append("<p>Test statistics</p>");

    $sidePanel.sideNav('show');
}

function addMarkers(items) {
    for (var i = 0; i < items.length; ++i) {
        addMarker(items[i]);
    }
}
