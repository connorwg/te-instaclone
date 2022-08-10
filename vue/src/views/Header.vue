<template>
  <ul>
    <li class="logo">ClicknShare  <img :src="$store.state.icon"  alt="none" class="icon"></li>
    <li></li>
    <li
      v-if="$route.name != 'home'"
      @click="this.$router.push({ name: 'home' })"
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
      @click="setTab('post')"
      :class="{ active: $store.state.active === 'post' }"
    >
      Post a Picture
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
import Vue from 'vue'
Vue.prototype.$window = window
export default {
  methods: {
    setTab(active) {
      this.$store.commit("SET_TAB", active);
    },
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
  font-size: 50px;
}

.icon {
    height: .8em;
    width: .8em;
    margin-left: 10px;
}
</style>