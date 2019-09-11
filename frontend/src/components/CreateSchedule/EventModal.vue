<template>
  <v-layout justify-center>
    <v-dialog v-model="dialog" persistent max-width="350">
      <template v-slot:activator="{ on }">
        <v-icon color="#4DB6AC" v-on="on">fa-plus-circle</v-icon>
      </template>
      <v-card>
        <v-card-title class="headline">{{ SpecificLocation.name }}</v-card-title>
        <v-divider />
        <v-card-text>
          <v-layout>
            <p>{{ head }} {{ selectedHour }}시 {{ selectedMin }}분 부터</p>
          </v-layout>
          <v-layout align-center justify-space-between>
            <v-icon @click="duration > 30 ? duration -= 30 : duration">fa-minus</v-icon>
            <p class="teal--text text--lighten-2">{{ duration }}분</p>
            <p>동안</p>
            <v-icon @click="duration += 30">fa-plus</v-icon>
          </v-layout>
          <v-layout>
            <v-select v-model="selectedHour" :items="hour" placeholder="시"></v-select>
            <v-select v-model="selectedMin" :items="min" placeholder="분"></v-select>
          </v-layout>
        </v-card-text>
        <!-- <v-card-text>{{ SpecificLocation.place_id }}</v-card-text>
        <v-card-text>lat: {{ SpecificLocation.geometry.location.lat() }}</v-card-text>
        <v-card-text>lng: {{ SpecificLocation.geometry.location.lng() }}</v-card-text>-->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="#EF5350" text @click="resetEvents">취소</v-btn>
          <v-btn color="#26A69A" text @click="addEvents">추가</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
import { log } from "util";
export default {
  props: {
    SpecificLocation: null,
    events: null,
    head: null
  },
  data() {
    return {
      dialog: false,
      hour: [],
      min: ["00", "30"],
      duration: 30,
      selectedHour: null,
      selectedMin: null
    };
  },
  methods: {
    addEvents() {
      if (this.selectedHour == null || this.selectedMin == null) {
        // alert("입력정보를 확인하세요.");
        this.$notify({
          group: "foo",
          title: "Error message",
          text: "입력정보를 확인하세요.",
          type: "error",
          duration: 2000
        });
      } else {
        var startTime = new Date(
          this.head +
            " " +
            this.selectedHour +
            ":" +
            this.selectedMin +
            ":" +
            "00"
        );
        var endTime = startTime;
        endTime.setMinutes(endTime.getMinutes() + this.duration);
        var endYear = endTime.getFullYear();
        var endMonth = "" + (endTime.getMonth() + 1);
        var endDay = "" + endTime.getDate();
        var endHour = "" + endTime.getHours();
        var endMin = "" + endTime.getMinutes();
        if (endMonth.length < 2) endMonth = "0" + endMonth;
        if (endDay.length < 2) endDay = "0" + endDay;
        if (endHour.length < 2) endHour = "0" + endHour;
        if (endMin.length < 2) endMin = "0" + endMin;
        endTime =
          endYear +
          "-" +
          endMonth +
          "-" +
          endDay +
          " " +
          endHour +
          ":" +
          endMin;
        startTime =
          this.head + " " + this.selectedHour + ":" + this.selectedMin;

        var newEvent = {
          name: this.SpecificLocation.name,
          details: "없음.",
          start: startTime,
          end: endTime,
          color: "#80CBC4",
          latlng: {
            lat: this.SpecificLocation.geometry.location.lat(),
            lng: this.SpecificLocation.geometry.location.lng()
          },
          imageUrl: this.SpecificLocation.photos[0].getUrl(),
          category: this.SpecificLocation.types,
          rating: this.SpecificLocation.rating,
          address: this.SpecificLocation.formatted_address,

          type: "location"
        };

        var i;
        for (i = 0; i < this.events.length; ++i) {
          if (this.events[i].start > newEvent.start) {
            break;
          }
        }
        if (i > 0 && i < this.events.length) {
          if (
            (this.events[i - 1].end > newEvent.start &&
              this.events[i - 1].type == "location") ||
            (this.events[i].start < newEvent.end &&
              this.events[i].type == "location")
          ) {
            // alert("입력한 시간 내에 이미 다른 스케줄이 존재합니다.");
            this.$notify({
              group: "foo",
              title: "Error message",
              text: "입력한 시간 내에 이미 다른 스케줄이 존재합니다.",
              type: "error",
              duration: 2000
            });
            return;
          }
        } else if (i == 0 && this.events.length > 0) {
          if (
            this.events[i].start < newEvent.end &&
            this.events[i].type == "location"
          ) {
            // alert("입력한 시간 내에 이미 다른 스케줄이 존재합니다.");
            this.$notify({
              group: "foo",
              title: "Error message",
              text: "입력한 시간 내에 이미 다른 스케줄이 존재합니다.",
              type: "error",
              duration: 2000
            });
            return;
          }
        } else if (i == this.events.length && this.events.length > 0) {
          if (
            this.events[i - 1].end > newEvent.start &&
            this.events[i - 1].type == "location"
          ) {
            // alert("입력한 시간 내에 이미 다른 스케줄이 존재합니다.");
            this.$notify({
              group: "foo",
              title: "Error message",
              text: "입력한 시간 내에 이미 다른 스케줄이 존재합니다.",
              type: "error",
              duration: 2000
            });
            return;
          }
        }

        // if (i != 0 && this.events[i].start == newEvent.start) {
        //   alert("동일한 시간대에 스케줄이 존재합니다.");
        // }
        this.$emit("setCenter", newEvent.latlng);
        // To do
        // 1. 새로운 이벤트의 출발시간이 이전 이벤트의 끝나는 시간과 같을 경우 중간 경로 아예 삭제
        // 2. 새로운 이벤트의 끝나는 시간이 이후 이벤트의 시작 시간과 같을 경우 중간 경로 아예 삭제
        // 3. 상자 크기 이쁘게
        // 4. 상자 보더 관리하기.
        this.events.splice(i, 0, newEvent);
        if (i > 0) {
          if (this.events[i - 1].type == "route") {
            this.events[i - 1].end = this.events[i].start;
            this.events[i - 1].overview_path = null;
            this.events[i - 1].destination = this.events[i].latlng;
          } else {
            this.addRoute(i++);
          }
        }

        if (i + 1 < this.events.length) {
          if (this.events[i + 1].type == "route") {
            this.events[i + 1].start = this.events[i].end;
            this.events[i + 1].overview_path = null;
            this.events[i + 1].origin = this.events[i].latlng;
          } else {
            this.addRoute(i + 1);
          }
        }
        this.resetEvents();
      }
    },
    resetEvents() {
      this.dialog = false;
      this.selectedHour = null;
      this.selectedMin = null;
      this.duration = 30;
    },
    addRoute(idx) {
      var newRoute = {
        name: "길찾기",
        origin: this.events[idx - 1].latlng,
        destination: this.events[idx].latlng,
        overview_path: null,
        panel: null,
        start: this.events[idx - 1].end,
        end: this.events[idx].start,
        color: "transparent",
        latlng: null,
        type: "route"
      };
      this.events.splice(idx, 0, newRoute);
    }
  },
  watch: {},
  created() {
    for (var i = 0; i < 24; ++i) {
      if (i < 10) this.hour.push("0" + i);
      else this.hour.push(i);
    }
  }
};
</script>
