<template>
  <div>
    <div id="map" style="width: 100%"></div>
  </div>
</template>

<script>
// import { log } from 'util'
/* eslint-disable */
export default {
  name: "DetailMap",
  props: {
    mapEvents: null
  },
  data() {
    return {
      map: null,
      infoWindow: null,
      directionsService: null,
      directionsDisplay: null,
      markers: []
    };
  },
  methods: {
    initMap() {
      this.directionsService = new google.maps.DirectionsService();
      this.directionsDisplay = new google.maps.DirectionsRenderer();
      this.map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 0, lng: 0 },
        zoom: 2,
        gestureHandling: "cooperative"
      });
      this.$nextTick(() => {
        this.directionsDisplay.setMap(this.map);
      });
      this.infoWindow = new google.maps.InfoWindow();
    },
    handleLocationError(browserHasGeolocation, infoWindow, pos) {
      infoWindow.setPosition(pos);
      infoWindow.setContent(
        browserHasGeolocation
          ? "Error: The Geolocation service failed."
          : "Error: Your browser doesn't support geolocation."
      );
      infoWindow.open(this.map);
    },
    displayRoute(directionsService, directionsDisplay) {
      directionsService.route(
        {
          origin: "Jeju+International+Airport",
          destination: "Hyupjae+Beach",
          travelMode: "TRANSIT",
          provideRouteAlternatives: true
        },
        function(response, status) {
          if (status === "OK") {
            console.log(response);
            directionsDisplay.setDirections(response);
          } else {
            window.alert("Directions request failed due to " + status);
          }
        }
      );
    },
    addMarker() {
      var index = 1;

      for (var i in this.mapEvents) {
        if (this.mapEvents[i]["type"] == "location") {
          var marker = new google.maps.Marker({
            position: this.mapEvents[i]["latlng"],
            map: this.map,
            label: String(index++)
          });
          this.markers.push(marker);
        }
      }
    },
    deleteMarker() {
      this.setMapOnAll(null);
      this.markers = [];
    },
    setMapOnAll(map) {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(map);
      }
    },
    drawRoute() {
      for (var i in this.mapEvents) {
        if (
          this.mapEvents[i]["type"] == "route" &&
          this.mapEvents[i]["overview_path"]
        ) {
          var path = google.maps.geometry.encoding.decodePath(
            this.mapEvents[i]["overview_path"]
          );

          var polyline = new google.maps.Polyline({
            path: path,
            strokeColor: "#F06292",
            strokeOpacity: 1,
            strokeWeight: 3,
            fillColor: "#F06292",
            fillOpacity: 1,
            map: this.map
          });
          polyline.setMap(this.map);
        }
      }
    }
  },
  mounted() {
    this.initMap();
    this.addMarker();
    this.drawRoute();
    this.map.setCenter(this.mapEvents[0].latlng);
    this.map.setZoom(13);
    // console.log(this.mapEvents[0][0].latlng);
  },
  watch: {
    mapEvents: function() {
      this.deleteMarker();
      this.addMarker();
      this.drawRoute();
    }
  }
};
</script>
<style scoped>
#map {
  height: 500px;
  width: 100%;
}
</style>
