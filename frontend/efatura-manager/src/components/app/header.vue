<template>
  <div>
    <v-toolbar
      color="indigo"
      dark
      fixed
      app
    >
      <v-toolbar-side-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title>E-Invoice</v-toolbar-title>
      <v-spacer />
      <v-menu
        v-model="menu"
        bottom
        left
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
      >
        <template v-slot:activator="{ on }">
          <v-btn
            dark
            icon
            v-on="on"
          >
            <v-icon>more_vert</v-icon>
          </v-btn>
        </template>

        <v-card>
          <v-list>
            <v-list-tile avatar>
              <!--<v-list-tile-avatar>
                <img
                  src="https://cdn.vuetifyjs.com/images/john.jpg"
                  alt="John"
                >
              </v-list-tile-avatar>-->

              <v-list-tile-content>
                <v-list-tile-title>{{ user.username }}</v-list-tile-title>
                <!-- <v-list-tile-sub-title>Founder of Vuetify.js</v-list-tile-sub-title> -->
              </v-list-tile-content>
            </v-list-tile>
          </v-list>

          <v-divider />

          <v-card-actions>
            <v-spacer />

            <v-btn
              flat
              @click="menu = false"
            >
              Cancel
            </v-btn>
            <v-btn
              color="primary"
              flat
              @click="logout"
            >
              Logout
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-menu>
    </v-toolbar>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant="miniVariant"
      :clipped="clipped"
      fixed
      app
    >
      <v-list>
        <v-list-tile
          v-for="(item, i) in items"
          :key="i"
          :to="item.to"
          router
          exact
        >
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title v-text="item.title" />
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
    import { mapGetters } from 'vuex';

    export default {
        name: 'AppHeader',
        data() {
            return {
                clipped: false,
                drawer: false,
                fav: true,
                fixed: false,
                items: [
                    {title: 'Home', icon: 'dashboard', to: '/'},
                    {title: 'About', icon: 'question_answer', to: '/about'}
                ],
                miniVariant: false,
                logo: require('../../assets/logo.svg')
            };
        },
        methods: {
            logout() {
                this.$store.dispatch('auth/destroySession').then(() => {
                    this.$router.push('/login');
                });
            }
        },
        computed: {
            ...mapGetters('auth', {
                user: 'getUser'
            })
        },
        created() {
            this.$store.dispatch('auth/fetchSession');
        }
    };
</script>

<style>
</style>