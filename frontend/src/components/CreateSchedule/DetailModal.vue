<template>
  <v-layout justify-center>
    <v-dialog v-model="dialog" width="600px">
      <template v-slot:activator="{ on }">
        <v-btn
          color="transparent"
          depressed
          v-on="on"
          width="200px"
          style="overflow:hidden;"
        >{{ SpecificLocation.name }}</v-btn>
      </template>
      <v-card>
        <v-card-title class="headline">{{ SpecificLocation.name }}</v-card-title>
        <v-card-text>
          <div>
            카테고리:
            <template v-for="(value, index) in SpecificLocation.types">
              <span :key="index" v-if="value == 'lodging'">숙소</span>
              <span :key="index" v-if="value == 'spa'">스파</span>
              <span :key="index" v-if="value == 'bar'">바</span>
              <span :key="index" v-if="value == 'restaurant'">식당</span>
              <span :key="index" v-if="value == 'point_of_interest'">명소</span>
            </template>
          </div>
          <div>상세 주소: {{ SpecificLocation.formatted_address }}</div>
          <div>방문자 평점: {{ SpecificLocation.rating }}</div>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
export default {
  props: {
    SpecificLocation: null
  },
  data() {
    return {
      dialog: false
    };
  }
};
</script>
