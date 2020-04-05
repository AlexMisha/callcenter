<template>
  <v-app>
  <v-container>
    <v-dialog v-model="dialog" persistent max-width="290">
      <v-card>
        <v-progress-circular
                :size="50"
                color="primary"
                indeterminate
                style="margin: 1rem;"
        ></v-progress-circular>Подключение к серверу
      </v-card>
    </v-dialog>
    <v-tabs fixed-tabs v-if="dialog==false">
      <v-tab>Клиенты</v-tab>
      <v-tab-item>
        <v-data-table
          v-bind:key="clients"
          :items="clients"
          v-for="clients in info"
          :headers="headers"
        >
          <template v-slot:item.phone="props">
            <v-dialog v-model="dialogCall" persistent max-width="290">
              <template  v-slot:activator="{ on }">
                <v-btn
                        @click="call(props.item)"
                        rounded
                        color="success"
                        v-on="on"
                        :return-value.sync="props.item.phone"
                >
                  <v-icon>{{ "mdi-phone" }}</v-icon>
                  Звонок
                </v-btn>
              </template>
              <v-card>
                <v-card-title class="headline">
                  Идет звонок
                </v-card-title>
                <v-card-text>
                  Вызываемый абонент: {{abonent}}
                  <br>
                  {{callTime}}
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                          rounded
                          color="error"
                          @click="terminatecall()">
                    Завершить
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
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
            <v-dialog v-model="dialogCall" persistent max-width="290">
              <template  v-slot:activator="{ on }">
                <v-btn
                        @click="call(props.item)"
                        rounded
                        color="success"
                        v-on="on"
                        :return-value.sync="props.item.phone"
                >
                  <v-icon>{{ "mdi-phone" }}</v-icon>
                  Звонок
                </v-btn>
              </template>
              <v-card>
                <v-card-title class="headline">
                  Идет звонок
                </v-card-title>
                <v-card-text>
                  Вызываемый абонент: {{abonent}}
                  <br>
                  {{callTime}}
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                          rounded
                          color="error"
                          @click="terminatecall()">
                    Завершить
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
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
            <v-dialog v-model="dialogCall" persistent max-width="290">
            <template  v-slot:activator="{ on }">
            <v-btn
              @click="call(props.item)"
              rounded
              color="success"
              v-on="on"
              :return-value.sync="props.item.phone"
            >
              <v-icon>{{ "mdi-phone" }}</v-icon>
              Звонок
            </v-btn>
            </template>
            <v-card>
              <v-card-title class="headline">
                Идет звонок
              </v-card-title>
              <v-card-text>
                Вызываемый абонент: {{abonent}}
                <br>
                {{callTime}}
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                        rounded
                        color="error"
                        @click="terminatecall()">
                  Завершить
                </v-btn>
              </v-card-actions>
            </v-card>
            </v-dialog>
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
      <audio id="localAudio" autoPlay muted></audio>
      <audio id="remoteAudio" autoPlay></audio>
  </v-app>
</template>

<script>
import * as JsSIP from 'jssip';
const axios = require('axios');
export default {
  name: 'Abonent',
  showData: false,
  configuration: '',
  abonent: '',
  callTime: '',
  ua: '',
  mounted() {
    const socket = new JsSIP
        .WebSocketInterface('wss://25.118.246.153:8089/ws');
    socket.via_transport='wss';
    socket.connect();
    console.log(socket.sip_uri);
    this.configuration = {
      sockets: [socket],
      uri: 'sip:' + sessionStorage.phone + '@25.118.246.153',
      password: '123',
      realm: 'asterisk.org',
    };

    this.ua = new JsSIP.UA(this.configuration);

    this.ua.start();

    this.ua.on('registrationFailed', (data) => {
      this.snack = true;
      this.snackColor = 'error';
      this.snackText = 'Ошибка регистрации';
      console.error('UA registrationFailed', data.cause);
    });

    this.ua.on('registered', () => {
      axios({
        method: 'GET',
        url: 'api/clients/search/findByDoNotCallIsNullOrDoNotCallIsFalse',
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': sessionStorage.token,
        },
      })
          .then((response) => (this.info = response.data._embedded))
          .catch(
              (error) => (
                (this.snack = true),
                (this.snackColor = 'error'),
                (this.snackText = 'Ошибка подключения к серверу')
              ),
          );
      axios({
        method: 'GET',
        url: 'clients/findNotCalledToday',
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': sessionStorage.token,
        },
      })
          .then((response) => (this.infoToday = response))
          .catch(
              (error) => (
                (this.snack = true),
                (this.snackColor = 'error'),
                (this.snackText = 'Ошибка подключения к серверу')
              ),
          );
      axios({
        method: 'GET',
        url: 'clients/findNeverCalled',
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': sessionStorage.token,
        },
      })
          .then((response) => (this.infoNever = response))
          .catch(
              (error) => (
                (this.snack = true),
                (this.snackColor = 'error'),
                (this.snackText = 'Ошибка подключения к серверу')
              ),
          );
      console.log('UA registered');
      this.dialog=false;
    });
  },
  methods: {
    save(item) {
      this.snack = true;
      this.snackColor = 'success';
      this.snackText = 'Данные сохранены';
      axios({
        method: 'PATCH',
        url: '/api/clients/'+item.id,
        data: {orderComment: item.orderComment},
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': sessionStorage.token,
        },
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
      axios({
        method: 'PATCH',
        url: '/api/clients/'+item.id,
        data: {doNotCall: true},
        headers: {
          'Content-Type': 'application/json',
          'Accept': '*/*',
          'Authorization': sessionStorage.token,
        },
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
    call(item) {
      this.abonent = item.lastName +
              ' ' + item.firstName +
              ' ' + item.middleName;
      const options = {
        'pcConfig':
                {
                  hackStripTcp: true,
                  iceServers: [],
                },
        'mediaConstraints': {'audio': true, 'video': false},
        'rtcOfferConstraints':
                {
                  'offerToReceiveAudio': 1, // Принимаем только аудио
                  'offerToReceiveVideo': 0,
                },
      };

      this.session = this.ua.call(item.phone + '@25.118.246.153', options);

      this.session.on('progress', () => {
        this.callTime = 'Соединяем...';
        console.log('UA session progress');
      });

      this.session.on('failed', (data) => {
        console.log('UA session failed');
        this.callTime = 'Звонок не удался';
      });


      this.session.on('connecting', () => {
        console.log('UA session connecting');

        const peerconnection = this.session.connection;
        console.log(peerconnection);
        const localStream = peerconnection.getLocalStreams()[0];
        console.log(localStream);
        // Handle local stream
        if (localStream) {
          // Clone local stream
          this._localClonedStream = localStream.clone();

          console.log('UA set local stream');

          const localAudioControl = document.getElementById('localAudio');
          localAudioControl.srcObject = this._localClonedStream;
        }

        peerconnection.addEventListener('addstream', (event) => {
          console.log('UA session addstream');

          const remoteAudioControl = document.getElementById('remoteAudio');
          remoteAudioControl.srcObject = event.stream;
        });
      });

      this.session.on('ended', () => {
        console.log('UA session ended');
        this.callTime = 'Звонок завершен';
        JsSIP.Utils.closeMediaStream(this._localClonedStream);
      });

      // Звонок принят, моно начинать говорить
      this.session.on('confirmed', () => {
        console.log('UA session accepted');
      });
    },
    terminatecall() {
      this.dialogCall = false;
      this.ua.terminateSessions();
      JsSIP.Utils.closeMediaStream(this._localClonedStream);
    },
  },

  data() {
    return {
      dialog: true,
      dialogCall: false,
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
