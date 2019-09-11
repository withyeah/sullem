<template>
  <div>
    <div id="map" style="width: 1240px"></div>
  </div>
  <!-- <div>
    <div id="directionsPanel" style="float:left;width:30%;height 100%; display: inline;"></div>
  </div>-->
</template>

<script>
// import { log } from 'util'
/* eslint-disable */
export default {
  name: "MapTest",
  props: {
    mapEvents: null
  },
  data() {
    return {
      map: null,
      infoWindow: null,
      directionsService: null,
      directionsDisplay: null,
      markers: [],
      polylines: [],
      count: 0
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
    geolocation() {
      // Try HTML5 geolocation.
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          function(position) {
            var pos = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };
            map.setCenter(pos);
          },
          function() {
            handleLocationError(true, this.infoWindow, this.map.getCenter());
          }
        );
      } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, this.infoWindow, this.map.getCenter());
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
          this.polylines.push(polyline);
          polyline.setMap(this.map);
        }
      }
    },
    deleteRoute() {
      if (this.polylines.length > 0) {
        for (var i = 0; i < this.polylines.length; ++i) {
          this.polylines[i].setMap(null);
        }
        this.polylines = [];
      }
    }
  },
  mounted() {
    this.initMap();
    this.$emit("sendMap", this.map);
    this.drawRoute();
  },
  watch: {
    mapEvents: function() {
      if (this.count == 0) {
        this.map.setCenter(this.mapEvents[0].latlng);
        this.map.setZoom(13);
        this.count += 1;
      }
      this.deleteMarker();
      this.deleteRoute();
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
