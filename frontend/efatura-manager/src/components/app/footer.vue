<template>
  <v-footer
    height="auto"
    color="indigo"
  >
    <v-layout
      justify-center
      row
      wrap
    >
      <v-btn
        v-for="link in links"
        :key="link.title"
        color="white"
        flat
        round
        :to="link.to"
      >
        {{ link.title }}
      </v-btn>
      <v-flex
        indigo
        py-3
        text-xs-center
        white--text
        xs12
      >
        &copy;2019 — <strong>Experteam</strong>
      </v-flex>
    </v-layout>
  </v-footer>
</template>

<script>
    export default {
        name: 'AppFooter',
        data() {
            return {
                links: [
                    {title: 'Home', to: '/'},
                    {title: 'About Us', to: '/about'},
                    {title: 'Team', to: '/about'},
                    {title: 'Services', to: '/about'},
                    {title: 'Blog', to: '/about'},
                    {title: 'Contact Us', to: '/about'}
                ]
            };
        }
    };
</script>

<style>
</style>