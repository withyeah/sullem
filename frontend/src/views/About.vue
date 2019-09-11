<template>
  <div class="about">
    <h1>This is an about page</h1>
    <p>구글 맵 테스트 중이에여</p>
    <Place></Place>
  </div>
</template>

<script>
import Place from "@/components/CreateSchedule/Place.vue";
export default {
  name: "about",
  components: {
    Place
  },
  mounted() {
    this.$store.state.val = true;
  } // navbar 나오게 전역변수 true로
};
</script>

<style>
#map {
  height: 500px;
}
</style>
