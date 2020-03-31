<template>
  <v-container>
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
  </v-container>
</template>

<script>
const axios = require('axios');
export default {
  name: 'CDR',
  data() {
    return {
      info: null,
      headers: [
        {
          text: 'accountCode',
          align: 'left',
          value: 'accountCode',
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
        'Authorization': localStorage.token,
      },
    }).then((response) => (this.info = response.data._embedded));
  },
  methods: {
    download(filename) {
      axios({
        method: 'GET',
        url: 'downloadCallRecord/' + filename,
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': localStorage.token,
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
  },
};
</script>
