<template>
  <v-layout justify-space-between>
    <div id="map" style="width: 600px"></div>
    <div>
      <div row>
        <v-btn @click="activeBtn = 'TRANSIT'">
          <v-icon :color="activeBtn === 'TRANSIT' ? '#1DE9B6' : ''">fa-bus</v-icon>
        </v-btn>
        <v-btn @click="activeBtn = 'DRIVING'">
          <v-icon :color="activeBtn === 'DRIVING' ? '#1DE9B6' : ''">fa-car</v-icon>
        </v-btn>
        <v-btn @click="activeBtn = 'WALKING'">
          <v-icon :color="activeBtn === 'WALKING' ? '#1DE9B6' : ''">fa-walking</v-icon>
        </v-btn>
      </div>
      <div
        id="directionsPanel"
        style="float:left;width:300px;height: 500px; display: inline; overflow: auto"
      ></div>
    </div>
  </v-layout>
</template>

<script>
export default {
  name: "RouteMap",
  props: {
    mapEvent: null
  },
  data() {
    return {
      map: null,
      infoWindow: null,
      directionsService: null,
      directionsDisplay: null,
      activeBtn: "TRANSIT"
    };
  },
  methods: {
    initMap() {
      this.directionsService = new google.maps.DirectionsService();
      this.directionsDisplay = new google.maps.DirectionsRenderer();
      this.map = new google.maps.Map(document.getElementById("map"), {
        center: this.mapEvent.origin,
        zoom: 10,
        gestureHandling: "cooperative"
      });
      this.$nextTick(() => {
        this.directionsDisplay.setMap(this.map);
      });
      this.infoWindow = new google.maps.InfoWindow();
    },
    displayRoute(directionsService, directionsDisplay) {
      var self = this;
      directionsService.route(
        {
          origin: this.mapEvent.origin,
          destination: this.mapEvent.destination,
          travelMode: this.activeBtn,
          provideRouteAlternatives: true
        },
        function(response, status) {
          if (status === "OK") {
            self.$emit("noResult", false);
            directionsDisplay.setDirections(response);
          } else {
            self.$emit("noResult", true);
            directionsDisplay.setDirections(response);
            // window.alert("Directions request failed due to " + status);
            this.$notify({
              group: "foo",
              title: "Error message",
              text: "경로정보가 없습니다.",
              type: "warn",
              duration: 2000
            });
          }
        }
      );
    }
  },
  mounted() {
    this.initMap();
    this.directionsDisplay.setPanel(document.getElementById("directionsPanel"));
    this.displayRoute(this.directionsService, this.directionsDisplay);
    // this.$emit("sendMap", this.map);
    var self = this;
    google.maps.event.addListener(
      this.directionsDisplay,
      "routeindex_changed",
      function() {
        //current routeIndex
        // console.log(this.getRouteIndex());
        //current route
        var route = this.getDirections().routes[this.getRouteIndex()];
        // console.log(route);
        // console.log(self.directionsDisplay.getPanel());
        // console.log(route.legs[0].steps.length);
        // console.log(route.legs[0].steps[0].travel_mode);

        var step = [];
        for (var i = 0; i < route.legs[0].steps.length; ++i) {
          step.push({
            distance: route.legs[0].steps[i].distance.text,
            duration: route.legs[0].steps[i].duration.text,
            travel_mode: route.legs[0].steps[i].travel_mode,
            instructions: route.legs[0].steps[i].instructions,
            icon:
              route.legs[0].steps[i].travel_mode === "TRANSIT"
                ? "fa-bus"
                : route.legs[0].steps[i].travel_mode === "WALKING"
                ? "fa-walking"
                : "fa-car"
          });
        }
        // console.log(step);

        self.$emit("sendRoute", route, self.activeBtn, step);
      }
    );
  },
  watch: {
    activeBtn: function() {
      this.displayRoute(this.directionsService, this.directionsDisplay);
    },
    mapEvent: function() {}
  }
};
</script>
<style scoped>
#map {
  height: 500px;
  width: 100%;
}
</style>
