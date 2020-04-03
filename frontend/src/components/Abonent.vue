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
          <template v-slot:item.phone="props" >
            <v-btn
                    v-on="on"
              @click="call()"
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
              @click="call()"
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
              @click="call()"
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
    <v-btn @click="terminatecall()">
      Завершить
    </v-btn>
    <audio id="localAudio" autoPlay muted></audio>
    <audio id="remoteAudio" autoPlay></audio>

  </v-container>
</template>

<script>
import * as JsSIP from 'jssip';
const axios = require('axios');
export default {
  name: 'Abonent',
  showData: false,
  configuration: '',
  ua: '',
  mounted() {
    const socket = new JsSIP
        .WebSocketInterface('wss://25.118.246.153:8089/ws');
    socket.via_transport='wss';
    // socket.sip_uri='sip:101@25.118.246.153';
    socket.connect();
    console.log(socket.sip_uri);
    this.configuration = {
      sockets: [socket],
      uri: 'sip:101@25.118.246.153',
      password: '123',
      realm: 'asterisk.org',
    };

    this.ua = new JsSIP.UA(this.configuration);

    this.ua.start();

    this.ua.on('registrationFailed', (data) => {
      this.snack = true;
      this.snackColor = 'error';
      this.snackText = 'Ошибка голосового клиента';
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
    });
  },
  methods: {
    save(item) {
      this.snack = true;
      this.snackColor = 'success';
      this.snackText = 'Данные сохранены';
      axios({
        method: 'PATCH',
        url: item._links.self.href,
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
        url: item._links.self.href,
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
    call() {
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

      this.session = this.ua.call('102@25.118.246.153', options);

      this.session.on('progress', () => {
        console.log('UA session progress');
        // playSound('audio/ringbacktone.mp3', true);
      });

      this.session.on('failed', (data) => {
        console.log('UA session failed');
        // stopSound('audio/ringbacktone.mp3');
        // playSound('audio/rejected.mp3', false);
      });


      this.session.on('connecting', () => {
        console.log('UA session connecting');
        // playSound('audio/ringbacktone.mp3', true);
      });

      this.session.on('ended', () => {
        console.log('UA session ended');
        // playSound('audio/rejected.mp3', false);
        JsSIP.Utils.closeMediaStream(this._localClonedStream);
      });

      // Звонок принят, моно начинать говорить
      this.session.on('confirmed', () => {
        console.log('UA session accepted');


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


        // stopSound('audio/ringbacktone.mp3');
        // playSound('audio/answered.mp3', false);
      });
      console.log('click');
    },
    terminatecall() {
      this.ua.terminateSessions();
      JsSIP.Utils.closeMediaStream(this._localClonedStream);
    },
  },

  data() {
    return {
      dialog: false,
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
