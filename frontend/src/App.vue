<template>
  <div>
    <v-app>
      <template v-if="this.$route['name'] !== 'intro'">
        <template v-if="this.$route['name'] !== 'admin'">
          <Header />
        </template>
      </template>

      <v-content>
        <router-view />
      </v-content>

      <template v-if="this.$route['name'] !== 'intro'">
        <template v-if="this.$route['name'] !== 'admin'">
          <Footer />
        </template>
      </template>
    </v-app>
    <notifications group="foo" position="top center" />
  </div>
</template>

<script>
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
export default {
  name: "App",
  components: {
    Header,
    Footer
  },
  data: () => ({
    val: 1
    //
  })
};
</script>
