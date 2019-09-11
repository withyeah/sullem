import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    Login: false,
    Account: false,
    FindPassword: false,
    user: null,
    seq: "",
    AdminNav: false,
    page: "",
    category: "",
    text: "",
    scheduleTitle: "",
    scheduleStart: null,
    scheduleDescription: ""
  },
  mutations: {},
  actions: {}
});
