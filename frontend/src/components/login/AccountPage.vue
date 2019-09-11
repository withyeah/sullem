<template>
  <v-dialog v-model="this.$store.state.Account" persistent max-width="600px">
    <v-card>
      <v-card-title>
        <span class="headline">Account</span>
      </v-card-title>
      <v-card-text>
        <v-container grid-list-md>
          <v-layout wrap>
            <v-flex sm6>
              <v-text-field v-model="user.email" label="Email*" required></v-text-field>
            </v-flex>
            <v-btn id="check" style="margin-top: 17px; margin-left: 4px;" @click="vaildCheck">check</v-btn>
            <v-flex xs12>
              <v-text-field v-model="user.password" label="Password*" type="password" required></v-text-field>
            </v-flex>
            <v-flex xs12>
              <v-text-field
                v-model="user.password_valid"
                label="Valid Password*"
                type="password"
                required
              ></v-text-field>
            </v-flex>
            <v-flex xs12>
              <v-text-field v-model="user.phone" label="phone(010-0000-0000)*" required></v-text-field>
            </v-flex>
            <v-flex xs12 sm6 md4>
              <v-text-field v-model="user.name" label="name*" required></v-text-field>
            </v-flex>
            <v-flex xs12 sm6 md4>
              <v-select :items="borns" label="Borns" v-model="user.age"></v-select>
            </v-flex>
            <v-flex xs12 sm6 md4>
              <v-select :items="genders" label="Gender" v-model="user.gender"></v-select>
              <!-- <v-text-field v-model="gender" label="gender*" required></v-text-field> -->
            </v-flex>
          </v-layout>
        </v-container>
        <small>*indicates required field</small>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="cancel">Close</v-btn>
        <v-btn color="blue darken-1" text @click="signUp">Register</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";
export default {
  name: "AccountPage",
  data() {
    return {
      borns: [
        "1990",
        "1991",
        "1992",
        "1993",
        "1994",
        "1995",
        "1996",
        "1997",
        "1998",
        "1999",
        "2000",
        "2001",
        "2002",
        "2003",
        "2004",
        "2005",
        "2006",
        "2007",
        "2008",
        "2009",
        "2010",
        "2011",
        "2012",
        "2013",
        "2014",
        "2015",
        "2016",
        "2017",
        "2018"
      ],
      genders: ["Man", "Woman"],
      user: {
        email: null,
        password: null,
        password_valid: null,
        name: null,
        age: null,
        gender: null,
        phone: null
      },
      validCheck: null
    };
  },
  methods: {
    cancel() {
      this.$store.state.Account = false;
    },
    signUp() {
      var self = this;
      if (
        this.user.email != null &&
        this.user.password != null &&
        this.user.password_valid != null &&
        this.user.name != null &&
        this.user.age != null &&
        this.user.gender != null &&
        this.user.phone != null &&
        !this.validCheck
      ) {
        if (this.user.password == this.user.password_valid) {
          axios
            .post("http://192.168.31.114:8399/member/insert/", {
              email: this.user.email,
              password: this.user.password,
              name: this.user.name,
              age: this.user.age,
              gender: this.user.gender,
              phone: this.user.phone
            })
            .then(
              response => (
                // alert("회원가입을 축하합니다 ^^")
                self.$notify({
                  group: "foo",
                  text: "회원가입을 축하합니다.",
                  duration: 2000
                }),
                this.cancel()
              )
            )
            .catch(error => {
              console.log(error);
              this.errored = true;
            })
            .finally(() => (this.loading = false));
        } else {
          // alert("패스워드를 확인하세요.");
          this.$notify({
            group: "foo",
            title: "Password needed",
            text: "패스워드를 확인하세요.",
            type: "warn",
            duration: 2000
          });
        }
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
    },
    async vaildCheck() {
      let user = null;
      var check = document.getElementById("check");
      await axios
        .get(
          "http://192.168.31.114:8399/member/select_email/" + this.user.email
        )
        .then(response => ((user = response.data), console.log(user)))
        .catch(error => {
          console.log(error);
          this.errored = true;
        })
        .finally(() => (this.loading = false));

      if (user === null || user == "") {
        this.validCheck = false;
        // alert("사용 가능한 이메일입니다.");
        this.$notify({
          group: "foo",
          text: "사용 가능한 이메일입니다.",
          duration: 2000
        });
        console.log(check);
        check.classList.remove("btntrue");
        check.classList.add("btnfalse");
      } else {
        this.validCheck = true;
        // alert("이미 있는 이메일입니다.");
        this.$notify({
          group: "foo",
          title: "Email already exists",
          text: "이미 있는 이메일입니다.",
          type: "warn",
          duration: 2000
        });
        check.classList.remove("btnfalse");
        check.classList.add("btntrue");
      }
    }
  }
};
</script>

<style>
.btnfalse {
  background-color: aquamarine !important;
}

.btntrue {
  background-color: rgb(248, 71, 71) !important;
}
</style>