<template>
  <div v-if="results !== ''">
    <v-img
      :src="results.country"
      aspect-ratio="3.0"
      gradient="rgba(255,255,255,.25), rgba(255,255,255,.5)"
      v-resize="resize"
      v-bind:style="{height:newHeight}"
    >
      <v-layout class="text-center" align-center justify-center fill-height>
        <v-flex xs12>
          <span class="display-2 font-weight-bold white--text" style="text-shadow: 2px 2px 8px black;">{{results.title}}</span>
        </v-flex>
      </v-layout>
    </v-img>

    <v-container grid-list-md>
      <v-layout wrap>
        <v-flex md3 class="hidden-sm-and-down">
          <v-card color="teal accent-4" dark>
            <v-card-title primary class="title">{{results.name}}</v-card-title>
            <v-card-text>{{results.createTime.substring(0,16)}}</v-card-text>
            <v-card-text class="title">{{ results.description }}</v-card-text>
            <v-card-actions>
              <v-btn @click="$router.go(-1)" color="grey lighten-3" light>목록</v-btn>
              <v-btn v-if="seq === results.seq" @click="updatePage" color="grey lighten-3" light>수정</v-btn>
              <v-btn
                v-if="seq === results.seq"
                @click="deleteSchedule"
                color="grey lighten-3"
                light
              >삭제</v-btn>
            </v-card-actions>
          </v-card>
        </v-flex>
        <v-flex xs12 md9>
          <v-layout wrap>
            <v-tabs centered grow icons-and-text color="teal accent-4">
              <v-tab @click="switchone" style="margin: auto;">
                스케쥴
                <v-icon>fa-calendar-alt</v-icon>
              </v-tab>
              <v-tab @click="switchtwo" style="margin: auto;">
                경로
                <v-icon>fa-map-marked-alt</v-icon>
              </v-tab>
            </v-tabs>

            <template v-if="this.tab">
              <v-flex xs12 v-for="(event,i) in events" :key="i">
                <v-flex ma-1 xs12>
                  <span class="title font-weight-bold" ma-2>
                    <v-icon>fa-calendar-day</v-icon>
                    &nbsp;Day {{i+1}}
                  </span>
                  <span class="subtitle-2 grey--text">{{event[0].start.substring(0,10)}}</span>
                </v-flex>
                <v-sheet elevation="5">
                  <v-timeline align-top dense>
                    <v-timeline-item
                      v-for="(data, i) in event"
                      v-if="data.type === 'location'"
                      :key="i"
                      :color="getColor(data.category)"
                      :icon="getIcon(data.category)"
                      fill-dot
                    >
                      <v-card class="elevation-2" width="95%">
                        <v-layout pa-3 wrap>
                          <v-flex xs12>
                            <v-icon>fa-clock</v-icon>&nbsp;
                            <span>{{data.start.substring(11)}} ~ {{data.end.substring(11)}}</span>
                          </v-flex>
                          <v-flex xs12 sm6>
                            <v-card elevation="3">
                              <v-img height="200" :src="data.imageUrl"></v-img>
                            </v-card>
                          </v-flex>
                          <v-flex xs12 sm6>
                            <v-card-title class="font-weight-bold">{{data.name}}</v-card-title>
                            <v-card-text>{{data.address}}</v-card-text>
                            <v-card-text>
                              <v-rating
                                v-model="data.rating"
                                readonly
                                size="15"
                                color="yellow"
                                background-color="yellow"
                                half-increments
                                empty-icon
                                dense
                              ></v-rating>
                            </v-card-text>
                          </v-flex>
                        </v-layout>
                      </v-card>
                    </v-timeline-item>
                  </v-timeline>
                </v-sheet>
              </v-flex>
            </template>

            <!-- tab2 부분 -->
            <template v-if="!this.tab">
              <v-flex xs12>
                <DetailMap style="width:auto" :mapEvents="event_data" />
              </v-flex>
              <v-flex xs12>
                <TimeTable :events="event_data" :startDay="events[0][0].start.substring(0,10)" />
              </v-flex>
            </template>

            <v-flex xs12 mt-2>
              <v-sheet elevation="5">
                <v-layout>
                  <v-flex xs12 ma-5>
                    <div id="disqus_thread"></div>
                  </v-flex>
                </v-layout>
              </v-sheet>
            </v-flex>
            <!--  -->
          </v-layout>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios'
import DetailMap from '../components/ScheduleDetail/DetailMap.vue'
import TimeTable from '../components/CreateSchedule/TimeTable.vue'
import router from '@/router'

export default {
  components: {
    DetailMap,
    TimeTable
  },
  data: () => ({
    no: '',
    events: [],
    results: '',
    tab: true,
    seq: '',
    disqus_config: '',
    event_data: [],
    newHeight: null
  }),
  created () {
    if (this.$session.get('user') !== undefined) {
      this.seq = this.$session.get('user')['seq']
    }
    this.no = this.$route.params.no
    axios
      .get('http://192.168.31.114:8399/schedule/selectByNo/' + this.no)
      .then(response => {
        var data = JSON.parse(response.data.events)
        var date = ''
        var count = -1
        var results = []
        for (let i = 0; i < data.length; i++) {
          if (date !== data[i].start.substring(0, 10)) {
            count++
            results.push([])
            date = data[i].start.substring(0, 10)
            results[count].push(data[i])
          } else {
            results[count].push(data[i])
          }
        }
        this.events = results
        this.event_data = data

        this.results = response.data
        console.log(this.results)
      })
      .catch(function (error) {
        console.log(error)
      })

    this.disqus_config = function () {
      this.page.url = 'http://192.168.31.129:8080/schedule/' + this.no // Replace PAGE_URL with your page's canonical URL variable
      this.page.identifier = this.no // Replace PAGE_IDENTIFIER with your page's unique identifier variable
    }

    let recaptchaScript = document.createElement('script')
    recaptchaScript.setAttribute(
      'src',
      'https://happyhacking-1.disqus.com/embed.js'
    )
    document.head.appendChild(recaptchaScript)
  },
  methods: {
    getIcon (category) {
      if (category[0] === 'lodging') {
        return 'fa-bed'
      } else if (category[0] === 'restaurant') {
        return 'fa-utensils'
      } else {
        return 'fa-camera'
      }
    },
    getColor (category) {
      if (category[0] === 'lodging') {
        return 'red'
      } else if (category[0] === 'restaurant') {
        return 'yellow lighten-3'
      } else {
        return 'teal accent-4'
      }
    },
    switchone () {
      this.tab = true
    },

    switchtwo () {
      this.tab = false
    },
    deleteSchedule () {
      if (confirm('일정을 정말로 삭제하시겠습니까?')) {
        axios
          .delete('http://192.168.31.114:8399/schedule/delete/' + this.no)
          .then(response => {
            console.log(response.data)
            if (response.data === 1) {
              this.$router.go(-1)
            } else {
              // alert("삭제에 실패했습니다.");
              this.$notify({
                group: 'foo',
                title: 'Error message',
                text: '삭제에 실패했습니다.',
                type: 'error',
                duration: 2000
              })
            }
          })
          .catch(function (error) {
            console.log(error)
          })
      }
    },
    updatePage () {
      console.log(this.no)
      router.push({ path: `update/${this.no}` })
    },
    resize () {
      this.newHeight =
        window.innerWidth < 600 ? window.innerHeight - 56 + 'px' : null
    }
  }
}
</script>

<style>
.v-tabs-slider-wrapper {
  /* width: 30% !important; */
}
</style>
