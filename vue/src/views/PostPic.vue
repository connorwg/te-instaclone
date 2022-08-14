<template>
    <div id="main-grid">
        <h1 id="postpic-header">
          <Header></Header>
        </h1>
        <input id="upload" type="file" accept="image/*" @change="preview" />
        <div v-if="item.imageUrl">
            <img id="preview" :src="item.imageUrl" />
            <input type="text"
             id="caption"
             v-model="item.caption"
             placeholder="Add a Caption!"/>
             <button id="postpicture" v-on:click.prevent="postPicture">Post</button><!--to .prevent default function of click-->
             <div>
              <label style="color: red" v-if="!gotResponse && postRequestSent">Uploading... Please wait...</label>
              <label style="color: blue; font-weight: bold" v-else-if="gotResponse">Picture uploaded!</label>
             </div> 
        </div>
    </div>
</template>

<script>
import Header from "./Header.vue";
import photoService from "../services/PhotoService.js";

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
          imageUrl: null,
          caption: null
      },
      gotResponse: false,
      postRequestSent: false,
    }
  },
  methods: {
    preview(event) {
      const file = event.target.files[0]//to retrieve data from event
      this.item.image = file
      this.item.imageUrl = URL.createObjectURL(file)
    },
    postPicture() {
        this.postRequestSent = true;
        photoService.addPicture(this.item.caption, this.item.image).then(response => {
          this.gotResponse = true;
          if(response.status === 200){
            const data = response.data;
            this.$store.commit('ADD_PHOTOS', data);
          }
        }).catch(error => {
          alert(error.response.status);
          alert('Message upload failed');
        });
      }
  }
}
</script>
<style>
  #main-grid {
    display: grid;
    grid-template-areas: "postpic-header"
                         "upload"
                         "preview";
  }
  #preview {
    display: grid;
    grid-area: preview;
    width: 200px;
    height: 200px;
    justify-items: center;
      
  }
  #postpic-header {
    grid-area: postpic-header;
    background-color: rgb(230, 230, 230);
    width: 100%;
    justify-content: space-around;
  }
  #upload {
    grid-area: upload;
  }
</style>
