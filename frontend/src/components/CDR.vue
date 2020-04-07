<template>
  <v-container>
    <v-dialog v-model="dialog" persistent max-width="290">
      <v-card>
        <v-progress-circular
                :size="50"
                color="primary"
                indeterminate
                style="margin: 1rem;"
        ></v-progress-circular>{{loading}}
      </v-card>
    </v-dialog>
    <v-btn
            @click="update()"
            rounded
            color="primary">
      Обновить
    </v-btn>
    <div v-if="dialog==false">
    <v-data-table
      v-bind:key="cdr"
      :items="cdr"
      v-for="cdr in info"
      :headers="headers"
    >
      <template v-slot:item.fileName="props">
        <v-btn
          @click="download(props.item.fileName)"
          rounded
          color="primary"
          :return-value.prop="props.item.fileName"
        >
          <v-icon>{{ "mdi-download" }}</v-icon>
          Скачать файл звонка
        </v-btn>
      </template>
    </v-data-table>
    </div>
    <v-snackbar
            v-model="snackbar"
            :timeout="2000"
            top
            color="error">
      {{ text }}
    </v-snackbar>
  </v-container>
</template>

<script>
const axios = require('axios');
export default {
  name: 'CDR',
  info: null,
  data() {
    return {
      loading: 'Подключение к серверу',
      snackbar: false,
      y: 'top',
      text: '',
      dialog: true,
      headers: [
        {
          text: 'Оператор',
          align: 'left',
          value: 'src',
        },
        {
          text: 'Клиент',
          align: 'left',
          value: 'dst',
        },
        {text: 'Date', value: 'callDate'},
        {text: 'Duration', value: 'duration'},
        {text: '', value: 'fileName', sortable: false, width: 1},
      ],
    };
  },
  mounted() {
    axios({
      method: 'GET',
      url: 'api/cdr',
      headers: {
        'Content-Type': 'application/json',
        'Accept': '*/*',
        'Authorization': sessionStorage.token,
      },
    }).then((response) => (
      (this.info = response.data._embedded),
      (this.dialog = false)))
        .catch(() => (
          (this.snackbar = true),
          (this.dialog = false),
          (this.text = 'Ошибка загрузка записей')
        ));
  },
  methods: {
    download(filename) {
      axios({
        method: 'GET',
        url: 'downloadCallRecord/' + filename,
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': sessionStorage.token,
        },
        responseType: 'blob',
      }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', filename);
        document.body.appendChild(link);
        link.click();
      });
    },
    update() {
      this.loading = 'Обновление списка';
      this.dialog = true;
      axios({
        method: 'GET',
        url: 'api/cdr',
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': sessionStorage.token,
        },
      }).then((response) => (
        (this.info = response.data._embedded),
        (this.dialog = false)))
          .catch(() => (
            (this.snackbar = true),
            (this.dialog = false),
            (this.text = 'Ошибка загрузка записей')
          ));
    },
  },
};
</script>
