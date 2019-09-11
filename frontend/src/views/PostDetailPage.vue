<template>
  <v-container grid-list-md>
    <v-layout wrap>
      <v-flex xs12 ml-6 mr-6>
        <span class="grey--text">{{post.createDate.substring(0,16)}}</span>
      </v-flex>
      <v-flex xs12 ml-5 mr-5>
        <span class="display-1 font-weight-black">{{post.title}}</span>
      </v-flex>
      <v-flex xs12 ml-8 mr-8 mb-4>
        <span class="title font-weight-thin">{{post.description}}</span>
      </v-flex>
      <v-flex xs12 class="text-right" mb-1>
        <v-btn class="font-weight-bold" color="teal accent-4" dark @click="$router.go(-1)">목록으로</v-btn>&nbsp;
        <v-btn
          v-if="seq === post.seq"
          class="font-weight-bold"
          color="teal accent-4"
          dark
          @click="updatePage"
        >수정하기</v-btn>&nbsp;
        <v-btn
          v-if="seq === post.seq"
          class="font-weight-bold"
          color="teal accent-4"
          dark
          @click="deletePost"
        >삭제하기</v-btn>
      </v-flex>
      <v-flex xs12>
        <v-sheet elevation="5">
          <v-layout>
            <v-flex xs12 ma-5>
              <div v-html="post.content"></div>
            </v-flex>
          </v-layout>
        </v-sheet>
      </v-flex>
      <v-flex xs12 mt-2>
        <v-sheet elevation="5">
          <v-layout>
            <v-flex xs12 ma-5>
              <div id="disqus_thread"></div>
            </v-flex>
          </v-layout>
        </v-sheet>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      post: {
        no: 0,
        title: "",
        description: "",
        content: "",
        createDate: "",
        disqus_config: "",
        seq: ""
      },
      seq: "",
      no: 0
    };
  },
  created() {},
  mounted() {
    if (this.$session.get("user") != undefined) {
      this.seq = this.$session.get("user")["seq"];
    }
    this.no = this.$route.params.no;
    axios
      .get("http://192.168.31.114:8399/post/detailPost/" + this.no)
      .then(response => {
        console.log(response.data);
        this.post = response.data;
      })
      .catch(function(error) {
        console.log(error);
      });

    this.disqus_config = function() {
      this.page.url = "http://192.168.31.129:8080/post/" + this.no; // Replace PAGE_URL with your page's canonical URL variable
      this.page.identifier = this.no; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
    };

    let recaptchaScript = document.createElement("script");
    recaptchaScript.setAttribute(
      "src",
      "https://happyhacking-1.disqus.com/embed.js"
    );
    document.head.appendChild(recaptchaScript);
  },
  methods: {
    deletePost() {
      if (confirm("포스트를 정말로 삭제하시겠습니까?")) {
        axios
          .delete("http://192.168.31.114:8399/post/delete/" + this.no)
          .then(response => {
            console.log(response.data);
            if (response.data === 1) {
              this.$router.go(-1);
            } else {
              // alert("삭제에 실패했습니다.");
              this.$notify({
                group: "foo",
                title: "Delete failed",
                text: "삭제에 실패했습니다.",
                type: "error",
                duration: 2000
              });
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    updatePage() {
      this.$router.push({
        name: "postWriter",
        params: { no: this.no, post: this.post }
      });
    }
  }
};
</script>

<style>
img {
  width: 100%;
  height: auto;
}
</style>
