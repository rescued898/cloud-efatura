<template>
  <v-app id="inspire">
    <v-content>
      <v-container
        fluid
        fill-height
      >
        <slot />
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
    export default {
        name: 'LoginLayout'
    };
</script>