var zoomLevel = 7
var x = 45.991
var y = 25.154
var map = L.map('mapid').setView([x, y], zoomLevel);

L.tileLayer(
    'https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Base map and data from OpenStreetMap and OpenStreetMap Foundation'
   }
    ).addTo(map);

//map.on("zoom", onZoom)
//function onZoom(event) {
//    alert(event.center)
//}



var markers = L.markerClusterGroup();
markers.addLayer(L.marker([45.991, 25.154]));
markers.addLayer(L.marker([45.9, 25.154]));
markers.addLayer(L.marker([45.9, 25.1]));

markers.addLayer(L.marker([45.7099956, 27.1976632]));
markers.addLayer(L.marker([45.7099956, 27.1976632]));

markers.addLayer(L.marker([46.2496524,26.76234055]));
map.addLayer(markers);

//var focsani = L.markerClusterGroup();
//markers.addLayer(L.marker([45.7099956, 27.1976632, ]));
//markers.addLayer(L.marker([45.3894622, 27.4481841, ]));
//map.addLayer(focsani);
