<template>
  <v-content>
    <v-card class="py-12 mx-auto" width="33%" flat>
      <v-card-text
        class="headline
                            text-center"
        v-text="'Вход в систему Call-центра'"
      />
      <v-spacer />
      <v-text-field
        label="Логин"
        hide-details="auto"
        autofocus
        outlined
        clearable
        rounded
      />
      <v-spacer class="ma-6"></v-spacer>
      <v-text-field
        label="Пароль"
        outlined
        clearable
        rounded
        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
        :type="show ? 'text' : 'password'"
        @click:append="show = !show"
      />

      <v-btn
        class="ma-auto"
        rounded
        @click="Login()"
        >Вход</v-btn
      >
    </v-card>
    <v-snackbar v-model="snackbar" timeout="2000" top color="error">{{
      text
    }}</v-snackbar>
  </v-content>
</template>

<script>
import App from '../App';
export default {
  name: 'LoginWindow',
  methods: {
    Login() {
      App.$emit.log();
    },
  },
};

</script>

<style scoped></style>
