<template>
  <div>
    <v-timeline dense>
      <v-timeline-item color="#F06292" fill-dot right>
        <v-card class="elevation-2" max-width="250px">
          <v-card-text>
            <p class="font-weight-bold">출발</p>
          </v-card-text>
        </v-card>
      </v-timeline-item>
      <v-timeline-item
        v-for="(step, index) in steps"
        :key="index"
        :color="index != steps.length - 1 ? '#4DB6AC' : '#F06292'"
        :icon="step.icon"
        fill-dot
        right
      >
        <!-- <span slot="opposite">
          <v-icon v-if="step.travel_mode == 'TRANSIT'">fa-bus</v-icon>
          <v-icon v-if="step.travel_mode == 'DRIVING'">fa-car</v-icon>
          <v-icon v-if="step.travel_mode == 'WALKING'">fa-walking</v-icon>
        </span>-->
        <v-card class="elevation-2" max-width="250px">
          <v-card-text>
            <p class="font-weight-bold" v-if="index == steps.length - 1">도착</p>
            <p>{{ step.instructions }}</p>
            <p>{{ step.distance }}, {{ step.duration }}</p>
          </v-card-text>
        </v-card>
      </v-timeline-item>
    </v-timeline>
  </div>
</template>

<script>
export default {
  name: "StepsTimeLine",
  props: {
    steps: null
  },
  data() {
    return {};
  }
};
</script>