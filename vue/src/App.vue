<template>
  <div id="app">
    <!--<div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>-->
    <router-view />
  </div>
</template>

<style>
body {
  background: rgb(0,246,255);
  background: linear-gradient(0deg, rgba(0,246,255,1) 0%, rgba(46,107,152,0.9814385150812065) 33%, rgba(0,212,255,1) 100%);
  height: 100vh;
}

</style>
