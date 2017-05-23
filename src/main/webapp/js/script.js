document.addEventListener('DOMContentLoaded', function () {
    if (document.querySelectorAll('#map').length > 0) {
        if (document.querySelector('html').lang)
            lang = document.querySelector('html').lang;
        else
            lang = 'en';

        var google_js = document.createElement('script');
        google_js.type = 'text/javascript';
        google_js.src = 'https://maps.googleapis.com/maps/api/js?callback=initMap&key=AIzaSyDKKo1oPPy6FcBJXMaPhd_iIkIzrzRtCu8&language=' + lang;
        ;
        document.getElementsByTagName('head')[0].appendChild(google_js);
    }
});

var map;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 49.210087, lng: 16.599346},
        zoom: 8
    });
}

// function addMarker(item) {
//     var c = new SMap.Card();
//     c.setSize(400, 300);
//     var center = SMap.Coords.fromWGS84(item.start.longitude, item.start.latitude);
//
//     c.getHeader().innerHTML = "<h5>" + item.start.longitude + ", " + item.start.latitude + "</h5>";
//     var sidePanelButton = document.createElement("a");
//     sidePanelButton.href = "#";
//     sidePanelButton.textContent = "Open";
//     sidePanelButton.addEventListener('click', function () {
//         openSidePanel(item);
//     });
//     c.getBody().appendChild(sidePanelButton);
//
//     var options = {};
//     var marker = new SMap.Marker(center, "myMarker", options);
//     marker.decorate(SMap.Marker.Feature.Card, c);
//     markerLayer.addMarker(marker);
// }
//
// function openSidePanel(item) {
//     var $sidePanel = $('#sidePanelButton');
//     var $description = $('#side-panel-desc');
//     var $weather = $('#side-panel-weather');
//     var $statistics = $('#side-panel-stats')
//
//     $sidePanel.sideNav({
//         menuWidth: 450,
//         draggable: false
//     });
//
//     $description.empty();
//     $weather.empty()
//     $statistics.empty();
//
//     $description.append("<p style='margin: 0'>" + item.message + "</p>");
//     $weather.append("<p>Test weather</p>");
//     $statistics.append("<p>Test statistics</p>");
//
//     $sidePanel.sideNav('show');
// }
//
// function addMarkers(items) {
//     for (var i = 0; i < items.length; ++i) {
//         console.log(items[i]);
//         setTimeout(function () {
//             // addMarker(items[i]);
//         }, i * 200);
//     }
// }
