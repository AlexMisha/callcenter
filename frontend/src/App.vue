<template>
  <v-app>
    <HeadlineBar/>
    <div v-if="showData"><Calls/></div>
    <div v-else>
      <v-content>
        <v-card
              class="py-12 mx-auto"
              width=33%
              flat>
        <v-card-text class="headline
                            text-center"
                     v-text="'Вход в систему Call-центра'"/>
        <v-spacer/>
        <v-text-field label="Логин"
                      hide-details="auto"
                      autofocus
                      outlined
                      clearable
                      rounded
        />
        <v-spacer class="ma-6"></v-spacer>
        <v-text-field label="Пароль"
                      outlined
                      clearable
                      rounded
                      :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                      :type="show ? 'text' : 'password'"
                      @click:append="show = !show"/>

        <v-btn
                class="ma-auto"
                rounded
                @click="snackbar = true,
                showData=true"
        >Вход</v-btn>
      </v-card>
      <v-snackbar
              v-model="snackbar"
              timeout=2000
              top
              color=error
      >{{ text }}</v-snackbar>
    </v-content>
    </div>
    <BottomBar/>
  </v-app>
</template>

<script>
import HeadlineBar from "./components/HeadlineBar";
import BottomBar from "./components/BottomBar";
import Calls from "./Calls";

export default {
  name: 'App',
  components: {
    Calls,
    BottomBar,
    HeadlineBar,
  },
  data: () => ({
    login: '',
    showData: false,
    show: false,
    snackbar: false,
    y: 'top',
    text: 'Неверный логин или пароль'
  }),
};

</script>

