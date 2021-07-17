var zoomLevel = 7
var x = 45.991
var y = 25.154
var map = L.map('mapid').setView([x, y], zoomLevel);

L.tileLayer(
    'https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Base map and data from OpenStreetMap and OpenStreetMap Foundation'
   }
    ).addTo(map);