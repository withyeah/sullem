<template>
  <v-layout wrap>
    <v-flex
      v-for="(result,i) in results"
      :key="i"
      xs12 sm6 md4
      pa-1
    >
      <schedule-card v-if="category == 0"
        :no="result.scdNo"
        :name="result.name"
        :title="result.title"
        :country="result.country"
        :description="result.description"
      ></schedule-card>
      <post-card v-if="category == 1"
        :no="result.postNo"
        :name="result.name"
        :title="result.title"
        :description="result.description"
        :create-date="result.createDate"
        :thumbnail="result.thumbnail"
      ></post-card>
    </v-flex>
    <v-flex xs12>
      <div class="text-center">
    <v-pagination
      v-if="results.length != 0"
      v-model="page"
      :length="endpage"
      color="teal accent-4"
      prev-icon="fa-angle-left"
      next-icon="fa-angle-right"
    ></v-pagination>
  </div>

    </v-flex>
  </v-layout>
</template>

<script>
import ScheduleCard from './ScheduleCard.vue'
import PostCard from './PostCard.vue'

export default {
  components: {
    ScheduleCard,
    PostCard
  },
  props: {
    results: { type: Array },
    num: { type: Number },
    category: { type: Number },
    endpage: { type: Number }
  },
  created () {
    this.page = this.num
  },
  data () {
    return {
      page: 1
    }
  },
  watch: {
    page: function () {
      this.$emit('getPage', this.page)
    }
  },
  methods: {

  }
}
</script>

<style>
.v-text-field__details {
  display: none !important;
}
</style>
