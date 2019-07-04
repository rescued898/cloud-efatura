import Vue from 'vue';
import Vuex from 'vuex';
import auth from './modules/auth/index';

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
    modules: {
        auth
    },
    // Making sure that we're doing
    // everything correctly by enabling
    // strict mode in the dev environment.
    strict: debug
    // plugins: debug ? [createLogger()] : []
});