<template>
  <v-app>
    <v-toolbar color="primary" max-height="75" dark flat>
      <v-toolbar-title
        ><v-icon>{{ "mdi-phone" }}</v-icon
        >Call Center APP</v-toolbar-title
      >
    </v-toolbar>
    <v-content>
      <v-card class="py-12 mx-auto" width="33%" flat>
        <v-card-text
          class="headline
                            text-center"
          v-text="'Вход в систему Call-центра'"
        />
        <v-spacer />
        <v-text-field
          v-model="login"
          label="Логин"
          hide-details="auto"
          autofocus
          outlined
          clearable
          rounded
        />
        <v-spacer class="ma-6"></v-spacer>
        <v-text-field
          v-model="password"
          label="Пароль"
          outlined
          clearable
          rounded
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          @click:append="show = !show"
        />

        <v-btn class="ma-auto" rounded @click="log()">Вход</v-btn>
      </v-card>
      <v-snackbar v-model="snackbar" timeout="2000" top color="error">{{
        text
      }}</v-snackbar>
    </v-content>
    <BottomBar />
  </v-app>
</template>

<script>
import BottomBar from './components/BottomBar';
const axios = require('axios');
export default {
  name: 'App',
  components: {
    BottomBar,
  },
  data: () => ({
    login: '',
    password: '',
    showData: true,
    snackbar: false,
    y: 'top',
    text: 'Неверный логин или пароль',
  }),
  methods: {
    log() {
      axios({
        method: 'POST',
        url: 'http://localhost:8080/login',
        headers: {'Content-Type': 'application/json'},
        data: {login: this.login, password: this.password},
      }).catch(
          (error) => (
            (this.snack = true),
            (this.snackColor = 'error'),
            (this.snackText = 'Ошибка подключения к серверу')
          ),
      );
    },
  },
};
</script>
