<template>
  <v-app>
    <v-toolbar color="primary" max-height="75" dark flat>
      <v-toolbar-title
        ><v-icon>{{ "mdi-phone" }}</v-icon
        >Call Center APP</v-toolbar-title
      >
      <v-spacer></v-spacer>
      {{welcome}}
      <v-btn
              v-if="showData"
              rounded
              dark
      @click="unlog()">
        Выход
      </v-btn>
    </v-toolbar>
    <v-content v-if="!showData">
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
        <v-dialog v-model="dialog" persistent max-width="290">
          <template v-slot:activator="{ on }">
            <v-btn  v-on="on"
                    class="ma-auto"
                    rounded
                    @click="log()">Вход</v-btn>
          </template>
          <v-card>
          <v-progress-circular
                  :size="50"
                  color="primary"
                  indeterminate
                  style="margin: 1rem;"
          ></v-progress-circular>Авторизация...
          </v-card>
        </v-dialog>
      </v-card>
      <v-snackbar
              v-model="snackbar"
              :timeout="2000"
              top
              color="error">
        {{ text }}
      </v-snackbar>
    </v-content>
    <v-tabs v-else fixed-tabs>
      <v-tab>
        Вызвать
      </v-tab>
      <v-tab-item>
        <Abonent/>
      </v-tab-item>
      <v-tab>
        Звонки
      </v-tab>
      <v-tab-item>
        <CDR/>
      </v-tab-item>
    </v-tabs>
    <BottomBar />
  </v-app>
</template>

<script>
import BottomBar from './components/BottomBar';
import CDR from './components/CDR';
import Abonent from './components/Abonent';
const axios = require('axios');
export default {
  name: 'App',
  components: {
    BottomBar,
    Abonent,
    CDR,
  },
  data() {
    return {
      welcome: '',
      login: '',
      password: '',
      showData: false,
      show: false,
      dialog: false,
      snackbar: false,
      status: '',
      y: 'top',
      text: 'Неверный логин или пароль',
    };
  },

  mounted() {
    if ((sessionStorage.token)&&(sessionStorage.login)) {
      this.dialog=true;
      axios({
        method: 'GET',
        url: '/api/operators/search/findByLogin?login=' + sessionStorage.login,
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': sessionStorage.token,
        },
      })
          .then((response) =>
            ((sessionStorage.phone = response.data.phone),
            (this.dialog = false),
            (this.showData = true),
            (this.welcome =
            'Добро пожаловать, ' + response.data.firstName),
            (sessionStorage.login = response.data.login)
            ))
          .catch(
              () => (
                (this.snackbar = true),
                (this.dialog = false),
                (this.text = 'Ошибка авторизации. Пожалуйста повторите попытку')
              ),
          );
    } else {
      this.dialog = false;
    }
  },
  methods: {
    log() {
      axios({
        method: 'POST',
        url: '/login',
        headers: {'Content-Type': 'application/json', 'Accept': '*/*'},
        data: {login: this.login, password: this.password},
      }).then(
          (response) =>(
            (sessionStorage.token = response.headers.authorization),
            (this.status = response.status),
            (axios({
              method: 'GET',
              url: '/api/operators/search/findByLogin?login=' + this.login,
              headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*',
                'Authorization': sessionStorage.token,
              },
            })
                .then((response) =>
                  ((sessionStorage.phone = response.data.phone),
                  (this.dialog=false),
                  (this.showData=true),
                  (this.welcome =
                          'Добро пожаловать, ' + response.data.firstName),
                  (sessionStorage.login = response.data.login)
                  ))
            ))).catch(
          () => (
            (this.snackbar = true),
            (this.dialog = false)
          ),
      );
    },
    unlog() {
      this.showData = false;
      this.welcome = '';
      sessionStorage.token = '';
      sessionStorage.phone = '';
      sessionStorage.login = '';
    },
  },
};
</script>
