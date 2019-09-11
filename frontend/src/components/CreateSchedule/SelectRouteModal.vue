<template>
  <v-layout justify-center>
    <v-dialog v-model="isOpen" persistent width="800px">
      <v-card>
        <v-card-title class="headline">경로 찾기</v-card-title>
        <v-card-text>
          <RouteMap :mapEvent="event" @sendRoute="saveRoute" @noResult="isNoResult" />
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="sendClose">취소</v-btn>
          <v-btn color="green darken-1" text @click="save">저장</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
import RouteMap from "./routeMap.vue";
export default {
  name: "SelectRouteModal",
  components: {
    RouteMap
  },
  props: {
    isOpen: false,
    event: null
  },
  data() {
    return {
      route: null,
      travelmode: null,
      panel: null,
      noResult: false
    };
  },
  methods: {
    sendClose() {
      this.$emit("close");
    },
    save() {
      if (!this.noResult) {
        this.event.overview_path = this.route.overview_polyline;
        this.event.name = this.travelmode;
        this.event.color = "#F48FB1";
        this.event.panel = this.panel;
        this.$emit("save");
      } else {
        // window.alert("저장 할 경로가 없습니다.");
        this.$notify({
          group: "foo",
          title: "Error message",
          text: "저장할 경로가 없습니다.",
          type: "error",
          duration: 2000
        });
      }
      this.sendClose();
    },
    saveRoute(routeSend, travelmode, steps) {
      this.route = routeSend;
      this.travelmode = travelmode;
      this.panel = steps;
    },
    isNoResult(value) {
      this.noResult = value;
    }
  }
};
</script>
