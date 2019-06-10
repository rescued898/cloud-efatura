<template>
  <div>
    <v-breadcrumbs
      :items="crumbs"
      divider=">"
    />
  </div>
</template>
<script>
    export default {
        name: 'Breadcrumb',
        computed: {
            crumbs() {
                const pathArray = this.$route.path.split('/');
                pathArray.shift();
                const breadcrumbs = pathArray.reduce((breadcrumbArray, path, idx) => {
                    breadcrumbArray.push({
                        path,
                        to: breadcrumbArray[idx - 1]
                            ? `/${breadcrumbArray[idx - 1].path}/${path}`
                            : `/${path}`,
                        text: this.$route.matched[idx].meta.breadCrumb || path
                    });
                    // if (path !== "/") {
                    breadcrumbArray.unshift({
                        path: '/',
                        disabled: (path === '/'),
                        to: '/',
                        text: 'Home'
                    });
                    // }
                    return breadcrumbArray;
                }, []);
                return breadcrumbs;
            }
        }
    };
</script>