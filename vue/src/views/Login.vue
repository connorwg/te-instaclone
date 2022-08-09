<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1>ClicknShare</h1>
      <h2 class="h3 mb-3 font-weight-normal">Please sign in</h2>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
       <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      
      <button type="submit">Log In</button>
    </form>
    <div id="register-user">
      <label>Need an account? <router-link :to="{ name: 'register' }">Sign up</router-link></label>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    register(){
      this.$router.push({name: 'register'});
    },

    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style>
#login {
  display: grid;
  justify-items: center;
  grid-template-areas: "form-register"
                       "back-to-login";
}

.form-signin {
  display: grid;
  grid-area: form-register;
  border: 1px solid black;
  border-radius: 5px;
  width: 25%;
  margin: 5px;
  row-gap: 10px;
  column-gap: 30px;
  padding-bottom: 20px;
  background-color: aliceblue;
  justify-items: center;
}

#register-user {
  display: grid;
  grid-area: back-to-login;
  border: 1px solid black;
  border-radius: 5px;
  width: 25%;
  margin: 5px;
  padding-bottom: 10px;
  padding-top: 10px;
  row-gap: 10px;
  background-color: aliceblue;
  justify-items: center;
}

h1 {
  font-family: emoji;
  font-weight: 1000;
  font-style: italic;
  margin-bottom: 0%;
  
}

h2 {
  font-family: emoji;
  font-style: italic;
  margin: 0%;
}
</style>
