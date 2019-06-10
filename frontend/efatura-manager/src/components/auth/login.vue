<template>
  <v-layout
    align-center
    justify-center
  >
    <v-flex
      xs12
      sm8
      md4
    >
      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
        @submit.prevent="handleSubmit"
      >
        <v-card class="elevation-12">
          <v-toolbar
            dark
            color="primary"
          >
            <v-toolbar-title>Login</v-toolbar-title>
            <v-spacer />
          </v-toolbar>
          <v-card-text>
            <v-text-field
              v-model="user.username"
              prepend-icon="person"
              name="login"
              type="text"
              :rules="usernameRules"
              label="username"
              required
            />
            <v-text-field
              id="password"
              v-model="user.password"
              prepend-icon="lock"
              name="password"
              type="password"
              :rules="passwordRules"
              label="Password"
              required
            />
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn
              color="primary"
              type="submit"
            >
              Login
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-flex>
  </v-layout>
</template>

<script>
    export default {
        name: 'AppLogin',
        data: () => ({
            valid: true,
            username: '',
            usernameRules: [
                v => !!v || 'Name is required',
                v => (v && v.length > 5) || 'Password must be greater than 5 characters'
            ],
            password: '',
            passwordRules: [
                v => !!v || 'Password is required',
                v => (v && v.length > 5) || 'Password must be greater than 5 characters'
            ],
            drawer: null,
            user: {
                username: '',
                password: ''
            }
        }),
        methods: {
            // reset() {
            //   this.$refs.form.reset();
            // },
            // resetValidation() {
            //   this.$refs.form.resetValidation();
            // },
            handleSubmit() {
                if (this.$refs.form.validate()) {
                    // const postData = {
                    //   username: this.user.username,
                    //   password: this.user.password
                    // };
                    /**
                     * Storing data in localstorage
                     * Assuming data coming from backend
                     */
                    const token = 'hello-vuetiyf';
                    const user = {
                        username: this.user.username
                    };
                    this.$store
                        .dispatch('auth/saveSession', {
                            token,
                            user
                        })
                        .then(() => {
                            this.$router.push(this.$route.query.redirect || '/');
                        });
                }
            }
        }
    };
</script>