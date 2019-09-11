<template>
  <v-layout fill-height>
    <v-flex>
      <v-sheet height="64">
        <v-toolbar flat color="white">
          <v-btn outlined class="mr-4" @click="setToday">First</v-btn>
          <v-btn fab text small @click="prev">이전</v-btn>
          <v-btn fab text small @click="next">이후</v-btn>
          <!-- <v-toolbar-title>{{ title }}</v-toolbar-title> -->
          <v-spacer></v-spacer>
        </v-toolbar>
      </v-sheet>
      <v-sheet height="600">
        <v-calendar
          interval-minutes="30"
          interval-count="48"
          ref="calendar"
          v-model="focus"
          color="#1DE9B6"
          event-text-color="black"
          :events="events"
          :event-color="getEventColor"
          :event-overlap-threshold="0"
          :event-margin-bottom="3"
          :now="start"
          :type="type"
          :end="end"
          @click:event="showEvent"
          @click:more="viewDay"
          @click:date="viewDay"
        ></v-calendar>
        <v-menu
          v-model="selectedOpen"
          :close-on-content-click="false"
          :activator="selectedElement"
          full-width
          offset-x
        >
          <v-card color="grey lighten-4" min-width="350px" flat>
            <v-toolbar :color="selectedEvent.color" dark>
              <v-btn @click="deleteRoute" icon>
                <v-icon>fa-trash-alt</v-icon>
              </v-btn>
              <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>
            <v-card-text>
              <StepsTimeLine v-if="selectedEvent.panel" :steps="selectedEvent.panel"></StepsTimeLine>
            </v-card-text>
            <v-card-actions>
              <v-btn text color="secondary" @click="selectedOpen = false">확인</v-btn>
            </v-card-actions>
          </v-card>
        </v-menu>
        <SelectRouteModal
          v-if="modalOpen"
          :isOpen="routeSelect"
          :event="selectedEvent"
          @close="close"
          @save="save"
        />
      </v-sheet>
    </v-flex>
  </v-layout>
</template>

<script>
import SelectRouteModal from "./SelectRouteModal.vue";
import StepsTimeLine from "./StepsTimeLine.vue";
import { log } from "util";

export default {
  components: {
    SelectRouteModal,
    StepsTimeLine
  },
  props: {
    startDay: null,
    events: null
  },
  data: () => ({
    focus: "2019-01-08",
    type: "4day",
    start: "2019-01-08",
    end: null,
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    routeSelect: false,
    modalOpen: true
  }),
  computed: {
    monthFormatter() {
      return this.$refs.calendar.getFormatter({
        timeZone: "UTC",
        month: "long"
      });
    }
  },
  methods: {
    viewDay({ date }) {
      this.focus = date;
      this.start = date;
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.start = this.startDay;
      this.focus = this.startDay;
    },
    prev() {
      var prev = new Date(this.start);
      prev.setDate(prev.getDate() - 1);
      var year = prev.getFullYear();
      var month = "" + (prev.getMonth() + 1);
      var day = "" + prev.getDate();
      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;
      prev = year + "-" + month + "-" + day;
      this.focus = prev;
      this.start = prev;
    },
    next() {
      var nextDay = new Date(this.start);
      nextDay.setDate(nextDay.getDate() + 1);
      var year = nextDay.getFullYear();
      var month = "" + (nextDay.getMonth() + 1);
      var day = "" + nextDay.getDate();
      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;
      nextDay = year + "-" + month + "-" + day;
      this.focus = nextDay;
      this.start = nextDay;
    },
    showEvent({ nativeEvent, event }) {
      this.selectedEvent = event;
      this.selectedElement = nativeEvent.target;
      if (event.name == "길찾기") {
        setTimeout(() => {
          this.routeSelect = true;
        }, 10);
      } else if (event.overview_path == null) {
        console.log("장소입니다.");
        // const open = () => {
        //   // this.selectedEvent = event;
        //   // this.selectedElement = nativeEvent.target;
        setTimeout(() => {
          this.selectedOpen = true;
        }, 10);
        // };
      } else {
        console.log("경로입니다.");
        console.log(this.selectedEvent.panel);
        setTimeout(() => {
          this.selectedOpen = true;
        }, 10);
      }

      // if (this.selectedOpen) {
      //   this.selectedOpen = false;
      //   setTimeout(open, 10);
      // } else {
      //   open();
      // }
      nativeEvent.stopPropagation();
    },
    // nth(d) {
    //   return d > 3 && d < 21
    //     ? "th"
    //     : ["th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"][d % 10];
    // },
    // send() {
    //   this.$emit("sendEvents", this.events);
    // },
    save() {
      this.events.push({});
      this.events.pop();
      this.close();
    },
    close() {
      this.routeSelect = false;
      this.modalOpen = false;
      this.$nextTick(() => {
        this.modalOpen = true;
      });
    },
    deleteRoute() {
      var deleteIndex = this.events.indexOf(this.selectedEvent);
      if (this.selectedEvent.type == "route") {
        var newRoute = {
          name: "길찾기",
          origin: this.events[deleteIndex].origin,
          destination: this.events[deleteIndex].destination,
          overview_path: null,
          panel: null,
          start: this.events[deleteIndex].start,
          end: this.events[deleteIndex].end,
          color: "transparent",
          latlng: null,
          type: "route"
        };
        this.events.splice(deleteIndex, 0, newRoute);
        this.events.splice(deleteIndex + 1, 1);
      } else {
        this.events.splice(deleteIndex, 1);
        if (this.events.length >= 1) {
          if (deleteIndex == 0) {
            this.events.splice(deleteIndex, 1);
          } else if (deleteIndex == this.events.length) {
            this.events.splice(deleteIndex - 1, 1);
          } else {
            this.events.splice(deleteIndex, 1);
            this.events[deleteIndex - 1].color = "transparent";
            this.events[deleteIndex - 1].name = "길찾기";
            this.events[deleteIndex - 1].panel = null;
            this.events[deleteIndex - 1].latlng = null;
            this.events[deleteIndex - 1].end = this.events[deleteIndex].start;
            this.events[deleteIndex - 1].overview_path = null;
            this.events[deleteIndex - 1].destination = this.events[
              deleteIndex
            ].latlng;
          }
        }
      }
    }
  },
  mounted() {
    this.start = this.startDay;
    this.setToday();
  },
  watch: {
    start: function() {
      this.$emit("changeHead", this.start);
    },
    startDay: function() {
      this.setToday();
    }
  }
};
</script>

<style scoped>
.pl-1 {
  color: black;
}
</style>
