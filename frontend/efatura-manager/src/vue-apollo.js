import Vue from 'vue'
import ApolloClient from "apollo-boost"
import VueApollo from "vue-apollo";

/* eslint-disable */

// Create the apollo client
export const apolloClient = new ApolloClient({
    uri: process.env.VUE_APP_GRAPHQL_HTTP,
});

const apolloProvider = new VueApollo({
    defaultClient: apolloClient,
    errorHandler (error) {
        // eslint-disable-next-line no-console
        console.log('%cError', 'background: red; color: white; padding: 2px 4px; border-radius: 3px; font-weight: bold;', error.message)
    },
});

// Install the vue plugin
Vue.use(VueApollo);

export default apolloProvider