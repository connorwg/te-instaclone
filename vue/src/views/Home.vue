<template>

  <div class="home">
    <h1>
      <Header></Header>
    </h1>
  
   <div style="position: relative; display: flex; flex-direction: column; padding-bottom: 150px; padding-top: 80px; border-color: black; margin: 10px;">
     
   
    <section  class="home" v-for="p in $store.state.images" v-bind:key="p.id">
      blah
      <img :src="p.picture"  alt="none">
      {{p.id}} {{p.description}}
    </section>
   </div>
    
  </div>
</template>

<script>
import Header from './Header.vue';
export default {
  name: "home",
  components: {
    Header,
  },
  
  
};
</script>

<style scoped>


  .home {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: rgb(255,255,255);
  
  color: rgb(38, 38, 38);
  font-size: 14px;
  line-height: 18px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}


 
h1 {
  position: fixed;
  font-family: Billabong;
  top: 0;
  z-index: 200;
  background-color: rgb(230,230,230);
  width: 100%;
  justify-content: space-around;
}


section {
  border-radius: 10px;
  background-color: white;
  color: black;
  border: 1px solid lightgray;
  margin-bottom: 20px;
  padding: 20px;
  font-size: .8rem;
  text-align: left;;
}
</style>