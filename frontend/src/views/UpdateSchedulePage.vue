<template>
  <div>
    <ScheduleImage :coverImage="coverimageUrl" :title="title" :description="description" />
    <v-container>
      <v-layout>
        <GoogleMap :mapEvents="events" v-on:sendMap="deliverMap" />
      </v-layout>
      <v-layout>
        <place :map="map" :events="events" :head="head" />
      </v-layout>
      <v-flex xs11 lg12>
        <v-btn
          style="background-color: aquamarine; margin-left: 90%; margin-bottom: 10px;"
          @click="save"
        >작성하기</v-btn>
      </v-flex>
      <v-layout>
        <TimeTable v-on:changeHead="deliverHead" :startDay="setStart" :events="events" />
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import ScheduleImage from "../components/UpdateSchedule/ScheduleImageCover.vue";
import GoogleMap from "../components/CreateSchedule/GoogleMap.vue";
import TimeTable from "../components/CreateSchedule/TimeTable.vue";
import Place from "../components/CreateSchedule/Place.vue";
import axios from "axios";
export default {
  name: "UpdateSchedulePage",
  components: {
    ScheduleImage,
    GoogleMap,
    TimeTable,
    Place
  },
  data() {
    return {
      no: "",
      results: "",
      seq: "",
      title: "",
      description: "",
      coverimageUrl: "",
      map: null,
      events: [],
      setStart: "2019-01-01",
      head: "2019-01-01"
    };
  },
  methods: {
    deliverEvents(passed) {
      this.events = passed;
    },
    deliverMap(mapPassed) {
      this.map = mapPassed;
    },
    deliverHead(value) {
      this.head = value;
    },
    save() {
      var imageIdx = Math.floor(Math.random() * this.events.length);
      imageIdx += imageIdx % 2 == 0 ? 0 : 1;

      this.coverimageUrl = this.events[imageIdx].imageUrl;

      var route = {
        title: this.title,
        description: this.description,
        seq: this.seq,
        name: this.$session.get("user")["name"],
        startDate: this.setStart,
        country: this.coverimageUrl,
        events: this.events,
        scdNo: this.no
      };

      axios
        .post("http://192.168.31.114:8399/schedule/update", route)
        .then(response => (console.log(response.date), this.cancel()))
        .catch(error => {
          console.log(error);
          this.errored = true;
        })
        .finally(() => (this.loading = false));

      this.$router.push("/home");
    }
  },
  beforeCreate() {
    if (this.$session.get("user") !== undefined) {
      this.seq = this.$session.get("user")["seq"];
    }
    this.no = this.$route.params.no;
    axios
      .get("http://192.168.31.114:8399/schedule/selectByNo/" + this.no)
      .then(response => {
        var data = JSON.parse(response.data.events);
        this.setStart = response.data.startDate;
        this.head = response.data.startDate;
        this.events = data;
        this.results = response.data;
        this.title = this.results["title"];
        this.description = this.results["description"];
        this.coverimageUrl = this.results["country"];
      })
      .catch(function(error) {
        console.log(error);
      });
  },
  mounted() {
    if (this.$session.get("user") !== undefined) {
      this.seq = this.$session.get("user")["seq"];
    }
    this.no = Number(this.$route.params.no);
  }
  // beforeDestroy() {
  //   this.save();
  // }
};
</script>
