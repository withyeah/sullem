<template>
<v-hover v-slot:default="{ hover }">
  <v-card   :elevation="hover ? 5 : 0"
   max-height="300px"
   @click="$router.push({name:'post', params:{no:no}})"
   outlined
  >
    <v-img
      class="white--text"
      height="200px"
      :src="thumbnail"
    >
    </v-img>
    <v-card-title class="text-truncate body-1 font-weight-bold">{{title}}</v-card-title>
    <v-card-text class="body-2 font-weight-medium ma-0">
      {{description}}
    </v-card-text>
  </v-card>
</v-hover>
</template>

<script>
export default {
  props: {
    no: { type: Number },
    name: { type: String },
    title: { type: String },
    description: { type: String },
    createDate: { type: String },
    thumbnail: { type: String }
  },
  mounted () {

  }
}
</script>

<style>

</style>
