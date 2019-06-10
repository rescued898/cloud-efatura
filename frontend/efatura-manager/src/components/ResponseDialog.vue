<template>
    <v-app id="inspire">

        <v-dialog v-model="dialogNote[item.uuid]" scrollable lazy max-width="500" :key="item.uuid">
            <v-card>
                <v-card-title>
                    <span class="headline">{{ item.invoiceNumber }}</span>
                </v-card-title>

                <v-card-text>
                    <v-text-field
                            v-model="responseText"
                            :rules="rules"
                            counter="50"
                            hint="For electronic invoice application response."
                            label="Application Response"
                    ></v-text-field>
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" flat @click.stop="$set(dialogNote, item.uuid, false)" v-on:click="onClickButton(item.uuid)">Send</v-btn>
                    <v-btn color="primary" flat @click.stop="$set(dialogNote, item.uuid, false)">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

    </v-app>
</template>

<script>
    export default {
        name: "ResponseDialog",
        props: ['item', 'dialogNote'],
        data () {
            return {
                responseText: '',
                rules: [v => v.length <= 50 || 'Max 50 characters'],
            }
        },
        methods: {
            onClickButton (uuid) {
                this.$emit('sendClicked', {'responseText': this.responseText, 'uuid': uuid});
            }
        }
    }
</script>

<style scoped>

</style>