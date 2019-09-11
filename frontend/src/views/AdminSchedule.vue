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
          <v-list-item @click.stop="left = !left" @click="memclick">
            <v-list-item-action>
              <h1>Member</h1>
            </v-list-item-action>
          </v-list-item>
          <br />
          <v-list-item @click.stop="left = !left" @click="postclick">
            <v-list-item-action>
              <h1>Post</h1>
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
            Schedules
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
            :items="schedules"
            :page.sync="page"
            :items-per-page="itemsPerPage"
            hide-default-footer
            class="elevation-1"
            @page-count="pageCount = $event"
          >
            <template v-slot:item.action="{ item }">
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
    // console.log(this.$session.get('post'))
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
    schedule: {
      //   country: null,
      createTime: null,
      description: null,
      likes: null,
      name: null,
      scdNo: null,
      seq: null,
      title: null,
      views: null
    },
    dialog: false,
    drawer: false,
    left: false,
    page: 1,
    pageCount: 0,
    itemsPerPage: 10,
    search: "",
    headers: [
      { text: "scdNo", value: "scdNo" },
      { text: "Title", value: "title" },
      { text: "Name", value: "name" },
      { text: "Description", value: "description" },
      { text: "CreateTime", value: "createTime" },
      { text: "Likes", value: "likes" },
      { text: "Views", value: "views" },
      { text: "Actions", value: "action", sortable: false }
    ],
    schedules: []
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
        url: "//192.168.31.114:8399/schedule/selectAll/"
      }).then(response => {
        //   console.log(response['data'])
        this.schedules = response["data"];
        // this.schedules.push(response['data'][0])
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
      //   console.log(item['scdNo'])
      var con_test = confirm("Are you sure you want to delete this item?");
      console.log(con_test);
      if (con_test) {
        await axios({
          method: "delete",
          url: `//192.168.31.114:8399/schedule/delete/${item["scdNo"]}`
        }).then(response => {
          console.log("delete!");
        });
        this.selectAll();
      }
    },

    editItem(item) {
      this.schedule.createDate = item["createDate"];
      this.schedule.name = item["name"];
      this.schedule.likes = item["likes"];
      this.schedule.scheduleNo = item["scheduleNo"];
      this.schedule.thumbnail = item["thumbnail"];
      this.schedule.title = item["title"];
      this.schedule.description = item["description"];
      this.schedule.views = item["views"];
      this.dialog = true;
    },

    editSave: async function() {
      await axios
        .schedule("//192.168.31.114:8399/schedule/update", {
          title: this.schedule.title,
          description: this.schedule.description
        })
        .then(
          response => (
            // alert("업데이트 성공 ^^")
            this.$notify({
              group: "foo",
              text: "업데이트 성공 ^^",
              duration: 2000
            }),
            // console.log(response.data),
            // console.log(this.$session.get('schedule')['type']),
            this.close(),
            this.selectAll()
          )
        )
        .catch(error => {
          console.log(error);
          this.errored = true;
        })
        .finally(() => (this.loading = false));
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