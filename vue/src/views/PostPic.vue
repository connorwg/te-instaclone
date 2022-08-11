<template>
    <div>
        <h1>
          <Header></Header>
        </h1>
        <input type="file" accept="image/*" @change="onChange" />
        <div>
            <img id="preview" v-if="item.imageUrl" :src="item.imageUrl" />
        </div>
    </div>
</template>

<script>
import Header from "./Header.vue";

export default {
 name: "postpic",
  components: {
    Header,   
 },
   data() {
    return {
      item:{
          //...
          image : null,
          imageUrl: null
      }
    }
  },
    methods: {
    preview(event) {
      const file = event.target.files[0]
      this.image = file
      this.item.imageUrl = URL.createObjectURL(file)
    }
  }
}
</script>
<style>
    #preview {
        width: 200px;
        height: 200px;
    }
</style>