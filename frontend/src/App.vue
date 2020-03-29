<template>
  <v-app>
    <v-toolbar color="primary" max-height="75" dark>
      <v-toolbar-title
        ><v-icon>{{ "mdi-phone" }}</v-icon
        >Call Center APP</v-toolbar-title
      >
    </v-toolbar>
    <LoginWindow v-if="showData" />
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
import Abonent from './components/Abonent';
import LoginWindow from './components/LoginWindow';
import CDR from './components/CDR';
export default {
  name: 'App',
  components: {
    CDR,
    LoginWindow,
    Abonent,
    BottomBar,
  },
  data: () => ({
    login: '',
    showData: false,
    snackbar: false,
    y: 'top',
    text: 'Неверный логин или пароль',
  }),
  methods: {
    unLog() {
      this.showData = false;
      console.log('clicked');
    },
    log() {
      this.showData = true;
      console.log(this.showData);
    },
  },
};
</script>
