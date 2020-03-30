<template>
  <v-container>
    <v-tabs fixed-tabs>
      <v-tab>Клиенты</v-tab>
      <v-tab-item>
        <v-data-table
          v-bind:key="clients"
          :items="clients"
          v-for="clients in info"
          :headers="headers"
        >
          <template v-slot:item.phone="props">
            <v-btn
              @call="call"
              rounded
              color="success"
              :return-value.sync="props.item.phone"
            >
              <v-icon>{{ "mdi-phone" }}</v-icon>
              Звонок
            </v-btn>
          </template>
          <template v-slot:item.doNotCall="props">
            <v-btn
              @click="block(props.item)"
              rounded
              color="error"
              :return-value.prop="props.item.doNotCall"
            >
              <v-icon>{{ "mdi-block" }}</v-icon>
              Больше не вызывать
            </v-btn>
          </template>
          <template v-slot:item.orderComment="props">
            <v-edit-dialog
              :return-value.sync="props.item.orderComment"
              large
              persistent
              @save="save(props.item)"
              @cancel="cancel"
              @open="open"
            >
              <div>{{ props.item.orderComment }}</div>
              <template v-slot:input>
                <div class="mt-4 title">Редактировать комментарий</div>
              </template>
              <template v-slot:input>
                <v-text-field
                  v-model="props.item.orderComment"
                  label="Редактировать"
                  single-line
                  counter
                  autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>
        </v-data-table>
      </v-tab-item>
      <v-tab>Клиенты, которым сегодня не звонили</v-tab>
      <v-tab-item>
        <v-data-table
          v-bind:key="clients"
          :items="clients"
          v-for="clients in infoToday"
          :headers="headers"
        >
          <template v-slot:item.phone="props">
            <v-btn
              @call="call"
              rounded
              color="success"
              :return-value.sync="props.item.phone"
            >
              <v-icon>{{ "mdi-phone" }}</v-icon>
              Звонок
            </v-btn>
          </template>
          <template v-slot:item.doNotCall="props">
            <v-btn
              @click="block(props.item)"
              rounded
              color="error"
              :return-value.prop="props.item.doNotCall"
            >
              <v-icon>{{ "mdi-block" }}</v-icon>
              Больше не вызывать
            </v-btn>
          </template>
          <template v-slot:item.orderComment="props">
            <v-edit-dialog
              :return-value.sync="props.item.orderComment"
              large
              persistent
              @save="save(props.item)"
              @cancel="cancel"
              @open="open"
            >
              <div>{{ props.item.orderComment }}</div>
              <template v-slot:input>
                <div class="mt-4 title">Редактировать комментарий</div>
              </template>
              <template v-slot:input>
                <v-text-field
                  v-model="props.item.orderComment"
                  label="Редактировать"
                  single-line
                  counter
                  autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>
        </v-data-table>
      </v-tab-item>
      <v-tab>Клиенты, которым никогда не звонили</v-tab>
      <v-tab-item>
        <v-data-table
          v-bind:key="clients"
          :items="clients"
          v-for="clients in infoNever"
          :headers="headers"
        >
          <template v-slot:item.phone="props">
            <v-btn
              @call="call"
              rounded
              color="success"
              :return-value.sync="props.item.phone"
            >
              <v-icon>{{ "mdi-phone" }}</v-icon>
              Звонок
            </v-btn>
          </template>
          <template v-slot:item.doNotCall="props">
            <v-btn
              @click="block(props.item)"
              rounded
              color="error"
              :return-value.prop="props.item.doNotCall"
            >
              <v-icon>{{ "mdi-block" }}</v-icon>
              Больше не вызывать
            </v-btn>
          </template>
          <template v-slot:item.orderComment="props">
            <v-edit-dialog
              :return-value.sync="props.item.orderComment"
              large
              persistent
              @save="save(props.item)"
              @cancel="cancel"
              @open="open"
            >
              <div>{{ props.item.orderComment }}</div>
              <template v-slot:input>
                <div class="mt-4 title">Редактировать комментарий</div>
              </template>
              <template v-slot:input>
                <v-text-field
                  v-model="props.item.orderComment"
                  label="Редактировать"
                  single-line
                  counter
                  autofocus
                ></v-text-field>
              </template>
            </v-edit-dialog>
          </template>
        </v-data-table>
      </v-tab-item>
    </v-tabs>
    <v-snackbar v-model="snack" :timeout="3000" :color="snackColor">
      {{ snackText }}
      <v-btn text @click="snack = false">Закрыть</v-btn>
    </v-snackbar>
  </v-container>
</template>

<script>
const axios = require('axios');
export default {
  name: 'Abonent',
  mounted() {
    axios
        .get(
            'http://localhost:8080/api/clients/search/findByDoNotCallIsNullOrDoNotCallIsFalse',
        )
        .then((response) => (this.info = response.data._embedded))
        .catch(
            (error) => (
              (this.snack = true),
              (this.snackColor = 'error'),
              (this.snackText = 'Ошибка подключения к серверу')
            ),
        );
    axios
        .get('http://localhost:8080/clients/findNotCalledToday')
        .then((response) => (this.infoToday = response))
        .catch(
            (error) => (
              (this.snack = true),
              (this.snackColor = 'error'),
              (this.snackText = 'Ошибка подключения к серверу')
            ),
        );
    axios
        .get('http://localhost:8080/clients/findNeverCalled')
        .then((response) => (this.infoNever = response))
        .catch(
            (error) => (
              (this.snack = true),
              (this.snackColor = 'error'),
              (this.snackText = 'Ошибка подключения к серверу')
            ),
        );
  },
  methods: {
    save(item) {
      this.snack = true;
      this.snackColor = 'success';
      this.snackText = 'Данные сохранены';
      axios
          .patch(item._links.self.href, {
            orderComment: item.orderComment,
          })
          .then(function(response) {
            console.log(response);
          })
          .catch(
              (error) => (
                console.log(error),
                (this.snack = true),
                (this.snackColor = 'error'),
                (this.snackText = 'Ошибка сохранения данных')
              ),
          );
    },
    cancel() {
      this.snack = true;
      this.snackColor = 'error';
      this.snackText = 'Отмена';
    },
    open() {
      this.snack = true;
      this.snackColor = 'info';
      this.snackText = 'Окно открыто';
    },
    block(item) {
      axios
          .patch(item._links.self.href, {
            doNotCall: true,
          })
          .then(function(response) {
            console.log(response);
          })
          .catch(
              (error) => (
                console.log(error),
                (this.snack = true),
                (this.snackColor = 'error'),
                (this.snackText = 'Ошибка сохранения данных')
              ),
          );
      this.snack = true;
      this.snackColor = 'success';
      this.snackText = 'Абонент больше не будет вызываться';
    },
    call() {},
  },
  data() {
    return {
      Client: [],
      info: null,
      infoToday: null,
      infoNever: null,
      snack: false,
      snackColor: '',
      snackText: '',
      pagination: {},
      headers: [
        {
          text: 'Фамилия',
          align: 'left',
          value: 'lastName',
        },
        {text: 'Имя', value: 'firstName'},
        {text: 'Отчество', value: 'middleName'},
        {text: '', value: 'phone', sortable: false, width: 1},
        {text: '', value: 'doNotCall', sortable: false, width: 1},
        {text: 'Комментарий', value: 'orderComment'},
      ],
    };
  },
};
</script>
