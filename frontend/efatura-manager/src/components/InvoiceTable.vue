<template>
    <!-- <v-app id="inspire">-->
    <div>
        <v-toolbar flat color="white">
            <!--<v-toolbar-title>Invoices</v-toolbar-title>
             <v-divider
                    class="mx-2"
                    inset
                    vertical
            ></v-divider>
            <v-spacer></v-spacer>-->
            <v-text-field
                    v-model="searchQuery"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
                    v-on:input="updateSearchQuery"
            />
        </v-toolbar>

        <v-data-table
                :headers="headers"
                :items="invoices"
                :pagination.sync="pagination"
                :total-items="totalInvoices"
                :rows-per-page-items="rowsPerPageItems"
                :loading="loading"
                class="elevation-1"
        >
            <template v-slot:items="props">
                <td>{{ props.item.invoiceNumber }}</td>
                <!-- <td>{{ props.item.direction }}</td> -->
                <td>{{ props.item.issueDate }}</td>
                <td>{{ props.item.accountingSupplierParty.partyName }}</td>
                <td>{{ props.item.accountingCustomerParty.partyName }}</td>
                <td>
                    {{ props.item.lineExtensionAmount.amount }} {{ props.item.lineExtensionAmount.currencyCode }}
                </td>
                <td>{{ props.item.integrationStatus }}</td>
                <td class="justify-center layout px-0">

                    <template v-if="invoiceType == 'INCOMING'">
                        <template v-if="onInterface(props.item)">
                            <v-btn flat icon color="indigo" :loading="rowLoading(props.item)" @click="sendInvoiceToCloud(props.item)">
                                <v-icon> cloud_upload </v-icon>
                            </v-btn>
                        </template>

                        <template v-else>
                            <v-btn flat icon color="indigo">
                                <v-icon> cloud_done </v-icon>
                            </v-btn>

                            <v-btn flat icon color="indigo" small @click.stop="$set(dialogNote, props.item.uuid, true)">
                                <v-icon>reply</v-icon>
                            </v-btn>

                            <response-dialog
                                    :item="props.item"
                                    :dialog-note="dialogNote"
                                    :responseText="dialogResponse"
                                    @sendClicked="onResponseSend"
                            ></response-dialog>

                        </template>

                    </template>

                    <template v-else>
                    </template>

                </td>
            </template>
            <template v-if="searchQuery" v-slot:no-data>
                <v-alert :value="true" color="error" icon="warning">
                    Your search for "{{ searchQuery }}" found no results.
                </v-alert>
            </template>
        </v-data-table>

        <h1>Response: {{dialogResponse}}</h1>

        <v-snackbar
                v-model="snackbar"
                color="error"
                :timeout="snackbarTimeout"
        >
            {{ alertMessage }}
            <v-btn
                    flat
                    @click="snackbar = false"
            >
                Close
            </v-btn>
        </v-snackbar>

    </div>
    <!-- </v-app> -->
</template>

<script>
    /* eslint-disable */

    import ResponseDialog from "./ResponseDialog";

    export default {
        components: {ResponseDialog},
        props: ['invoices', 'invoiceType', 'totalInvoices', 'search'],
        data () {
            return {
                searchQuery: this.search,
                //totalInvoices: 0,
                //invoices: [],
                loading: false,
                pagination: {},
                alertMessage: '',
                snackbar: false,
                dialogResponse: '',
                dialogNote: {},
                skipQuery: false,
                snackbarTimeout: 6000,
                headers: [
                    {
                        text: 'Invoice Number',
                        align: 'left',
                        value: 'invoiceNumber'
                    },
                    //{text: 'Direction', value: 'direction'},
                    {
                        text: 'Issue Date',
                        sortable: false,
                        value: 'issueDate'
                    },
                    {text: 'Supplier Party', value: 'accountingSupplierParty.partyName'},
                    {text: 'Customer Party', value: 'accountingCustomerParty.partyName'},
                    {
                        text: 'Amount',
                        sortable: false,
                        value: 'lineExtensionAmount.amount'
                    },
                    {text: 'Status', value: 'processed', sortable: false},
                    {text: 'Actions', value: 'name', sortable: false},
                ],
                rowsPerPageItems: [5, 10, 20, 50],
            };
        },
        watch: {
        },
        methods: {
            sendInvoiceToCloud(item) {
                console.log('uuid: ' + item.uuid);

                let currentObj = this;

                this.$http.post('sendInvoiceToCloud', {
                    uuid: item.uuid
                }).then((response) => {
                    item.integrationStatus = "CLOUD_PROCESSING";

                    currentObj.alertMessage = response.data.message;
                    currentObj.snackbar = true;
                }).catch((e) => {
                    currentObj.alertMessage = e;
                    console.error(e);

                    currentObj.snackbar = true;
                });
            },
            rowLoading(item) {
                return item.integrationStatus === "CLOUD_PROCESSING" ? true : false;
            },
            onInterface(item) {
                return ["ON_INTERFACE", "CLOUD_PROCESSING"].indexOf(item.integrationStatus) >= 0 ? true : false;
            },
            onResponseSend (resp) {
                this.dialogResponse = resp.uuid + " -- " + resp.responseText;
            },
            updateSearchQuery (value) {
                this.$emit('searchEntered', value);
            }
        },
        computed: {
            currentPage() {
                if (this.pagination.page == null)
                    return 0;

                return this.pagination.page - 1;
            },
            pageSize() {
                if (this.pagination.rowsPerPage == null)
                    return 10;

                return this.pagination.rowsPerPage
            },
            sortOrder() {
                if (this.pagination.descending)
                    return "DESC";

                return "ASC";
            },
            sortBy() {
                if (this.pagination.sortBy == null)
                    return "invoiceNumber";

                return this.pagination.sortBy;
            },
            pages () {
                if (this.pagination.rowsPerPage == null ||
                    this.totalInvoices == null
                ) return 0;

                return Math.ceil(this.totalInvoices / this.pagination.rowsPerPage);
            },
        },
    }
</script>

<style>

</style>
