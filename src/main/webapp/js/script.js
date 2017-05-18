/**
 * Created by robert-ntb on 5/11/17.
 */
var map;
var markerLayer;

$(document).ready(function () {
    initMap();
    addMarker(map.getCenter());
});

function initMap() {
    var center = SMap.Coords.fromWGS84(16.599346, 49.210087);
    map = new SMap(JAK.gel("map"), center, 10);
    map.addDefaultLayer(SMap.DEF_BASE).enable();
    map.addDefaultControls();

    markerLayer = new SMap.Layer.Marker();
    map.addLayer(markerLayer);
    markerLayer.enable();
}

function addMarker(center) {
    var c = new SMap.Card();
    c.setSize(400, 300);

    c.getHeader().innerHTML = "<h5>Report</h5>";
    c.getBody().innerHTML = "<a href='#' onclick='openSidePanel()'>Open</a>";

    var options = {};
    var marker = new SMap.Marker(center, "myMarker", options);
    marker.decorate(SMap.Marker.Feature.Card, c);
    markerLayer.addMarker(marker);
}

function openSidePanel() {
    var $sidePanel = $('#sidePanelButton');
    var $description = $('#side-panel-desc');
    var $weather = $('#side-panel-weather');
    var $statistics = $('#side-panel-stats')

    $sidePanel.sideNav({
        menuWidth: 450,
        draggable: false
    });

    $description.empty();
    $weather.empty()
    $statistics.empty();

    $description.append("<p>Test description</p>");
    $weather.append("<p>Test weather</p>");
    $statistics.append("<p>Test statistics</p>");

    $sidePanel.sideNav('show');
}