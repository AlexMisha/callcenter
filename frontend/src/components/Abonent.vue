<template>
    <v-container>
        <v-data-table
                :headers="headers"
                :items="Client">
            <template v-slot:item.phone="props">
                <v-btn
                        @call="call"
                        rounded
                        color="success"
                        :return-value.sync="props.item.phone">
                    <v-icon>{{'phone'}}</v-icon>
                    Звонок
                </v-btn>
            </template>
            <template v-slot:item.doNotCall="props">
                <v-btn
                        @click="block"
                        rounded
                        color="error"
                        :return-value.prop="props.item.doNotCall">
                    <v-icon>{{'block'}}</v-icon>
                    Больше не вызывать
                </v-btn>
            </template>
            <template v-slot:item.orderComment="props">
                <v-edit-dialog
                        :return-value.sync="props.item.orderComment"
                        large
                        persistent
                        @save="save"
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

        <v-snackbar v-model="snack" :timeout="3000" :color="snackColor">
            {{ snackText }}
            <v-btn text @click="snack = false">Закрыть</v-btn>
        </v-snackbar>

    </v-container>
</template>

<script>
    export default {
        name: "Abonent",
        methods: {
            save () {
                this.snack = true
                this.snackColor = 'success'
                this.snackText = 'Данные сохранены'
            },
            cancel () {
                this.snack = true
                this.snackColor = 'error'
                this.snackText = 'Отмена'
            },
            open () {
                this.snack = true
                this.snackColor = 'info'
                this.snackText = 'Окно открыто'
            },
            block ()
            {
            },
            call()
            {

            }
        },
        data () {
        return {
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
                { text: 'Имя', value: 'firstName' },
                { text: 'Отчество', value: 'middleName' },
                { text: '', value: 'phone', sortable: false, width: 1},
                { text: '', value: 'doNotCall', sortable: false, width: 1},
                { text: 'Комментарий', value: 'orderComment' },
            ],
            Client: [
                {
                    id: 0,
                    firstName: 'Глеб',
                    lastName: 'Разницын',
                    middleName: 'Александрович',
                    phone: '+79117124175',
                    doNotCall: false,
                    orderComment: 'Front-end developer, Tester',
                },
                {
                    id: 1,
                    firstName: 'Михаил',
                    lastName: 'Алексеев',
                    middleName: 'Витальевич',
                    phone: '',
                    doNotCall: false,
                    orderComment: 'Back-end developer, CI/CD',
                },
                {
                    id: 2,
                    firstName: 'Марина',
                    lastName: 'Заерко',
                    middleName: 'Витальевна',
                    phone: '',
                    doNotCall: false,
                    orderComment: 'Designer',
                },
                {
                    id: 3,
                    firstName: 'Светлана',
                    lastName: 'Минина',
                    middleName: 'Владимировна',
                    phone: '',
                    doNotCall: false,
                    orderComment: 'Documentator',
                },
            ],
        }
        },
    }
</script>