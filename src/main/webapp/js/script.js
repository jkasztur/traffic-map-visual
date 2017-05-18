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
    /* Šířka, výška */
    c.getHeader().innerHTML = "<h5>Report</h5>";
    c.getBody().innerHTML = "<a href='#' onclick='openSidePanel()'>Open</a>";

    var options = {};
    var marker = new SMap.Marker(center, "myMarker", options);
    marker.decorate(SMap.Marker.Feature.Card, c);
    markerLayer.addMarker(marker);
}

function openSidePanel() {
    $('#sidePanelButton').sideNav({
        menuWidth: 450,
        draggable: false
    });

    $('#side-panel-desc').empty();
    $('#side-panel-weather').empty()
    $('#side-panel-stats').empty();

    $('#side-panel-desc').append("<p>Test description</p>");
    $('#side-panel-weather').append("<p>Test weather</p>");
    $('#side-panel-stats').append("<p>Test statistics</p>");

    $('#sidePanelButton').sideNav('show');
}