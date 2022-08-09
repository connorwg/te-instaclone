<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1>TEgram</h1>
      <h2 class="h3 mb-3 font-weight-normal">Create an Account</h2>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div>
        <!--<label for="name" class="sr-only">Name</label> -->
        <input
          type="text"
          id="name"
          class="form-control"
          placeholder="Full Name"
          v-model="user.name"
          required
          />
      </div>  
      
      <div>
        <!-- <label for="emailid" class="sr-only">Email Id</label> -->
        <input
          type="text"
          id="emailid"
          class="form-control"
          placeholder="email@example.com"
          v-model="user.emailId"
          required
        />
      </div>

      <div>
        <!-- <label for="username" class="sr-only">Username</label> -->
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      </div>
      
      <div>
        <!-- <label for="password" class="sr-only">Password</label> -->
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
          v-on:change="checkPassword"
        />
        <div v-if="!validPassword">Invalid Password</div>
      </div>

      <div>
        <input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
          v-on:change="checkConfirmPassword"
        />
        <div v-if="!passwordsMatch">Passwords Not Matching</div>
      </div>
      
      <div>
        <label for="image" class="sr-only">Upload a Profile picture</label> 
      </div>
      <div>
        <input type="file" accept="image/*" id="file-input" />
      </div>
      
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
    </form>

    <div class="back-to-login">
      <!--<router-link :to="{ name: 'login' }">Already have an account?</router-link>-->
      <label>Already have an account?</label>
      <button class="btn btn-lg btn-primary btn-block" type="login" v-on:click="backToLogin">
        Log in
      </button>
    </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        name: '',
        emailId: '',
        username: '',
        password: '',
        confirmPassword: '',
        image: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      validPassword : true,
      passwordsMatch : true
    };
  },
  methods: {
    backToLogin(){
      this.$router.push({name: 'login'});
    },

    checkPassword(){
      const pwd = this.user.password;
      const smallLettersRule = /[a-z]/;
      const upperLettersRule = /[A-Z]/;
      const numbersRule = /[0-9]/;
      const splCharRule = /[@$%!]/;
      if(pwd.length < 8 || !smallLettersRule.test(pwd) || !upperLettersRule.test(pwd) 
      || !numbersRule.test(pwd) || !splCharRule.test(pwd)){
        this.validPassword = false;
      } else{
        this.validPassword = true;
      }
    },

    checkConfirmPassword(){
      if(this.user.password === this.user.confirmPassword){
        this.passwordsMatch = true;
      } else {
        this.passwordsMatch = false;
      }
    },
    
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style>

#register {
  display: grid;
  justify-items: center;
  grid-template-areas: "form-register"
                       "back-to-login";
}

.form-register {
  display: grid;
  grid-area: form-register;
  border: 1px solid black;
  border-radius: 5px;
  width: 25%;
  height: 400px;
  margin: 10px;
  row-gap: 10px;
  column-gap: 30px;
  padding-bottom: 20px;
  background-color: aliceblue;
  justify-items: center;
}

.back-to-login {
  display: grid;
  grid-area: back-to-login;
  border: 1px solid black;
  border-radius: 5px;
  width: 25%;
  height: 50px;
  margin: 10px;
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

.form-control {
  width: 200px;
  height: 30px;
}

</style>
