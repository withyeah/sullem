<template>
  <v-card height="200" width="100%" outlined
    @click="$router.push({name:'schedule', params:{no:result.scdNo}})"
  >
    <v-img class="white--text" :src="result.country"  alt="lorem" aspect-ratio="1" height="200">
      <v-card-title
        class="align-end fill-height title font-weight-bold"
        style="text-shadow: 2px 2px 8px black;"

      ><span class="text-wrap">{{ result.title}}</span>

    </v-card-title>

    </v-img>

  </v-card>
</template>

<script>
export default {
  props: {
    result: { type: Object }
  },
  created () {

  }
}
</script>

<style>

</style>
