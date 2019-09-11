<template>
  <v-dialog v-model="isOpenCreateModal" persistent max-width="600px">
    <v-card>
      <v-card-title>
        <span class="headline">새로운 여행일정</span>
      </v-card-title>
      <v-card-text>
        <v-form v-model="valid">
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field :rule="titleRules" v-model="title" label="여행일정 제목" required></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-text-field v-model="description" label="여행일정 설명" required></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-menu
                  v-model="modal"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  full-width
                  required
                  min-width="290px"
                >
                  <template v-slot:activator="{ on }">
                    <v-text-field v-model="startDate" label="출발일" readonly v-on="on"></v-text-field>
                  </template>
                  <v-date-picker v-model="startDate" @input="modal = false"></v-date-picker>
                </v-menu>
              </v-flex>
            </v-layout>
          </v-container>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="cancel">취소</v-btn>
        <v-btn color="blue darken-1" text @click="save">생성</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "CreateScheduleModal",
  props: {
    isOpenCreateModal: false
  },
  data() {
    return {
      valid: false,
      title: "",
      description: "",
      startDate: "",
      modal: false,
      titleRules: [v => !!v || "Title is required"]
    };
  },
  methods: {
    cancel() {
      this.$emit("closeCreateScheduleModal");
    },
    save() {
      if (this.title != "" && this.description != "" && this.startDate != "") {
        this.$store.state.scheduleStart = this.startDate;
        this.$store.state.scheduleTitle = this.title;
        this.$store.state.scheduleDescription = this.description;
        this.$emit("createNewSchedule");
      } else {
        // alert("입력정보를 확인하세요.");
        this.$notify({
          group: "foo",
          title: "Error message",
          text: "입력정보를 확인하세요.",
          type: "error",
          duration: 2000
        });
      }
    }
  }
};
</script>