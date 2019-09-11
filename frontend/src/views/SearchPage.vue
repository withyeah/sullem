<template>
<div>
    <v-sheet class="teal accent-4" height="150" tile>
      <v-layout align-center justify-center fill-height>
        <v-flex xs6 sm4>
          <v-text-field
            solo
            label="장소 입력"
            clearable
            color="teal accent-4"
            class="font-weight-bold"
            append-icon="fa-search"
            @click:append="search()"
            @keyup.enter="search()"
            v-model="text"
          ></v-text-field>
        </v-flex>
      </v-layout>
    </v-sheet>

    <v-container grid-list-md mt-3>
      <v-layout wrap>
        <v-flex xs12 class="hidden-sm-and-up">
          <v-tabs centered grow color="teal accent-4">
          <v-tab @click="selectCategory(0)">여행일정</v-tab>
          <v-tab @click="selectCategory(1)">여행후기</v-tab>
        </v-tabs>

        </v-flex>
        <v-flex sm3 class="hidden-xs-only">
          <v-card
            class="mx-auto"
            max-width="300"
            tile
          >
          <v-list rounded>
            <v-subheader>Category</v-subheader>
            <v-list-item-group v-model="category" color="teal accent-4" mandatory>
              <v-list-item
                v-for="(item, i) in items"
                :key="i"
                 @click="selectCategory(i)"
              >
                <!-- <v-list-item-icon>
                  <v-icon v-text="item.icon"></v-icon>
                </v-list-item-icon> -->
                <v-list-item-content>
                  <v-list-item-title v-text="item.text" class="font-weight-bold"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>

        </v-flex>
        <v-flex xs12 sm9>
          <v-layout wrap>
            <v-flex xs12>
                <search-item-list
                v-if="results"
                 :results="results"
                 :num='page'
                 :category='category'
                 :endpage='endpage'
                 @getPage="paging"
                ></search-item-list>
            </v-flex>

          </v-layout>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import SearchItemList from '../components/Search/SearchItemList.vue'
import axios from 'axios'
export default {
  components: {
    SearchItemList
  },
  data: () => ({
    category: 0,
    items: [
      { text: '여행일정' },
      { text: '여행후기' }
    ],
    text: '',
    results: null,
    page: 1,
    endpage: 1
  }),
  mounted () {
    if (this.$route.params.text === undefined && this.$route.params.category === undefined) {
      this.text = this.$store.state.text
      this.category = this.$store.state.category
      this.page = this.$store.state.page
    } else {
      this.text = this.$route.params.text
      this.category = this.$route.params.category
      this.page = this.$route.params.page
    }

    this.search()
  },
  methods: {
    search () {
      this.$store.state.page = this.page
      this.$store.state.category = this.category
      this.$store.state.text = this.text

      this.page = this.page === '' ? 1 : this.page
      this.category = this.category === '' ? 0 : this.category

      if (this.category === 0) {
        this.getScheduleList()
      } else if (this.category === 1) {
        this.getPostList()
      }
    },
    selectCategory (item) {
      this.text = ''
      this.page = 1
      this.category = item
      this.search()
    },
    getScheduleList () {
      var query = this.text === '' ? ' ' : this.text
      axios.get('http://192.168.31.114:8399/schedule/selectPage/' + query + '/' + this.page)
        .then(response => {
          this.results = response.data.list
          this.endpage = response.data.endPage
        }
        )
        .catch(function (error) {
          console.log(error)
        })
    },
    getPostList () {
      var query = this.text === '' ? ' ' : this.text
      axios.get('http://192.168.31.114:8399/post/selectPage/' + query + '/' + this.page)
        .then(response => {
          this.results = response.data.postList
          this.endpage = response.data.endPage
        }
        )
        .catch(function (error) {
          console.log(error)
        })
    },
    paging (page) {
      this.page = page
      this.search()
    }
  }
}
</script>
