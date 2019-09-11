<template>
  <div>
    <v-app id="inspire">
      <v-app-bar app clipped-right color="blue-grey" dark>
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
        <v-toolbar-title>Sullem Administration</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-app-bar>
      <!-- 햄버거 부분 코드 시작 -->
      <v-navigation-drawer v-model="drawer" app temporary disable-resize-watcher>
        <v-list dense>
          <v-list-item @click.stop="left = !left" @click="home">
            <v-list-item-action>
              <h1>Home</h1>
            </v-list-item-action>
          </v-list-item>
          <br />
          <v-list-item @click.stop="left = !left" @click="postclick">
            <v-list-item-action>
              <h1>Post</h1>
            </v-list-item-action>
          </v-list-item>
          <br />
          <v-list-item @click.stop="left = !left" @click="scheclick">
            <v-list-item-action>
              <h1>Schedule</h1>
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-navigation-drawer v-model="left" fixed></v-navigation-drawer>
      <!-- 햄버거 부분 코드 끝 -->
      <!-- 리스트 부분 코드 시작 -->
      <v-container>
        <v-card>
          <v-card-title>
            Members
            <v-spacer></v-spacer>
            <v-text-field
              v-model="search"
              append-icon="fas fa-search"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table
            :headers="headers"
            :search="search"
            :items="members"
            :page.sync="page"
            :items-per-page="itemsPerPage"
            hide-default-footer
            class="elevation-1"
            @page-count="pageCount = $event"
          >
            <template v-slot:top>
              <v-divider class="mx-4" inset vertical></v-divider>
              <v-spacer></v-spacer>
              <v-dialog v-model="dialog" max-width="500px">
                <v-card>
                  <v-card-text>
                    <v-container grid-list-md>
                      <v-layout wrap>
                        <v-flex xs12>
                          <v-text-field
                            v-model="user.password"
                            label="Password*"
                            type="password"
                            required
                          ></v-text-field>
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
                          <v-select :items="types" label="Type*" v-model="user.type"></v-select>
                        </v-flex>
                      </v-layout>
                    </v-container>
                    <small>*indicates required field</small>
                  </v-card-text>
                  <!-- edit 부분 -->
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="editSave">Save</v-btn>
                  </v-card-actions>
                </v-card>
                <!-- edit 끝 -->
              </v-dialog>
            </template>
            <template v-slot:item.action="{ item }">
              <v-icon small class="mr-2" @click="editItem(item)">fas fa-edit</v-icon>
              <v-icon small @click="deleteItem(item)">fas fa-trash</v-icon>
            </template>
          </v-data-table>
          <div class="text-center pt-2">
            <v-pagination v-model="page" :length="pageCount"></v-pagination>
            <v-text-field
              :value="itemsPerPage"
              label="Items per page"
              type="number"
              min="-1"
              max="15"
              @input="itemsPerPage = parseInt($event, 10)"
            ></v-text-field>
          </div>
        </v-card>
      </v-container>
      <v-footer app color="blue-grey" class="white--text" style="height: 70px;">
        <v-spacer></v-spacer>
        <span>&copy; 2019</span>
      </v-footer>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";
import { async } from "q";

export default {
  props: {
    source: String
  },
  created() {
    this.selectAll();
    // console.log(this.$session.get('user'))
    if (this.$session.get("user") === undefined) {
      router.push({ path: "home" });
      // alert('접근 권한이 없습니다.')
      this.$notify({
        group: "foo",
        title: "Authentication needed",
        text: "접근 권한이 없습니다.",
        type: "error",
        duration: 2000
      });
    } else {
      if (this.$session.get("user")["type"] === "admin") {
        // alert('관리자입니다.')
      } else {
        router.push({ path: "home" });
        // alert("관리자 권한이 없습니다.");
        this.$notify({
          group: "foo",
          title: "Authentication needed",
          text: "접근 권한이 없습니다.",
          type: "error",
          duration: 2000
        });
      }
    }
  },
  data: () => ({
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
    types: ["admin", "user"],
    user: {
      email: null,
      password: null,
      password_valid: null,
      name: null,
      age: null,
      gender: null,
      phone: null
    },
    dialog: false,
    drawer: false,
    left: false,
    page: 1,
    pageCount: 0,
    itemsPerPage: 10,
    search: "",
    headers: [
      { text: "Seq", value: "seq" },
      { text: "Type", value: "type" },
      { text: "Name", value: "name" },
      { text: "Email", value: "email" },
      { text: "Created", value: "create_at" },
      { text: "Age", value: "age" },
      { text: "Phone", value: "phone" },
      { text: "Actions", value: "action", sortable: false }
    ],
    members: []
  }),

  watch: {
    dialog(val) {
      val || this.close();
    }
  },

  methods: {
    selectAll: function() {
      axios({
        method: "get",
        url: "//192.168.31.114:8399/member/selectAll/"
      }).then(response => {
        // console.log(response['data'])
        this.members = response["data"];
        // this.members.push(response['data'][0])
      });
    },
    memclick: function() {
      router.push({ path: "admin" });
    },
    postclick: function() {
      router.push({ path: "adminpost" });
    },
    scheclick: function() {
      router.push({ path: "adminschedule" });
    },
    home: function() {
      router.push({ path: "home" });
    },

    deleteItem: async function(item) {
      console.log(item["seq"]);
      var con_test = confirm("Are you sure you want to delete this item?");
      console.log(con_test);
      if (con_test) {
        await axios({
          method: "delete",
          url: `//192.168.31.114:8399/member/delete/${item["seq"]}`
        }).then(response => {
          console.log("delete!");
        });
        this.selectAll();
      }
    },

    editItem(item) {
      this.user.seq = item["seq"];
      this.user.name = item["name"];
      this.user.create_at = item["create_at"];
      this.user.age = item["age"];
      this.user.phone = item["phone"];
      this.user.email = item["email"];
      this.user.type = item["type"];
      this.dialog = true;
    },

    editSave: async function() {
      var self = this;
      await axios
        .post("//192.168.31.114:8399/member/update", {
          email: this.user.email,
          password: this.user.password,
          phone: this.user.phone,
          type: this.user.type
        })
        .then(
          response => (
            // alert("업데이트 성공 ^^")
            self.$notify({
              group: "foo",
              text: "업데이트 성공 ^^",
              duration: 2000
            }),
            // console.log(response.data),
            // console.log(this.$session.get('user')['type']),
            this.close(),
            this.selectAll()
          )
        )
        .catch(error => {
          console.log(error);
          this.errored = true;
        })
        .finally(() => (this.loading = false));
      if (this.$session.get("user")["email"] === this.user.email) {
        console.log(this.user);
        this.$session.set("user", this.user);
      }
    },

    close() {
      this.dialog = false;
    },

    enterkey: function() {
      if (window.event.keyCode === 13) {
        // 엔터키가 눌렸을 때 실행할 내용
        console.log("enter!");
      }
    }
  }
};
</script>

<style>
</style>
