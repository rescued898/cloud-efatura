import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import requireAuth from './helpers/requireAuth';
import localStorageService from './helpers/localStorage';

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/login',
            name: 'login',
            component: () => import('./components/auth/login.vue'),
            meta: {
                layout: 'login'
            },
            beforeEnter(to, from, next) {
                if (to.name === 'login' && localStorageService.getToken()) {
                    return next(from.path);
                }
                if (to.name === 'login' && !localStorageService.getToken()) {
                    return next();
                }
                return next();
            }
        },
        {
            path: '/',
            name: 'home',
            component: Home,
            beforeEnter: requireAuth
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
        }
    ]
})
