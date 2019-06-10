import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store/index';
import defaultLayout from './layouts/default';
import loginLayout from './layouts/login'
import './plugins/vuetify'
import apolloProvider from './vue-apollo'
import VueLogger from 'vuejs-logger';
import axios from 'axios'

/** Configure Layouts */
Vue.component('default-layout', defaultLayout);
Vue.component('login-layout', loginLayout);

/** Vue js configuration */
Vue.config.productionTip = false;

const isProduction = process.env.NODE_ENV === 'production';

const options = {
    isEnabled: true,
    logLevel : isProduction ? 'error' : 'debug',
    stringifyArguments : false,
    showLogLevel : true,
    showMethodName : true,
    separator: '|',
    showConsoleColors: true
};

Vue.use(VueLogger, options);

/** Creating and configuring Vuejs Instance */
new Vue({
  router,
  store,
  apolloProvider,
  render: h => h(App)
}).$mount('#app');
