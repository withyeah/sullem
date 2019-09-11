<template>
  <v-dialog v-model="this.$store.state.FindPassword" persistent max-width="600px">
    <v-card>
      <v-card-title>
        <span class="headline">Find Password</span>
      </v-card-title>
      <v-card-text>
        <v-container grid-list-md>
          <v-layout wrap>
            <v-flex xs12>
              <v-text-field v-model="user.email" label="Email*" required></v-text-field>
            </v-flex>
          </v-layout>
        </v-container>
        <small>*indicates required field</small>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="cancel">Close</v-btn>
        <v-btn color="blue darken-1" text @click="findPassword">Send</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from 'axios';
export default {
  name: 'FindPassword',
  data () {
    return {
      user: {
        email: null
      }
    }
  },
  methods: {
    cancel () {
      this.$store.state.FindPassword = false
    },
    findPassword () {
      axios
        .get('http://192.168.31.114:8399/member/findPassword/' + this.user.email)
        .catch(error => {
          console.log(error)
          this.errored = true
        })
      this.user.email = null
      alert('전송되었습니다.')
      this.cancel()
    }
  }
}
</script>
