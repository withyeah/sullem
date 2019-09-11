<template>
      <v-container grid-list-md>
          <v-layout wrap>
            <v-flex v-for="(result,i) in results" :key="i" xs12 sm6 md4>
              <home-schedule-card
                v-if="no == 2"
                :result="result"
              ></home-schedule-card>
              <home-schedule-card
                v-if="no == 4"
                :result="result"
              ></home-schedule-card>
              <home-post-card v-if="no === '1'" :result="result"></home-post-card>

              <home-post-card v-if="no === '3'" :result="result"></home-post-card>

              <home-post-card v-if="no === '5'" :result="result"></home-post-card>
            </v-flex>
          </v-layout>
        </v-container>
</template>

<script>
import axios from 'axios'
import HomePostCard from './HomePostCard.vue'
import HomeScheduleCard from './HomeScheduleCard.vue'

export default {
  components: {
    HomePostCard,
    HomeScheduleCard
  },
  props: {
    no: { type: String }
  },
  data () {
    return {
      results: null
    }
  },
  created () {
    if (this.no === '1') {
      axios.get('http://192.168.31.114:8399/post/selectTopPost')
        .then(response => {
          console.log(response)
          this.results = response.data
        }
        )
        .catch(function (error) {
          console.log(error)
        })
    } else if (this.no === '2') {
      axios.get('http://192.168.31.114:8399/schedule/selectByViewsTop')
        .then(response => {
          this.results = response.data
        }
        )
        .catch(function (error) {
          console.log(error)
        })
    } else if (this.no === '3') {
      axios.get('http://192.168.31.114:8399/post/selectPage/ /1')
        .then(response => {
          if (response.data.postList.length > 6) {
            this.results = response.data.postList.slice(0, 6)
          } else {
            this.results = response.data.postList
          }

          // console.log(this.results)
        }
        )
        .catch(function (error) {
          console.log(error)
        })
    } else if (this.no === '4') {
      axios.get('http://192.168.31.114:8399/schedule/selectPage/ /1')
        .then(response => {
          if (response.data.list.length > 6) {
            this.results = response.data.list.slice(0, 6)
          } else {
            this.results = response.data.list
          }
        }
        )
        .catch(function (error) {
          console.log(error)
        })
    } else if (this.no === '5') {
      axios.get(`//192.168.31.114:8399/post/selectBySeq/${this.$session.get('user')['seq']}`)
        .then(response => {
          // console.log(response.data)
          this.results = response.data
          // console.log(this.results)
        }
        )
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style>

</style>
