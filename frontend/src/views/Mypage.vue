<template>
  <div class="animate-contentopen">
    <v-img
      src="../assets/mypage.jpg"
      aspect-ratio="3.0"
      gradient="rgba(255,255,255,.25), rgba(255,255,255,.5)"
    >
      <v-layout align-center justify-center fill-height>
        <v-flex class="text-center" xs4 md2>
          <h1>MyPage</h1>
        </v-flex>
      </v-layout>
    </v-img>

    <v-container>
      <v-tabs centered grow icons-and-text color="teal accent-4">
        <v-tab style="margin: auto;" @click="switchpost">
          내 포스트
          <v-icon>fa-camera</v-icon>
        </v-tab>
        <v-tab style="margin: auto;" @click="switchsche">
          내 스케줄
          <v-icon>fa-calendar-alt</v-icon>
        </v-tab>
      </v-tabs>
      <v-card v-if="this.tab">
        <v-card-title>
          내 포스트
          <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            append-icon="fas fa-search"
            label="Search"
            single-line
            hide-details
            color="teal accent-4"
          ></v-text-field>
        </v-card-title>
        <v-data-table
          :headers="headers"
          :search="search"
          :items="posts"
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
                        <v-text-field v-model="post.title" label="Title*" required></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field v-model="post.description" label="Description*" required></v-text-field>
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
            <v-icon small class="mr-2" @click="openItem(item)">fas fa-book-open</v-icon>
            <v-icon small class="mr-2" @click="editItem(item)">fas fa-edit</v-icon>
            <v-icon small @click="deleteItem(item)">fas fa-trash</v-icon>
          </template>
        </v-data-table>
        <div class="text-center pt-2">
          <v-pagination v-model="page" :length="pageCount" color="teal accent-4"></v-pagination>
          <v-text-field
            :value="itemsPerPage"
            label="Items per page"
            type="number"
            min="-1"
            max="15"
            @input="itemsPerPage = parseInt($event, 10)"
            color="teal accent-4"
          ></v-text-field>
        </div>
      </v-card>

      <v-card v-if="!this.tab">
        <v-card-title>
          내 스케줄
          <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            append-icon="fas fa-search"
            label="Search"
            single-line
            hide-details
            color="teal accent-4"
          ></v-text-field>
        </v-card-title>
        <v-data-table
          :headers="sheaders"
          :search="search"
          :items="schedules"
          :page.sync="page"
          :items-per-page="itemsPerPage"
          hide-default-footer
          class="elevation-1"
          @page-count="pageCount = $event"
        >
          <template v-slot:item.action="{ item }">
            <v-icon small class="mr-2" @click="sopenItem(item)">fas fa-book-open</v-icon>
            <v-icon small class="mr-2" @click="seditItem(item)">fas fa-edit</v-icon>
            <v-icon small @click="sdeleteItem(item)">fas fa-trash</v-icon>
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
            color="teal accent-4"
          ></v-text-field>
        </div>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import ItemList from "@/components/Home/ItemList.vue";
import router from "@/router";
import axios from "axios";
import { async } from "q";

export default {
  data() {
    return {
      tab: true,
      posts: [],
      post: {
        createDate: null,
        description: null,
        likes: null,
        name: null,
        postNo: null,
        thumbnail: null,
        title: null,
        views: null
      },
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
        { text: "seq", value: "seq" },
        { text: "PostNo", value: "postNo" },
        { text: "Title", value: "title" },
        { text: "Name", value: "name" },
        { text: "Description", value: "description" },
        { text: "CreateDate", value: "createDate" },
        { text: "Likes", value: "likes" },
        { text: "Views", value: "views" },
        { text: "Actions", value: "action", sortable: false }
      ],
      sheaders: [
        { text: "seq", value: "seq" },
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
    };
  },
  components: { ItemList },
  created() {
    // console.log(this.$session.get('user'))
    if (this.$session.get("user") === undefined) {
      router.push({ path: "home" });
      // alert("로그인 해야 이용 가능한 서비스입니다.");
      this.$notify({
        group: "foo",
        title: "Login required",
        text: "로그인 해야 이용 가능한 서비스입니다.",
        type: "error",
        duration: 2000
      });
    }
  },
  mounted() {
    this.selectAll();
  },
  methods: {
    selectAll: function() {
      axios({
        method: "get",
        url: `//192.168.31.114:8399/post/selectBySeq/${
          this.$session.get("user")["seq"]
        }`
      }).then(response => {
        // console.log(response['data'])
        this.posts = response["data"];
        // this.posts.push(response['data'][0])
      });
    },

    openItem: function(item) {
      router.push({ path: `post/${item["postNo"]}` });
    },

    deleteItem: async function(item) {
      console.log(item["postNo"]);
      var con_test = confirm("Are you sure you want to delete this item?");
      console.log(con_test);
      if (con_test) {
        await axios({
          method: "delete",
          url: `//192.168.31.114:8399/post/delete/${item["postNo"]}`
        }).then(response => {
          console.log("delete!");
        });
        this.selectAll();
      }
    },

    editItem(item) {
      if (confirm("게시물을 수정하시겠습니까?")) {
        let no = item.postNo;
        axios
          .get("http://192.168.31.114:8399/post/detailPost/" + no)
          .then(response => {
            this.$router.push({
              name: "postWriter",
              params: { no: no, post: response.data }
            });
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },

    editSave: async function() {
      await axios
        .post("//192.168.31.114:8399/post/update", {
          title: this.post.title,
          description: this.post.description
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
            // console.log(this.$session.get('post')['type']),
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

    switchpost() {
      this.tab = true;
    },

    switchsche() {
      axios({
        method: "get",
        url: `//192.168.31.114:8399/schedule/selectBySeq/${
          this.$session.get("user")["seq"]
        }`
      }).then(response => {
        // console.log(response['data'])
        this.schedules = response["data"];
        // this.schedules.push(response['data'][0])
      });
      this.tab = false;
    },

    sopenItem: function(item) {
      router.push({ path: `schedule/${item["scdNo"]}` });
    },

    sdeleteItem: async function(item) {
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
        this.switchsche();
      }
    },

    seditItem(item) {
      this.schedule.seq = item["seq"];
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

    seditSave: async function() {
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
            this.switchsche()
          )
        )
        .catch(error => {
          console.log(error);
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    }
  }
};
</script>
<style>
</style>
