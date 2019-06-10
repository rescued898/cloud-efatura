<template>
  <div v-if="layout">
    <component :is="layout">
      <router-view :key="$route.fullPath" />
    </component>
  </div>
</template>

<script>
    const defaultLayout = 'default';
    export default {
        computed: {
            layout() {
                return `${this.$route.meta.layout || defaultLayout}-layout`;
            }
        }
    };
</script>