<template>
  <ul>
    <li class="logo" @click="home"
      :class="{ active: $store.state.active === 'home' }">
      ClicknShare  
      <img  class="ico" src="../../public/logo.jpeg"/> </li>
  
    <li></li>
    <li
      v-if="$route.name != 'home'"
      @click="home"
      :class="{ active: $store.state.active === 'home' }"
    >
      Home
    </li>
  <li
        v-else
       
   @click="$window.scrollTo(0,0)"
        :class="{ active: $store.state.active === 'home' }"
      >
        Home
  </li>



    <li
      @click="postpic"
      :class="{ active: $store.state.active === 'post' }"
    >
      Post a Picture +
    </li>

    <li
      @click="favorites"
      :class="{ active: $store.state.active === 'favorites' }"
    >
      My Favorites
    </li>

    <li
      @click="userprofile"
      :class="{ active: $store.state.active === 'userprofile' }"
    >
      My Profile
    </li>

    <li
      @click="setTab('logout')"
      :class="{ active: $store.state.active === 'logout' }"
    >
    <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      
    </li>
  </ul>
</template>

<script>
import Vue from 'vue';
//import photoService from '../services/PhotoService.js';
Vue.prototype.$window = window

export default {
  components: {
   
  },
  methods: {
    setTab(active) {
      this.$store.commit("SET_TAB", active);
    },
    postpic(){
      this.setTab('post');
      this.$router.push({name: 'postpic'});
    },
    home(){
      this.setTab('home');
      this.$router.push({name: 'home'});
    },
    userprofile(){
      this.setTab('userprofile');
      this.$router.push({name: 'userprofile', params :{userId: this.$store.state.user.id}});
    },
    favorites(){
      this.setTab('favorites');
      this.$router.push({name: 'favorites'});
    }
  },
};
</script>

<style>

@font-face {
    font-family:"Billabong";
    src: local("Billabong"),
    url(../fonts/Billabong.ttf);
}

ul {
  display: flex;
  justify-content: space-around;
  list-style-type: none;
  top: 0;
  
  margin-bottom: 1px;
}

ul > li {
  display: flex;
  justify-content: center;
  margin-left: 10px;
  margin-right: 10px;
  padding: 5px;
  cursor: pointer;
}

.logo {
  font-family:"Billabong";
  color: cadetblue;
  font-size: 60px;
}

.ico {
    height: 50px;
    width: 50px;
    margin-left: 20px;
    line-height: 10px;
    border-radius: 15px 15px 10px 10px;
    
   
}
</style>
