<template>
  <div>
    <ScheduleImage :coverImage="coverimageUrl" />
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
        >저장</v-btn>
      </v-flex>
      <v-layout>
        <TimeTable v-on:changeHead="deliverHead" :startDay="setStart" :events="events" />
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import ScheduleImage from '../components/CreateSchedule/ScheduleImageCover.vue'
import GoogleMap from '../components/CreateSchedule/GoogleMap.vue'
import TimeTable from '../components/CreateSchedule/TimeTable.vue'
import Place from '../components/CreateSchedule/Place.vue'
import axios from 'axios'
export default {
  name: 'CreateSchedulePage',
  components: {
    ScheduleImage,
    GoogleMap,
    TimeTable,
    Place
  },
  data () {
    return {
      title: this.$store.state.scheduleTitle,
      description: this.$store.state.scheduleDescription,
      coverimageUrl:
        'http://tourimage.interpark.com/BBS/Tour/FckUpload/201703/discovery_20170323_6362582542356180960.jpg',
      map: null,
      events: [],
      setStart: this.$store.state.scheduleStart,
      head: this.$store.state.scheduleStart
    }
  },
  beforeCreate () {
    if (this.$session.get('user') === undefined) {
      alert('로그인이 필요합니다.')
      this.$router.push({ name: 'home' })
    }
  },
  methods: {
    deliverEvents (passed) {
      this.events = passed
    },
    deliverMap (mapPassed) {
      this.map = mapPassed
    },
    deliverHead (value) {
      this.head = value
    },
    async save () {
      var imageIdx = Math.floor(Math.random() * this.events.length)
      imageIdx += imageIdx % 2 == 0 ? 0 : 1

      this.coverimageUrl = this.events[imageIdx].imageUrl

      var route = {
        title: this.title,
        description: this.description,
        seq: this.$session.get('user')['seq'],
        name: this.$session.get('user')['name'],
        startDate: this.setStart,
        country: this.coverimageUrl,
        events: this.events
      }

      await axios
        .post('http://192.168.31.114:8399/schedule/insert', route)
        .then(response => (console.log(response.date), this.cancel()))
        .catch(error => {
          console.log(error)
          this.errored = true
        })
        .finally(() => (this.loading = false))

      this.$router.push('/home')
    }
  }
  // beforeDestroy() {
  //   this.save();
  // }
}
</script>
