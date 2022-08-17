<template>

  <div id="login" class="text-center">
    <icon-image id="login-image"/>
    <form class="form-signin" @submit.prevent="login">
      <h1 id="login-banner">ClicknShare</h1>
      <h2 id="login-text" class="h3 mb-3 font-weight-normal">Please sign in</h2>
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
      
      <button id="loginButton" type="submit"  class="badge rounded-pill bg-info">Log In</button><!--added bootstrap-->
    </form>
    <div id="register-user">
      <label>Need an account? <router-link :to="{ name: 'register' }">Sign up</router-link></label>
    </div>
  </div>
  
</template>

<script>
import authService from "../services/AuthService";
import IconImage from "../components/IconImage.vue"

export default {
  name: "login",
  components: {
    IconImage
  },
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
  grid-template-columns: 1fr 1fr;
  grid-template-areas: "login-image form-signin"
                       "login-image register-user";                
}

.form-signin {
  
  display: grid;
  grid-area: form-signin;
  border: 1px solid black;
  border-radius: 5px;
  width: 35%;
  margin: 5px;
  margin-top: 105px;
  margin-left: -10%;
  row-gap: 10px;
  column-gap: 30px;
  padding-bottom: 20px;
  background-color: aliceblue;
  justify-items: center;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

#register-user {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  display: grid;
  grid-area: register-user;
  border: 1px solid black;
  border-radius: 5px;
  width: 35%;
  margin: 5px;
  margin-left: -10%;
  padding-bottom: 10px;
  padding-top: 10px;
  row-gap: 10px;
  background-color:aliceblue;
  justify-items: center;
  align-content: center;
  font-family:"Billabong";
  font-size: 200%;
}

#login-image {
  grid-area: login-image;
  margin-top: 75px;
  margin-right: -20%;
  
}

#login-banner {
  font-family:"Billabong";
  color: cadetblue;
  font-weight: 1000;
  font-style: italic;
  margin-top: 10%;
  margin-bottom: 0%; 
}

#login-text {
  font-family: "Billabong";
  font-style: italic;
  margin: 0%;
}



#username {
  width: 75%;
  height: 90%;
  font-size: 15pt;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  margin-bottom: 2%
}

#password {
  width: 75%;
  height: 88%;
  font-size: 15pt;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  margin-bottom: 4%
}

#loginButton {
  font-size: 20px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}


#loginButton:active {
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}

</style>
