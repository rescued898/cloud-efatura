<template>
  <div id="app">
    <v-card>
      <v-tabs
              v-model="activeTab"
              centered
              flat
      >
        <v-tab
                v-for="invoiceType in invoiceTypes"
                :key="invoiceType"
                ripple
        >
          {{ invoiceType }} Invoices

        </v-tab>
        <v-tab-item
                v-for="invoiceType in invoiceTypes"
                :key="invoiceType"
        >

          <InvoiceTable
                  :invoices="invoices"
                  :invoice-type="invoiceType"
                  :total-invoices="totalInvoices"
                  :search="filter"
                  @searchEntered="onSearchEntered"
          ></InvoiceTable>

        </v-tab-item>
      </v-tabs>

    </v-card>
  </div>
</template>

<script>

    import {searchInvoicesQuery, invoiceCount} from './../graphql/queries/invoices'
    import InvoiceTable from "../components/InvoiceTable";

    export default {
        components: {InvoiceTable},
        apollo: {
            searchInvoices: {
                // GraphQL Query
                query: searchInvoicesQuery,
                // Reactive variables
                variables() {
                    return {
                        direction: this.direction,
                        filter: this.filter,
                    }
                },
                // Disable the query
                skip () {
                    return this.skipQuery
                },
                pollInterval: 500, // ms
                fetchPolicy : 'cache-and-network',
                update(data) {
                    this.invoices = data.searchInvoices;
                    // this.totalInvoices = data.searchInvoices.length;
                }
            },
            // Query with parameters
            invoiceCount () {
                // This is called once when the component is created
                // It must return the option object
                return {
                    // gql query
                    query: invoiceCount,
                }
            },
        },
        data() {
            return {
                activeTab: 0,
                filter: '',
                totalInvoices: 0,
                invoices: [],
                skipQuery: false,
                invoiceTypes: ['INCOMING', 'OUTGOING'],
            };
        },
        computed: {
            direction() {
                return this.invoiceTypes[this.activeTab] === 'INCOMING' ? 'IN' : 'OUT';
            },
        },
        methods: {
            onSearchEntered(value) {
                this.filter = value;
            }
        },
        watch: {
            activeTab: {
                handler () {
                    // eslint-disable-next-line
                    console.log('tab changed.' + this.activeTab);
                    this.filter = '';
                }
            }
        }
    };
</script>
