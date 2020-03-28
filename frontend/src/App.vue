<template>
  <v-app>
    <v-toolbar color="primary" max-height="75" dark>
      <v-toolbar-title
        ><v-icon>{{ "mdi-phone" }}</v-icon
        >Call Center APP</v-toolbar-title
      >
    </v-toolbar>
    <LoginWindow v-if="showData" />
    <Abonent v-else />
    <BottomBar />
  </v-app>
</template>

<script>
import BottomBar from './components/BottomBar';
import Abonent from './components/Abonent';
import LoginWindow from './components/LoginWindow';
export default {
  name: 'App',
  components: {
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
