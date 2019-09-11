<template>
  <div>
    <v-img
      :src="coverImage"
      alt="coverImage"
      height="300"
      gradient="to top right, rgba(0,0,0,.2), rgba(0,0,0,.4)"
    >
      <v-layout fill-height justify-center align-center>
        <div class="white--text text-center font-weight-bold" style="font-size: 20px;">
          {{ title }}
          <br />
          <div class="white--text text-center font-weight-thin">{{ description }}</div>
        </div>
      </v-layout>
    </v-img>
  </div>
</template>

<script>
export default {
  name: "ScheduleImage",
  props: {
    coverImage: String,
    title: "",
    description: ""
  }
};
</script>

<style>
</style>
