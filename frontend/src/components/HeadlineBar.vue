<template>
    <div>
        <v-app-bar
                app
                color="primary"
        >

            <v-btn
                    class="headline"
                    rounded
            ><v-icon>{{'mdi-call'}}</v-icon>Call Center APP</v-btn>
            <v-spacer></v-spacer>
            <v-btn
                    class="headline"
                    rounded
                    @click="showData=false"
            >Выход</v-btn>
        </v-app-bar>
    </div>
</template>

<script>
    export default {
        name: "HeadlineBar",
        methods:{

        }
    }
</script>
